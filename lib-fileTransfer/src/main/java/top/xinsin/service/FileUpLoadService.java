package top.xinsin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import top.xinsin.Utils.ResponseData;

import java.io.File;
import java.io.IOException;

/**
 * @Author xinxin
 * @Date 2021/12/18 13:40
 * @Version 1.0
 */

@Service
@Slf4j
public class FileUpLoadService {

    @Value("${saveFolder}")//从Application.yml中读取上传文件存储的位置
    private String FileSaveFolder;//将其写入变量

    /**
     * 上传文件服务
     * @param file 文件
     * @return
     */

    public ResponseData fileUpload(MultipartFile file){
        System.out.println(FileSaveFolder);
        if (file.isEmpty()) {
            return new ResponseData(HttpStatus.BAD_REQUEST);
        }
        String fileName = file.getOriginalFilename();
        File dest = new File(FileSaveFolder + File.separator + fileName);
        try {
            file.transferTo(dest);
            log.info("上传成功");
            return new ResponseData(HttpStatus.OK);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return new ResponseData(HttpStatus.EXPECTATION_FAILED);
    }
}
