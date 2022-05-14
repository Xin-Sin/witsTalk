package top.xinsin.Utils;

import com.alibaba.fastjson.JSONObject;

public class SqlUtils {
    public static JSONObject insertOperate(int i){
        JSONObject jsonObject = new JSONObject();
        if (i >= 1){
            jsonObject.put("affected_rows",i);
        }else{
            jsonObject.put("affected rows","error");
        }
        return jsonObject;
    }
    public static JSONObject updateOperate(int i){
        JSONObject jsonObject = new JSONObject();
        if (i >= 0){
            jsonObject.put("affected_rows",i);
        }else{
            jsonObject.put("affected rows","error");
        }
        return jsonObject;
    }
}
