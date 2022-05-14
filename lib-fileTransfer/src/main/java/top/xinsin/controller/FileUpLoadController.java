package top.xinsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xinsin.Utils.ResultData;
import top.xinsin.service.FileUpLoadService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author xinxin
 * @date 2021/12/14 14:07
 * @version 1.0
 */
@RestController
public class FileUpLoadController {
    private final FileUpLoadService fileUpLoadService;
    @Autowired
    public FileUpLoadController(FileUpLoadService fileUpLoadService) {
        this.fileUpLoadService = fileUpLoadService;
    }

    @PostMapping("/api/fileUpload")
    public ResultData<String> fileUpload(@RequestBody MultipartFile file) throws NoSuchAlgorithmException, IOException {
        return fileUpLoadService.fileUpload(file);
    }
    @GetMapping("/file/api/debug")
    public String debug(){
        return "123";
    }
}
