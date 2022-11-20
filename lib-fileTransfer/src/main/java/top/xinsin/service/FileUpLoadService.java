package top.xinsin.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.xinsin.dao.FileUploadMapper;
import top.xinsin.enums.HttpCodes;
import top.xinsin.pojo.FileObject;
import top.xinsin.utils.FileUtils;
import top.xinsin.utils.JwtTokenUtils;
import top.xinsin.utils.ResultData;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

/**
 * @author xinxin
 * Created On 2021/12/18 13:40
 * @version 1.0
 */

@Service
@Slf4j
public class FileUpLoadService {

    final
    FileUploadMapper fileUploadMapper;

    /**
     * 文件保存的目录
     */
    @Value("${saveFolder}")
    private String fileSaveFolder;
    @Autowired
    public FileUpLoadService(FileUploadMapper fileUploadMapper) {
        this.fileUploadMapper = fileUploadMapper;
    }

    /**
     * 上传文件服务
     *
     * @param file    文件
     * @param request 请求
     * @return 是否成功
     */

    public ResultData<String> fileUpload(MultipartFile file, HttpServletRequest request) throws NoSuchAlgorithmException, IOException {
        if (file.isEmpty()) {
            return ResultData.failed(HttpCodes.HTTP_CODES401, "文件是空的");
        }
        log.info("starting saving");
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件的InputStream
        InputStream input = file.getInputStream();
        //拼接文件保存路径
        String savePath = fileSaveFolder + File.separator + originalFilename;
        //获取文件对象
        File file1 = new File(savePath);
        //获取磁盘中的文件的OutputStream
        FileOutputStream out = new FileOutputStream(file1);
        //使用IOUtils来复制
        IOUtils.copy(input,out);
        //计算磁盘中文件的md5
        String md5 = FileUtils.calcMd5(file1);
        log.info("fileSaved,SavedName:{},md5:{}",savePath,md5);
        //关闭输入流
        input.close();
        //关闭输出流
        out.close();
        //清除缓存
        System.gc();
        log.info("closeIOStream");
        log.info("fileRename to {}",md5);
        //获取使用md5拼接的文件名
        String newSavePath = fileSaveFolder + File.separator + md5;
        File newFile = new File(newSavePath);
        if(newFile.exists()){
            log.info("fileExists!");
        }else{
            //将磁盘中的文件重命名
            boolean b = file1.renameTo(newFile);
            log.info("fileRenamed,Succeed:{}",b);
            log.info("Sending to database");
            int id = Integer.parseInt(JwtTokenUtils.getTokenInfo(request.getHeader("Access-Token")).getClaim("id").asString());
            //新建FileObject
            FileObject fileObject = new FileObject(originalFilename, file.getSize(), md5, id);
            //上传数据库
            fileUploadMapper.addFile(fileObject);
            log.info("Sanded");
            log.info("fileUpload done");
        }
        return ResultData.success(md5);
    }
}
