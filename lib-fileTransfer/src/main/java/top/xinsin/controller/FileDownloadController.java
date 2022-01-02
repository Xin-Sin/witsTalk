package top.xinsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.xinsin.pojo.FileObject;
import top.xinsin.service.FileDownloadService;
import top.xinsin.service.FileUpLoadService;

import java.io.IOException;

/**
 * @Author xinxin
 * @Date 2022/1/2 15:09
 * @Version 1.0
 */
@RestController
public class FileDownloadController {

    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("/api/getName")
    public String getFileName(@RequestParam("md5") String md5){
        return fileDownloadService.getFileName(new FileObject(md5)).toString();
    }

    @GetMapping("/api/getFile")
    public ResponseEntity<InputStreamResource> getFile(@RequestParam("md5") String md5, @RequestParam("filename") String filename) throws IOException {
        return fileDownloadService.getFile(md5,filename);
    }
}
