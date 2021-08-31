package ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.entity.Role;
import ssm.mapper.RoleMapper;
import ssm.service.RoleService;

import java.util.List;

/**
 * @author chen
 * @create 2019-07-26 10:31
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<Role> findAll() {
        //获得角色
        List<Role> roles = roleMapper.findAll();
        //封装count
        roles.forEach(role -> role.setCount(countUserByRoleId(role.getRoleId())));

        return roles;
    }

    @Override
    public List<Role> findOtherRoles(Integer userId) {

        //获得角色
        List<Role> roles = roleMapper.findOtherRoles(userId);
        //封装count
        roles.forEach(role -> role.setCount(countUserByRoleId(role.getRoleId())));
        return roles;
    }

    /**
     * 统计某个角色的用户数
     *
     * @param roleId 角色Id
     * @return 用户数
     */
    public Integer countUserByRoleId(Integer roleId) {
        return roleMapper.countUserByRoleId(roleId);
    }
}
