package ssm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chen
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -4415517704211731385L;
    /**
     * 编号
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     * $2a$10$cuJgpvzCjiiij3cprghkUeYd1aAIzUvTL6GsugBvvkEqoVApPO7U2
     */
    private String userPass;

    /**
     * 显示名称
     */
    private String userNickname;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 说明
     */
    private String userDesc;

    private String userUrl;

    /**
     * 头像
     */
    private String userAvatar;

    private String userLastLoginIp;
    /**
     * 注册时间
     */
    private Date userRegisterTime;
    /**
     * 最后一次登录时间
     */
    private Date userLastLoginTime;

    /**
     * 0 已删除
     * 1 正常
     * 2 禁用
     */
    private Integer userStatus;

    /**
     * 文章数量（不是数据库字段）
     */
    private Integer articleCount;

    /**
     * 角色集合（不是数据库字段）
     */
    private List<Role> roles;

}