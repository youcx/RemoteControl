package com.newtrekwang.remotecontrol.bean;
/**
 * auther    : WJX
 * classname : Result
 * desc      : json统一结果类
 * createTime: 2017/7/24 14:29
 */
public class Result<T> {
    private String msg;
    private int status;
    private T result;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", status=" + status +
                ", result=" + result +
                '}';
    }
}
