package ssm.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ssm.entity.User;

import java.util.List;

/**
 * @author chen
 * @date 2017/8/24
 */

public interface UserService extends UserDetailsService {
    /**
     * 获得用户列表
     *
     * @return 用户列表
     */
    List<User> listUser();

    /**
     * 根据id查询用户信息
     *
     * @param id 用户ID
     * @return 用户
     */
    User getUserById(Integer id);

    /**
     * 修改用户信息
     *
     * @param user 用户
     */
    void updateUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Integer id);

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 用户
     */
    User insertUser(User user);

    /**
     * 根据用户名或邮箱查询用户
     *
     * @param str 用户名或Email
     * @return 用户
     */
    User getUserByNameOrEmail(String str);

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return 用户
     */
    User getUserByName(String name);

    /**
     * 根据邮箱查询用户
     *
     * @param email Email
     * @return 用户
     */
    User getUserByEmail(String email);

    /**
     * 查询用户是否存在
     * @param str
     * @param pwd
     * @return
     */
    User getUser(String str, String pwd);

    /**
     * 根据用户编号和密码查询
     *
     * @param userId   userid
     * @param oldPwd userpass
     * @return User
     */
    User findByUserIdAndUserPass(Integer userId, String oldPwd);

    /**
     * 修改密码
     * @param userId
     * @param password
     */
    void updatePassword(Integer userId, String password);

    /**
     * 批量更新用户状态
     * @param itemsList
     * @param userStatus
     */
    void updateBatch(List<String> itemsList, Integer userStatus);

    /**
     * 移除用户没有真的删除
     * @param userId
     */
    void removeUser(Integer userId);

    Integer countUser();

    /**
     * 给用户添加角色
     * @param userId
     * @param roleIds
     */
    void addRoleToUser(String userId, List<String> roleIds);
}
