package top.xinsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xinsin.pojo.FileObject;
import top.xinsin.service.FileUpLoadService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author xinxin
 * @Date 2021/12/14 14:07
 * @Version 1.0
 */
@RestController
public class FileUpLoadController {
    @Autowired
    FileUpLoadService fileUpLoadService;
    @PostMapping("/api/fileUpload")
    public String fileUpload(@RequestBody MultipartFile file) throws NoSuchAlgorithmException, IOException {
        return fileUpLoadService.fileUpload(file).toString();
    }

    @GetMapping("/api/getName")
    public String getFileName(@RequestParam("md5") String md5){
        return fileUpLoadService.getFileName(new FileObject(md5)).toString();
    }

    @GetMapping("/api/getFile")
    public ResponseEntity<InputStreamResource> getFile(@RequestParam("md5") String md5,@RequestParam("filename") String filename) throws IOException {
        return fileUpLoadService.getFile(md5,filename);
    }
}
