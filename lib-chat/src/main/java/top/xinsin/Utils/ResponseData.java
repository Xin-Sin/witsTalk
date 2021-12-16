package top.xinsin.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import top.xinsin.enums.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * @Auther wzp
 * @Date 2021/12/16 20:39
 * @Version 1.0
 */
@Data
public class ResponseData {
    private JSONObject data;
    private JSONArray array;
    private String content;
    private HttpStatus Status;
    public ResponseData(JSONObject data){
        this.data = data;
        this.Status = HttpStatus.OK;
    }
    public ResponseData(JSONArray data){
        this.array = data;
        this.Status = HttpStatus.OK;
    }
    public ResponseData(Map data){
        this.data = JSONObject.parseObject(JSON.toJSONString(data));
        this.Status = HttpStatus.OK;
    }
    public ResponseData(List data){
        this.array = JSONArray.parseArray(JSON.toJSONString(data));
        this.Status = HttpStatus.OK;
    }
    public ResponseData(String data){
        this.content = data;
        this.Status = HttpStatus.OK;
    }
    public ResponseData(JSONObject data,HttpStatus status){
        this.data = data;
        this.Status = status;
    }
    public ResponseData(JSONArray data,HttpStatus status){
        this.array = data;
        this.Status = status;
    }
    public ResponseData(Map data,HttpStatus status){
        this.data = JSONObject.parseObject(JSON.toJSONString(data));
        this.Status = status;
    }
    public ResponseData(List data,HttpStatus status){
        this.array = JSONArray.parseArray(JSON.toJSONString(data));
        this.Status = status;
    }
    public ResponseData(String data,HttpStatus status){
        this.content = data;
        this.Status = status;
    }

    public ResponseData(HttpStatus status){
        this.data = null;
        this.array = null;
        this.content = null;
        this.Status = status;
    }
    public ResponseData(Object data,HttpStatus status){
        this.content = data.toString();
        this.Status = status;
    }

    public ResponseData(){
        this.data = null;
        this.array = null;
        this.content = null;
        this.Status = HttpStatus.OK;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",this.Status.code);
        jsonObject.put("msg",this.Status.status);
        if(this.data != null) {
            jsonObject.put("data", data);
        }else if(this.array != null){
            jsonObject.put("data",array);
        }else if(this.content != null){
            jsonObject.put("data",content);
        }
        return jsonObject.toJSONString();
    }
}
