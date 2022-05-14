package top.xinsin.dao;

import org.springframework.stereotype.Repository;
import top.xinsin.pojo.FileObject;

/**
 * @author xinxin
 * @date 2022/1/2 15:11
 * @version 1.0
 */

@Repository
public interface FileUploadMapper {
    void addFile(FileObject fileObject);
}
