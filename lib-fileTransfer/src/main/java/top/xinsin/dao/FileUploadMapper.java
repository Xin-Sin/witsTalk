package top.xinsin.dao;

import org.springframework.stereotype.Repository;
import top.xinsin.pojo.FileObject;

/**
 * @Author xinxin
 * @Date 2022/1/2 15:11
 * @Version 1.0
 */

@Repository
public interface FileUploadMapper {
    void addFile(FileObject fileObject);
}
