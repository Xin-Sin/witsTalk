package top.xinsin.Utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * @author wzp
 * @date 2021/12/16 20:39
 * @version 1.0
 */
@Data
public class ResponseData {
    private JSONObject data;
    private JSONArray array;
    private String content;
    private Integer num;
    private HttpStatus status;

    public ResponseData(JSONObject data){
        this.data = data;
        this.status = HttpStatus.OK;
    }

    public ResponseData(JSONArray data){
        this.array = data;
        this.status = HttpStatus.OK;
    }

    public ResponseData(Map<?,?> data){
        this.data = JSON.parseObject(JSON.toJSONString(data));
        this.status = HttpStatus.OK;
    }

    public ResponseData(List<?> data){
        this.array = JSON.parseArray(JSON.toJSONString(data));
        this.status = HttpStatus.OK;
    }

    public ResponseData(String data){
        this.content = data;
        this.status = HttpStatus.OK;
    }

    public ResponseData(Integer data){
        this.num = data;
        this.status = HttpStatus.OK;
    }

    public ResponseData(int data){
        this.num = data;
        this.status = HttpStatus.OK;
    }

    public ResponseData(Integer data,HttpStatus status) {
        this.num = data;
        this.status = status;
    }

    public ResponseData(int data,HttpStatus status){
        this.num = data;
        this.status = status;
    }

    public ResponseData(JSONObject data,HttpStatus status){
        this.data = data;
        this.status = status;
    }

    public ResponseData(JSONArray data,HttpStatus status){
        this.array = data;
        this.status = status;
    }

    public ResponseData(Map<?,?> data,HttpStatus status){
        this.data = JSON.parseObject(JSON.toJSONString(data));
        this.status = status;
    }

    public ResponseData(List<?> data,HttpStatus status){
        this.array = JSON.parseArray(JSON.toJSONString(data));
        this.status = status;
    }

    public ResponseData(String data,HttpStatus status){
        this.content = data;
        this.status = status;
    }

    public ResponseData(HttpStatus status){
        this.data = null;
        this.array = null;
        this.content = null;
        this.num = null;
        this.status = status;
    }

    public ResponseData(Object data,HttpStatus status){
        this.content = data.toString();
        this.status = status;
    }

    public ResponseData(Object data){
        this.content = data.toString();
        this.status = HttpStatus.OK;
    }

    public ResponseData(){
        this.data = null;
        this.array = null;
        this.content = null;
        this.status = HttpStatus.OK;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("status",this.status.value())
                .fluentPut("msg",this.status.getReasonPhrase());
        if(this.data != null) {
            jsonObject.fluentPut("data", data);
        }else if(this.array != null){
            jsonObject.fluentPut("data",array);
        }else if(this.content != null){
            jsonObject.fluentPut("data",content);
        }else if(this.num != null){
            jsonObject.fluentPut("data",num);
        }
        return jsonObject.toJSONString();
    }
}
