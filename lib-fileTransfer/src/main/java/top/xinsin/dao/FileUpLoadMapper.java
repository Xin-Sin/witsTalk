package top.xinsin.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.xinsin.pojo.FileObject;

/**
 * @Author xinxin
 * @Date 2021/12/23 19:07
 * @Version 1.0
 */
@Repository
public interface FileUpLoadMapper {
    String getFileName(FileObject fileObject);
    void addFile(FileObject fileObject);
}
