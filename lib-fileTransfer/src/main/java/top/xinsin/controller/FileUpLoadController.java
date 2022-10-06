package top.xinsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.xinsin.service.FileUpLoadService;
import top.xinsin.utils.ResultData;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author xinxin
 * Created On 2021/12/14 14:07
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
}
