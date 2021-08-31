package ssm.mapper;

import org.apache.ibatis.annotations.Param;
import ssm.entity.Role;

import java.util.List;

/**
 * @author chen
 * @create 2019-07-26 10:19
 */
public interface RoleMapper {

    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();

    /**
     * 统计某个角色的用户数
     *
     * @param roleId 角色Id
     * @return 用户数
     */
    Integer countUserByRoleId(@Param("roleId") Integer roleId);

    /**
     * 查询出用户没有的角色
     * @param userId
     * @return
     */
    List<Role> findOtherRoles(@Param("userId") Integer userId);
}
