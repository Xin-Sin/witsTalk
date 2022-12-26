package top.xinsin.pojo;

import lombok.Data;

/**
 * @author xinsin
 * Created On 2022/12/24 16:03
 * @version 1.0
 */
@Data
public class Route {
    private Integer id;
    private String path;
    private String name;
    private String parentId;
    private String component;
    private String title;
    private String auth;
    private Integer status;
    private String remark;
    private String createId;
    private String createTime;
    private String updateId;
    private String updateTime;
}
