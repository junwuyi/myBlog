package ssm.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author chen
 * @create 2019-07-16 21:01
 */
@Controller
public class LoginController {

    /**
     * 登录页面显示
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
       // model.addAttribute("site_title", SiteTitleEnum.LOGIN_PAGE.getTitle());
       // return "home/login/login";
        return "admin/login";
    }
}
