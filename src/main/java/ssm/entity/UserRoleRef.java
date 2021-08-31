package ssm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserRoleRef implements Serializable {

    private static final long serialVersionUID = 5091220066943292041L;

    private Integer id;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 角色Id
     */
    private Integer roleId;

    private Date createTime;

    private Date updateTime;

    public UserRoleRef(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRoleRef() {
    }
}