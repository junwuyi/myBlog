package ssm.service;

import ssm.entity.Role;

import java.util.List;

/**
 * @author chen
 * @create 2019-07-26 10:30
 */
public interface RoleService {

    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();

    /**
     * 查询出用户没有的角色
     * @param userId
     * @return
     */
    List<Role> findOtherRoles(Integer userId);
}
