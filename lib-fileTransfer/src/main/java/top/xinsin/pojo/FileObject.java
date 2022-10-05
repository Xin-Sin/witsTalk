package top.xinsin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wzp
 * Created On 2021/12/24 14:48
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class FileObject {
    private int id;
    private long size;
    private String name;
    private String md5;
    private String uploadTime;
    public FileObject(String name, long size, String md5){
        this.name = name;
        this.size = size;
        this.md5 = md5;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.uploadTime = simpleDateFormat.format(new Date());
    }
    public FileObject(String md5){
        this.md5 = md5;
    }
}
