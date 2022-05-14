package top.xinsin.Utils;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author xinsin
 * @version 1.0.0
 * @date 2022/5/14
 */
public class SqlUtils {
    public static JSONObject insertOperate(int i){
        JSONObject jsonObject = new JSONObject();
        if (i >= 1){
            jsonObject.fluentPut("affected_rows",i);
        }else{
            jsonObject.fluentPut("affected rows","error");
        }
        return jsonObject;
    }
    public static JSONObject updateOperate(int i){
        JSONObject jsonObject = new JSONObject();
        if (i >= 0){
            jsonObject.fluentPut("affected_rows",i);
        }else{
            jsonObject.fluentPut("affected rows","error");
        }
        return jsonObject;
    }
}
