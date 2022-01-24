package top.xinsin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

/**
 * @Auther wzp
 * @Date 2021/12/24 14:48
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class FileObject {
    private int id;
    private long size;
    private String name;
    private String md5;
    private Date uploadTime;
    private String uploadName;
    public FileObject(String name, long size, String md5){
        this.name = name;
        this.size = size;
        this.md5 = md5;
    }
    public FileObject(String md5){
        this.md5 = md5;
    }
}
