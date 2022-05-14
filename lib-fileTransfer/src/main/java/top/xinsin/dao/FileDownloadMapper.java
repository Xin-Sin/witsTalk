package top.xinsin.dao;

import org.springframework.stereotype.Repository;
import top.xinsin.pojo.FileObject;

import java.util.ArrayList;

/**
 * @author xinxin
 * @date 2021/12/23 19:07
 * @version 1.0
 */
@Repository
public interface FileDownloadMapper {
    String getFileName(FileObject fileObject);
    ArrayList<FileObject> getAllFileNames();
}
