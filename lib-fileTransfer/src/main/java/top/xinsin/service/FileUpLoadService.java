package top.xinsin.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import top.xinsin.Utils.ResponseData;
import top.xinsin.dao.FileUpLoadMapper;
import top.xinsin.pojo.FileObject;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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
        MessageDigest MD5 = MessageDigest.getInstance("MD5");
        byte[] digest = MD5.digest(file.getBytes());
        String md5 = String.valueOf(Hex.encodeHex(digest));//文件MD5
        long size = file.getSize();
        if (file.isEmpty()) {
            return new ResponseData(HttpStatus.BAD_REQUEST);
        }
        String originalFilename = file.getOriginalFilename(); // 源文件名
        FileObject fileObject = new FileObject(originalFilename, size, md5);
        File dest = new File(FileSaveFolder + File.separator + md5);
        try {
            file.transferTo(dest);
            fileUpLoadMapper.addFile(fileObject);
            log.info("上传成功{}",fileObject);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("md5",md5);
            return new ResponseData(jsonObject,HttpStatus.OK);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return new ResponseData(HttpStatus.EXPECTATION_FAILED);
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
