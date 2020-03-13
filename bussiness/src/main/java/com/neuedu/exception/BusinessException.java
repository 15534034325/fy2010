package com.neuedu.exception;

public class BusinessException extends RuntimeException {

    private String msg;
    private int status;

    public BusinessException(){
        super();
    }
    public BusinessException(int status,String msg){
        super(msg);
        this.status=status;
    }


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


}
