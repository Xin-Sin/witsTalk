package top.xinsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.xinsin.pojo.FileObject;
import top.xinsin.service.FileDownloadService;

import java.io.IOException;

/**
 * @Author xinxin
 * @Date 2022/1/2 15:09
 * @Version 1.0
 */
@RestController
public class FileDownloadController {
    //
    @Autowired
    private FileDownloadService fileDownloadService;
    @GetMapping("/file/api/getName")
    public String getFileName(@RequestParam("md5") String md5){
        return fileDownloadService.getFileName(new FileObject(md5)).toString();
    }

    @GetMapping("/file/api/downloadFile")
    public ResponseEntity<InputStreamResource> getFile(@RequestParam("md5") String md5, @RequestParam("filename") String filename,@RequestParam("token") String token) throws IOException {
        return fileDownloadService.getFile(md5,filename,token);
    }
    @PostMapping("/file/api/getAllFileNames")
    public String getAllFileNames(){
        return fileDownloadService.getAllFileNames().toString();
    }
}
