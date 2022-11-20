package top.xinsin.pojo;

import lombok.Data;

/**
 * @author wzp
 * Created On 2021/12/24 14:48
 * @version 1.0
 */
@Data
public class FileObject {
    private int id;
    private long size;
    private String name;
    private String md5;
    private String uploadTime;
    private String uploaderName;
    private int uploaderId;

    public FileObject(String name, long size, String md5, int uploadId) {
        this.name = name;
        this.size = size;
        this.md5 = md5;
        this.uploaderId = uploadId;
    }

    public FileObject(String md5) {
        this.md5 = md5;
    }

    public FileObject(int id, long size, String name, String md5, String uploadTime, String uploaderName) {
        this.id = id;
        this.size = size;
        this.name = name;
        this.md5 = md5;
        this.uploadTime = uploadTime;
        this.uploaderName = uploaderName;
    }
}
