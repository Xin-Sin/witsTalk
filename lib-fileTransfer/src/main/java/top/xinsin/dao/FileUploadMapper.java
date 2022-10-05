package top.xinsin.dao;

import org.springframework.stereotype.Repository;
import top.xinsin.pojo.FileObject;

/**
 * @author xinxin
 * Created On 2022/1/2 15:11
 * @version 1.0
 */

@Repository
public interface FileUploadMapper {
    /**
     * 上传文件
     * @param fileObject 文件对象
     */
    void addFile(FileObject fileObject);
}
