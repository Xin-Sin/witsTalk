package top.xinsin.service;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import top.xinsin.dao.FileDownloadMapper;
import top.xinsin.pojo.FileObject;
import top.xinsin.utils.JwtTokenUtils;
import top.xinsin.utils.ResultData;

import java.io.File;
import java.io.IOException;

/**
 * @author xinxin
 * Created On 2022/1/2 15:14
 * @version 1.0
 */

@Service
@Slf4j
public class FileDownloadService {

    private final FileDownloadMapper fileDownloadMapper;
    /**
     * 文件保存目录
     */
    @Value("${saveFolder}")
    private String fileSaveFolder;
    @Autowired
    public FileDownloadService(FileDownloadMapper fileDownloadMapper) {
        this.fileDownloadMapper = fileDownloadMapper;
    }

    /**
     * 获取原始文件名
     * @param fileObject 文件对象
     * @return 原始文件名
     */
    public ResultData<String> getFileName(FileObject fileObject){
        log.info("getFileName,args:{}",fileObject);
        String fileName = fileDownloadMapper.getFileName(fileObject);
        return ResultData.success(fileName);
    }

    /**
     * 获取所有文件名称
     *
     * @return 文件名称
     */
    public ResultData<JSONObject> getAllFileNames(int minId) {
        log.info("getAllFileNames --> begin");
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("files", fileDownloadMapper.getAllFileNames(minId));
        jsonObject.fluentPut("total", fileDownloadMapper.selectFileCount());
        return ResultData.success(jsonObject);
    }

    /**
     * 下载文件
     * @param md5 文件md5值
     * @param filename 下载时的文件名
     * @param token JWTToken
     * @return 文件
     * @throws IOException 当文件获取失败时抛出
     */
    public ResponseEntity<InputStreamResource> getFile(String md5, String filename,String token) throws IOException {
        //验证token合法性
        JwtTokenUtils.verify(token);
        log.info("getFile,md5={},filename={}",md5,filename);
        FileSystemResource file = new FileSystemResource(fileSaveFolder + File.separator + md5);
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
