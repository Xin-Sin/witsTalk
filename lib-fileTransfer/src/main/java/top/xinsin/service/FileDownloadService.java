package top.xinsin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import top.xinsin.Utils.ResponseData;
import top.xinsin.dao.FileDownloadMapper;
import top.xinsin.pojo.FileObject;

import java.io.File;
import java.io.IOException;

/**
 * @Author xinxin
 * @Date 2022/1/2 15:14
 * @Version 1.0
 */
@Service
@Slf4j
public class FileDownloadService {
    @Autowired
    private FileDownloadMapper fileDownloadMapper;

    @Value("${saveFolder}")//从Application.yml中读取上传文件存储的位置
    private String FileSaveFolder;//将其写入变量

    //获取原始文件名
    public ResponseData getFileName(FileObject fileObject){
        log.info("getFileName,args:{}",fileObject);
        String fileName = fileDownloadMapper.getFileName(fileObject);
        return new ResponseData(fileName);
    }

    //下载
    public ResponseEntity<InputStreamResource> getFile(String md5, String filename) throws IOException {
        log.info("getFile,md5={},filename={}",md5,filename);
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
