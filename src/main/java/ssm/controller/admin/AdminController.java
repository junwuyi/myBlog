package ssm.controller.admin;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ssm.entity.SysLog;
import ssm.entity.User;
import ssm.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static ssm.util.MyUtils.getIpAddr;

/**
 * @author chen
 * @create 2019-07-05 18:11
 * 后台首页控制器
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private AttachmentService attachmentService;


    @RequestMapping(value = "")
    public String index(Model model) {
        System.out.println("请求首页");
        //获得网站概况
        List<String> siteBasicStatistics = new ArrayList<String>();
        //文章总数（后台为所有的文章数，前台时为发布状态的）
        siteBasicStatistics.add(articleService.countArticle(null) + "");
        //用户总数
        siteBasicStatistics.add(userService.countUser() + "");
        //附件总数
        siteBasicStatistics.add(attachmentService.getCount() + "");
        //评论总数
        siteBasicStatistics.add(articleService.countArticleComment() + "");
        model.addAttribute("siteBasicStatistics", siteBasicStatistics);

        //最新的8条日志
        List<SysLog> sysLogs = sysLogService.findLogLimit(8);
        model.addAttribute("sysLogs", sysLogs);

        return "admin/admin_index";
    }

    @RequestMapping("/uedit")
    public String ueditor() {
        return "admin/article/ueditor";
    }

    /**
     * 登录页面显示
     *
     * @return
     */
  /*  @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }*/

    /**
     * 验证登录信息
     * @param loginName
     * @param loginPwd
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getLogin", method = RequestMethod.POST)
    @ResponseBody
    public String getLogin(@ModelAttribute("username") String loginName,
                               @ModelAttribute("password") String loginPwd, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();

        //String username = request.getParameter("loginName");
        //String password = request.getParameter("loginPwd");
        //String rememberme = request.getParameter("rememberme");
        User user = userService.getUserByNameOrEmail(loginName);
        if(user ==null) {
            map.put("code",0);
            map.put("msg","用户名无效！");
            //return new JsonResult(ResultCodeEnum.FAIL.getCode(), "用户名无效");
        } else if(!user.getUserPass().equals(loginPwd)) {
            map.put("code",0);
            map.put("msg","密码错误！");
            //return new JsonResult(ResultCodeEnum.FAIL.getCode(), "密码错误！");
        } else {
            //登录成功
            map.put("code",1);
            map.put("msg","登录成功");
            //添加session
            request.getSession().setAttribute("user", user);
            //添加cookie
          /*  if(rememberme!=null) {
                //创建两个Cookie对象
                Cookie nameCookie = new Cookie("username", username);
                //设置Cookie的有效期为3天
                nameCookie.setMaxAge(60 * 60 * 24 * 3);
                Cookie pwdCookie = new Cookie("password", password);
                pwdCookie.setMaxAge(60 * 60 * 24 * 3);
                response.addCookie(nameCookie);
                response.addCookie(pwdCookie);
            }*/
            user.setUserLastLoginTime(new Date());
            user.setUserLastLoginIp(getIpAddr(request));
            userService.updateUser(user);

        }
        String result = new JSONObject(map).toString();
        return result;
        //return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "登录成功");
    }

}
