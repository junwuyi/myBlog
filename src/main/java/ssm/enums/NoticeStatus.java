package ssm.enums;

/**
 * @author chen
 * @date 2019/8/17 下午4:47
 */

public enum NoticeStatus {

    RELEASE(1, "已发布"),
    NOT_RELEASE(0, "未发布");

    private Integer value;

    private String message;

    NoticeStatus(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
