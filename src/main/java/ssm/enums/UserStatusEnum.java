package ssm.enums;

/**
 * @author chen
 * @create 2019-07-25 16:57
 */
public enum UserStatusEnum {

    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 已禁用
     */
    BAN(2, "已禁用"),

    /**
     * 已删除
     */
    DELETED(0, "已删除");

    private Integer code;
    private String desc;

    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
