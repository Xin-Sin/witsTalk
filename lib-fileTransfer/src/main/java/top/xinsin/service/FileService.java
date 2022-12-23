package top.xinsin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.xinsin.dao.FileMapper;
import top.xinsin.dao.FileUploadMapper;
import top.xinsin.enums.HttpCodes;
import top.xinsin.utils.RData;

import java.io.File;

/**
 * @author xinsin
 * Created On 2022/12/22 11:22
 * @version 1.0
 */
@Service
@Slf4j
public class FileService {
    private final FileMapper fileMapper;
    /**
     * 文件保存的目录
     */
    @Value("${saveFolder}")
    private String fileSaveFolder;
    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public RData<String> fileDelete(Integer id, String md5) {
        Integer count = fileMapper.deleteByIdAndMd5(id,md5);
        if (count != 0){
            log.info("数据库数据删除成功-#:{},md5:{}",id,md5);
            log.info("即将删除本地文件");
            File file = new File(fileSaveFolder);
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (file1.getName().equals(md5)){
                    if (file1.delete()){
                        log.info("本地文件删除成功-md5:{}",md5);
                        return RData.success("删除成功");
                    }else{
                        log.info("本地文件删除失败-md5:{}",md5);
                        return RData.failed(HttpCodes.HTTP_CODES500,"删除文件失败");
                    }
                }
            }
        }
        return RData.failed(HttpCodes.HTTP_CODES500,"删除文件失败");
    }
}
