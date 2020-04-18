package com.sizaif.emsdemo.Result;
/*================================================================
 * 说明：业务操作响应结果
 * 作者          时间            注释
 * SIZ       2020.03.01	        创建
 *
 ==================================================================*/
public class SystemResult {

    /**
     * 200 为操作成功
     * 100 为数据校验异常或失败
     * 404 为找不到路径
     * 500 为程序内部错误
     */
    private Integer status;

    /**
     * 消息信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object data;

    public SystemResult() {
    }
    public SystemResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    public SystemResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public SystemResult(Object data) {
        this.status = 200;
        this.msg = "操作成功";
        this.data = data;
    }

    /**
     * 构建响应
     * @param status
     * @param msg
     * @param data
     * @return
     */
    public static SystemResult build(Integer status, String msg, Object data) {
        return new SystemResult(status, msg, data);
    }

    /**
     * 成功返回200 操作成功, 带数据
     * @param data
     * @return
     */
    public static SystemResult Ok(Object data){ return new SystemResult(data);}

    public static SystemResult Ok() {return new SystemResult(null);}
}
