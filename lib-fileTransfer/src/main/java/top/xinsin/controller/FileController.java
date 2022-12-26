package top.xinsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.xinsin.service.FileDownloadService;
import top.xinsin.service.FileService;
import top.xinsin.utils.RData;

/**
 * @author xinsin
 * Created On 2022/12/22 11:20
 * @version 1.0
 */
@RestController
public class FileController {
    private final FileService fileService;
    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }
    @GetMapping("/file/fileDelete")
    public RData<String> fileDelete(@RequestParam("id")Integer id,@RequestParam("md5")String md5){
        return fileService.fileDelete(id,md5);
    }
}
