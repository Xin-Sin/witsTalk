package top.xinsin.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.xinsin.Utils.FileUtils;
import top.xinsin.Utils.ResponseData;
import top.xinsin.dao.FileUpLoadMapper;
import top.xinsin.pojo.FileObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

/**
 * @Author xinxin
 * @Date 2021/12/18 13:40
 * @Version 1.0
 */

@Service
@Slf4j
public class FileUpLoadService {

    @Autowired
    FileUpLoadMapper fileUpLoadMapper;

    @Value("${saveFolder}")//从Application.yml中读取上传文件存储的位置
    private String FileSaveFolder;//将其写入变量

    /**
     * 上传文件服务
     * @param file 文件
     * @return
     */

    public ResponseData fileUpload(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        if(file.isEmpty()){
            return new ResponseData(HttpStatus.BAD_REQUEST);
        }
        log.info("starting saving");
        String originalFilename = file.getOriginalFilename();//获取原始文件名
        InputStream input =  file.getInputStream();//获取文件的InputStream
        String savePath = FileSaveFolder + File.separator + originalFilename; //拼接文件保存路径
        File file1 = new File(savePath);//获取文件对象
        FileOutputStream out = new FileOutputStream(file1);//获取磁盘中的文件的OutputStream
        IOUtils.copy(input,out);//使用IOUtils来复制
        String md5 = FileUtils.calcMD5(file1);//计算磁盘中文件的md5
        log.info("fileSaved,SavedName:{},md5:{}",savePath,md5);
        input.close();//关闭输入流
        out.close();//关闭输出流
        log.info("closeIOStream");
        log.info("fileRename to {}",md5);
        String newSavePath = FileSaveFolder + File.separator + md5;//获取使用md5拼接的文件名
        File newFile = new File(newSavePath);
        if(newFile.exists()){
            log.info("fileExists!");
        }else{
            boolean b = file1.renameTo(newFile);//将磁盘中的文件重命名
            log.info("fileRenamed,Succeed:{}",b);
            log.info("Sending to database");
            FileObject fileObject = new FileObject(originalFilename,file.getSize(),md5);//新建FileObject
            fileUpLoadMapper.addFile(fileObject);//上传数据库
            log.info("Sanded");
            log.info("fileUpload done");
        }
        return new ResponseData(md5);
    }

    public ResponseData getFileName(FileObject fileObject){
        String fileName = fileUpLoadMapper.getFileName(fileObject);
        return new ResponseData(fileName);
    }

    public ResponseEntity<InputStreamResource> getFile(String md5, String filename) throws IOException {
        FileSystemResource file = new FileSystemResource(FileSaveFolder + File.separator + md5);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

}
