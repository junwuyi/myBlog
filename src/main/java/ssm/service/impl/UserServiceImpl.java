package ssm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ssm.entity.Role;
import ssm.entity.SysLog;
import ssm.entity.User;
import ssm.enums.UserStatusEnum;
import ssm.mapper.ArticleMapper;
import ssm.mapper.UserMapper;
import ssm.mapper.UserRoleRefMapper;
import ssm.service.SysLogService;
import ssm.service.UserService;
import ssm.util.MyUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 *
 * @author chen
 * @date 2019/8/24
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private UserRoleRefMapper userRoleRefMapper;

    @Override
    @Cacheable(value = "users", key = "'list_user'")
    public List<User> listUser() {
        List<User> userList = userMapper.listUser();
        for (int i = 0; i < userList.size(); i++) {
            Integer articleCount = articleMapper.countArticleByUser(userList.get(i).getUserId());
            userList.get(i).setArticleCount(articleCount);
            //封装角色集合
            List<Role> roles = userRoleRefMapper.findRoleByUserId(userList.get(i).getUserId());
            userList.get(i).setRoles(roles);
        }
        return userList;
    }

    @Override
    @Cacheable(value = "users", key = "'user_id_'+#id")
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    @CacheEvict(value = "users", allEntries = true, key = "'user_'+#user.userId")
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public User insertUser(User user) {
        user.setUserRegisterTime(new Date());
        userMapper.insert(user);
        return user;
    }

    @Override
    @Cacheable(value = "users", key = "'user_str_'+#str")
    public User getUserByNameOrEmail(String str) {
        return userMapper.getUserByNameOrEmail(str);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    /**
     * 根据用户名(邮箱)和密码查询用户
     *
     * @param str
     * @param pwd
     * @return
     */
    @Override
    public User getUser(String str, String pwd) {
        return null;
    }

    /**
     * 根据用户编号和密码查询
     *
     * @param userId userid
     * @param oldPwd userpass
     * @return User
     */
    @Override
    public User findByUserIdAndUserPass(Integer userId, String oldPwd) {
        return userMapper.findByUserIdAndUserPass(userId, oldPwd);
    }

    /**
     * 修改密码
     * @param userId
     * @param password
     */
    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void updatePassword(Integer userId, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setUserPass(password);
        userMapper.update(user);
    }

    /**
     * 批量更新用户状态
     * @param itemsList
     * @param userStatus
     */
    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void updateBatch(List<String> itemsList, Integer userStatus) {
        userMapper.updateBatch(itemsList, userStatus);
    }

    /**
     * 移除用户没有真的删除
     * @param userId
     */
    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void removeUser(Integer userId) {
        //删除用户
        User user = getUserById(userId);
        if (user != null) {
            //1.修改用户状态为已删除
            user.setUserStatus(UserStatusEnum.DELETED.getCode());
            user.setUserName("deleted-" + System.currentTimeMillis() + "-" + user.getUserName());
            user.setUserNickname("已删除-" + user.getUserNickname());
            user.setUserEmail("deleted-" + user.getUserEmail());
            userMapper.update(user);
            //2.修改用户和角色关联
           // roleService.removeByUserId(userId);
            //3.文章删除
           // postService.removeByUserId(userId);
            //4.评论删除
           // commentService.removeByUserId(userId);
        }
    }

    /**
     * 统计用户数量
     * @return
     */
    @Override
    @Cacheable(value = "users", key = "'count_user'")
    public Integer countUser() {
        return userMapper.countUser();
    }

    /**
     * 给用户添加角色
     * @param userId
     * @param roleIds
     */
    @Override
    @CacheEvict(value = "users", allEntries = true, beforeInvocation = true)
    public void addRoleToUser(String userId, List<String> roleIds) {
        for(String roleId:roleIds){
            userRoleRefMapper.addRoleToUser(userId,roleId);
        }
    }

    /**
     * spring security 的登录验证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.springframework.security.core.userdetails.User user = null;
        try {
            user = null;
            //查询用户
            User loginUser = getUserByNameOrEmail(username);
            if (loginUser == null) {
                //自定义异常
                throw new AuthenticationServiceException(
                        "用户名不存在");
            }
            //根据用户ID查询角色
            List<Role> roleList = userRoleRefMapper.findRoleByUserId(loginUser.getUserId());

            //处理自己的用户对象封装成UserDetails "{noop}" + loginUser.getUserPass()加"{noop}"表示不加密登录
            user = new org.springframework.security.core.userdetails.User(loginUser.getUserName(), loginUser.getUserPass(), loginUser.getUserStatus() == 1 ? true : false, true, true, true, getAuthority(roleList));
            if (user != null) {
                //登录成功记录日志
                //将日志信息封装到SysLog对象
                SysLog sysLog = new SysLog();
                sysLog.setExecutionTime(0l);//执行时长
                sysLog.setIp(MyUtils.getIpAddr(request));
                sysLog.setContent("登录后台");
                sysLog.setUrl("/admin/login");
                sysLog.setUserName(loginUser.getUserNickname());
                sysLog.setVisitTime(new Date());

                //调用service完成记录日志到数据库
                sysLogService.save(sysLog);

                //添加session
                request.getSession().setAttribute("user", loginUser);
            }
            /*if (user == null) {
                throw new BadCredentialsException("用户名或密码错误!");
            }*/
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }
        return user;
    }

    //作用就是返回一个List集合，集合中装入的是角色名称
    private List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return list;
    }


   /* List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    List<String> roles = user.getRoles();
        for (String roleName : roles) {
        Role role = mongoTemplate.findOne(Query.query(Criteria.where("name").is(roleName)), Role.class);
        if (role == null) {
            continue;
        }
        for (JsonPermissions.SimplePermission permission : role.getPermissions()) {
            for (String privilege : permission.getPrivileges().keySet()) {
                authorities.add(new SimpleGrantedAuthority(String.format("%s-%s", permission.getResourceId(), privilege)));
            }
        }
    }*/

}
