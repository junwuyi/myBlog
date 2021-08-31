package ssm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chen
 */
@Data
public class Role implements Serializable  {

    private static final long serialVersionUID = 2233979806802117985L;

    /**
     * ID
     */
    private Integer roleId;

    /**
     * 角色名称：admin，root，subscriber
     */
    private String roleName;

    /**
     * 描述：管理员，作者，订阅者
     */
    private String roleDesc;

    /**
     * 级别，越小越大
     */
    private Integer roleLevel;

    /**
     * 该角色对应的用户数量，非数据库字段
     */
    private Integer count;

    /**
     * 当前角色的权限列表,非数据库字段
     */
    private List<Permission> permissions;

}