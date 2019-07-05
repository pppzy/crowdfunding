package com.itpzy.crowdfunding.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 异步请求完成后，用于返回的一个消息状态工具类
 */
public class AjaxResult {

    /**
     * 如果为true则执行成功，如果为false则执行失败
     */
    private boolean success;
    //存储的信息
    private String message;

    /**
     * 用于存储Control执行后要返回的数据
     */
    private Map<String,Object> dataMap = new HashMap<>();

    /**
     * 用于创建 执行状态为成功的工具类
     * @return
     */
    public static AjaxResult success(String message){
        AjaxResult result = new AjaxResult();
        result.setSuccess(true);
        result.setMessage(message);
        return result;
    }

    public static AjaxResult success(){
        AjaxResult result = new AjaxResult();
        result.setSuccess(true);
        return result;
    }

    /**
     * 用于创建 执行状态为失败的工具类
     * @return
     */
    public static AjaxResult fail(String message){
        AjaxResult result = new AjaxResult();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

    public static AjaxResult fail(){
        AjaxResult result = new AjaxResult();
        result.setSuccess(false);
        return result;
    }

    /**
     * 用于向工具类存储Map类型数据
     * @param key
     * @param value
     */
    public void add(String key, Object value){
        this.dataMap.put(key,value);
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
