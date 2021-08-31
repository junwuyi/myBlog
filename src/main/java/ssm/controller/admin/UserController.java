package ssm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.entity.Role;
import ssm.entity.User;
import ssm.enums.ResultCodeEnum;
import ssm.mapper.ArticleMapper;
import ssm.mapper.UserRoleRefMapper;
import ssm.service.RoleService;
import ssm.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @author chen
 */
@Controller
@Slf4j
@RequestMapping("/admin/user")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleRefMapper userRoleRefMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired(required = false)
    private ArticleMapper articleMapper;


    /**
     * 后台用户列表显示
     *
     * @return
     */
    @BussinessLog("用户列表")
    @RequestMapping(value = "")
    public ModelAndView userList() {
        ModelAndView modelandview = new ModelAndView();

        List<User> userList = userService.listUser();
        modelandview.addObject("userList", userList);

        modelandview.setViewName("admin/user/userList");
        return modelandview;

    }

    /**
     * 后台添加用户页面显示
     *
     * @return
     */
    @RequestMapping(value = "/insert")
    public ModelAndView insertUserView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/user/insert");
        return modelAndView;
    }

    /**
     * 检查用户名是否存在
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
    @ResponseBody
    public String checkUserName(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String username = request.getParameter("username");
        User user = userService.getUserByName(username);
        int id = Integer.valueOf(request.getParameter("id"));
        //用户名已存在,但不是当前用户(编辑用户的时候，不提示)
        if (user != null) {
            if (user.getUserId() != id) {
                map.put("code", 1);
                map.put("msg", "用户名已存在！");
            }
        } else {
            map.put("code", 0);
            map.put("msg", "");
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    /**
     * 检查Email是否存在
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkUserEmail", method = RequestMethod.POST)
    @ResponseBody
    public String checkUserEmail(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String email = request.getParameter("email");
        User user = userService.getUserByEmail(email);
        int id = Integer.valueOf(request.getParameter("id"));
        //用户名已存在,但不是当前用户(编辑用户的时候，不提示)
        if (user != null) {
            if (user.getUserId() != id) {
                map.put("code", 1);
                map.put("msg", "电子邮箱已存在！");
            }
        } else {
            map.put("code", 0);
            map.put("msg", "");
        }
        String result = new JSONObject(map).toString();
        return result;
    }


    /**
     * 后台添加用户页面提交
     *
     * @param user
     * @return
     */
    @BussinessLog("添加用户")
    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertUserSubmit(User user) {
        User user2 = userService.getUserByName(user.getUserName());
        User user3 = userService.getUserByEmail(user.getUserEmail());
        if (user2 == null && user3 == null) {
            user.setUserRegisterTime(new Date());
            user.setUserStatus(1);
            userService.insertUser(user);
        }
        return "redirect:/admin/user";
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    @BussinessLog("删除用户")
    public JsonResult deleteUser(@PathVariable("id") Integer id) {

        try {
            //1.检查用户有没有文章
            Integer articleCount = articleMapper.countArticleByUser(id);
            //2.检查用户有没有评论
            //Integer commentCount = postService.countByUserId(userId);
            if (articleCount > 0 /*|| commentCount > 0*/) {
                //Object[] args = {postCount, commentCount};
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败");
            }
            userService.removeUser(id);
        } catch (Exception e) {
            log.info("删除失败 {}" + e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
    }


    /**
     * 批量更新用户状态
     *
     * @param items
     * @param userStatus
     * @return
     */
    @RequestMapping(value = "/updateBatch", method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog("批量更新用户状态")
    public JsonResult updateBatch(@RequestParam("items") String items, Integer userStatus) {
        List<String> itemsList = new ArrayList<>();
        String[] strs = items.split(",");
        for (String str : strs) {
            itemsList.add(str);
        }
        try {
            if (itemsList != null && itemsList.size() > 0) {
                userService.updateBatch(itemsList, userStatus);
            }
        } catch (Exception e) {
            log.info("批量更新用户状态失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
    }


    /**
     * 编辑用户页面显示
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/edit")
    public ModelAndView editUserView(@RequestParam Integer userId) {
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.getUserById(userId);
        modelAndView.addObject("user", user);

        modelAndView.setViewName("admin/user/edit");
        return modelAndView;
    }


    /**
     * 新增/修改用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult editUserSubmit(User user) {

        try {
            //1.添加/更新用户名检查用户名是否存在 //root
            User nameCheck = userService.getUserByName(user.getUserName());
            if ((user.getUserId() == null && nameCheck != null) || (user.getUserId() != null && !Objects.equals(nameCheck.getUserId(), user.getUserId()))) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "用户名已存在");
            }
            //2.添加/更新用户名检查用户名是否存在
            User emailCheck = userService.getUserByEmail(user.getUserEmail());
            if ((user.getUserId() == null && emailCheck != null) || (user.getUserId() != null && !Objects.equals(emailCheck.getUserId(), user.getUserId()))) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "邮箱已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String userPass = user.getUserPass();
        if (userPass != null) {
            String encodedPassword = bCryptPasswordEncoder.encode(userPass);
            user.setUserPass(encodedPassword);
        }
        if (user.getUserId() != null) {
            //更新用户
            userService.updateUser(user);
        } else {
            // 添加用户
            user.setUserRegisterTime(new Date());
            user.setUserStatus(1);
            userService.insertUser(user);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "添加成功");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
    }

    @RequestMapping("selectRole")
    public String selectRole(@RequestParam Integer userId, Model model) {
        //角色列表
        //List<Role> roles = roleService.findAll();

        //根据用户ID查询角色
        List<Role> roleList = userRoleRefMapper.findRoleByUserId(userId);

        //查询出用户没有的角色
        List<Role> otherRoles = roleService.findOtherRoles(userId);
        model.addAttribute("roles", otherRoles);

        User user = userService.getUserById(userId);
        model.addAttribute("user", user);

        return "admin/user/select-role";
    }

    //给用户添加角色
    @RequestMapping("/addRoleToUser")
    @ResponseBody
    @BussinessLog("给用户添加角色")
    public JsonResult addRoleToUser(@RequestParam(name = "userId", required = true) String userId, @RequestParam(name = "ids", required = true) String ids) {
        try {
            List<String> itemsList = new ArrayList<>();
            String[] strs = ids.split(",");
            for (String str : strs) {
                itemsList.add(str);
            }
            userService.addRoleToUser(userId, itemsList);

        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "添加失败");
        }

        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "添加成功");
    }

}
