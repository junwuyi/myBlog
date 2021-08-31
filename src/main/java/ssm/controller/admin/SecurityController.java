package ssm.controller.admin;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chen
 * @create 2019-08-06 22:30
 */
@RestController
public class SecurityController {
    @RequestMapping("getUsername")
    public String getUsername(){
        //得到当前认证对象的用户名并返回
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
