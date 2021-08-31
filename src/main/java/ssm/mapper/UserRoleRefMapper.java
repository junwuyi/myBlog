package ssm.mapper;

import org.apache.ibatis.annotations.Param;
import ssm.entity.Role;

import java.util.List;

/**
 * @author chen
 * @create 2019-07-20 21:31
 */
public interface UserRoleRefMapper {

    /**
     * 根据用户id查询所对应的角色
     *
     * @param userId
     * @return 角色列表
     */
    List<Role> findRoleByUserId(Integer userId);

    /**
     * 给用户添角色
     *
     * @param userId
     * @param roleId
     */
    void addRoleToUser(@Param("userId") String userId, @Param("roleId") String roleId);
}
