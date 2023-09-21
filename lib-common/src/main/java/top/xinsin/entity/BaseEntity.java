package top.xinsin.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xinsin
 * Created On 2023/9/21 10:13
 * @version 1.0
 */
@Data
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2067385886549596679L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
    @Column(isLogicDelete = true)
    private Boolean delFlag;
}
