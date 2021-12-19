package top.xinsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xinsin.service.FileUpLoadService;

/**
 * @Author xinxin
 * @Date 2021/12/14 14:07
 * @Version 1.0
 */
@Controller
public class FileUpLoadController {
    @Autowired
    FileUpLoadService fileUpLoadService;
    @PostMapping("/api/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file")MultipartFile file){
        return fileUpLoadService.fileUpload(file);
    }
}
