package top.xinsin.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.xinsin.pojo.FileObject;

import java.util.ArrayList;

/**
 * @author xinxin
 * Created On 2021/12/23 19:07
 * @version 1.0
 */
@Repository
public interface FileDownloadMapper {
    /**
     * 获取原始文件名称
     * @param fileObject 文件对象
     * @return 文件名称
     */
    String getFileName(FileObject fileObject);

    /**
     * 获取所有文件的名称
     *
     * @return 文件名称列表
     */
    ArrayList<FileObject> getAllFileNames(@Param("min_id")Integer minId,@Param("pageSize")Integer pageSize);

    Integer selectFileCount();
}
