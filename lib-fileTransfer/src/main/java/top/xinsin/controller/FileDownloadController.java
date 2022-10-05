package top.xinsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.xinsin.pojo.FileObject;
import top.xinsin.service.FileDownloadService;
import top.xinsin.utils.ResultData;

import java.io.IOException;
import java.util.List;

/**
 * @author xinxin
 * Created On 2022/1/2 15:09
 * @version 1.0
 */
@RestController
public class FileDownloadController {
    private final FileDownloadService fileDownloadService;
    @Autowired
    public FileDownloadController(FileDownloadService fileDownloadService){
        this.fileDownloadService = fileDownloadService;
    }
    @GetMapping("/file/api/getName")
    public ResultData<String> getFileName(@RequestParam("md5") String md5){
        return fileDownloadService.getFileName(new FileObject(md5));
    }

    @GetMapping("/file/api/downloadFile")
    public ResponseEntity<InputStreamResource> getFile(@RequestParam("md5") String md5, @RequestParam("filename") String filename,@RequestParam("token") String token) throws IOException {
        return fileDownloadService.getFile(md5,filename,token);
    }
    @PostMapping("/file/api/getAllFileNames")
    public ResultData<List<?>> getAllFileNames(){
        return fileDownloadService.getAllFileNames();
    }
}
