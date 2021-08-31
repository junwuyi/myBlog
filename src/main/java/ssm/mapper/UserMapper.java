package ssm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ssm.entity.User;

import java.util.List;

/**
 * @author chen
 */
@Mapper
public interface UserMapper {

    /**
     * 根据ID删除
     * 
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteById(Integer userId);

    /**
     * 添加
     * 
     * @param user 用户
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 根据ID查询
     * 
     * @param userId 用户ID
     * @return 用户
     */
    User getUserById(Integer userId);

    /**
     * 更新
     * 
     * @param user 用户
     * @return 影响行数
     */
    int update(User user);


    /**
     * 获得用户列表
     * 
     * @return  用户列表
     */
    List<User> listUser() ;


    /**
     * 根据用户名或Email获得用户
     * 
     * @param str 用户名或Email
     * @return 用户
     */
    User getUserByNameOrEmail(String str) ;

    /**
     * 根据用户名查用户
     * 
     * @param name 用户名
     * @return 用户
     */
    User getUserByName(String name) ;

    /**
     * 根据Email查询用户
     * 
     * @param email 邮箱
     * @return 用户
     */
    User getUserByEmail(String email) ;

    /**
     * 根据文章id 查询用户名
     * @return 用户名
     */
    String getUserNameByArticleId(Integer articleId);

    /**
     * 根据用户编号和密码查询
     *
     * @param userId   userId
     * @param oldPwd userPass
     * @return User
     */
    User findByUserIdAndUserPass(@Param("userId") Integer userId, @Param("oldPwd") String oldPwd);

    /**
     * 批量更新用户状态
     * @param ids
     * @param userStatus
     */
    Integer updateBatch(@Param("ids") List<String> ids, @Param("userStatus") Integer userStatus);

    /**
     * 统计用户数量
     * @return
     */
    Integer countUser();
}