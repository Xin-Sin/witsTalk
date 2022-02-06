package cn.wzpmc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class HeadPortrait {
    private String username;
    private String base64;
}
