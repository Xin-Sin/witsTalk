package top.xinsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
}
