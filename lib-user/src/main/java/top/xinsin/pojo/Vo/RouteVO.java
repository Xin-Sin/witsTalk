package top.xinsin.pojo.Vo;

import lombok.Data;

/**
 * @author xinsin
 * Created On 2022/12/24 16:26
 * @version 1.0
 */
@Data
public class RouteVO {
    private Integer id;
    private String path;
    private String name;
    private String parentId;
    private String component;
    private String title;
}
