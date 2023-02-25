package top.xinsin.dao;

import org.apache.ibatis.annotations.Mapper;
import top.xinsin.pojo.FileObject;

/**
 * @author xinsin
 * Created On 2022/12/22 11:23
 * @version 1.0
 */
@Mapper
public interface FileMapper {
    Integer deleteByIdAndMd5(Integer id, String md5);
    FileObject selectByMD5(String md5);

    void initTable();
}
