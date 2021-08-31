package ssm.enums;

/**
 * <pre>
 *     常用博客设置enum
 * </pre>
 *
 * @author
 * @date : 2019/7/14
 */
public enum BlogPropertiesEnum {

    /**
     * 附件存储位置
     */
    ATTACH_LOC("attach_loc");

    private String prop;

    BlogPropertiesEnum(String prop) {
        this.prop = prop;
    }

    public String getProp() {
        return prop;
    }
}
