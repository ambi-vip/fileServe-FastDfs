package file.ambitlu.work.entity;

import lombok.Data;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/10 15:42
 * @description:
 */
public enum StatusCode {

    OK(0,"成功"),
    ERROR(20001,"失败"),
    LOGINERROR(20002,"用户名或密码错误"),
    ACCESSERROR(20003,"权限不足"),
    REMOTEERROR(20004,"远程调用失败"),
    REPERROR(20005,"重复操作"),
    NOTFOUNDERROR(20006,"没有对应的数据");//这个后面必须有分号
    private int code;
    private String name;
    private StatusCode(int code,String name) {
        this.code = code;
        this.name = name();
    }

    public int getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public void setName(String name) {
        this.name = name;
    }
}
