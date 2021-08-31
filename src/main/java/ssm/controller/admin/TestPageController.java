package ssm.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chen
 * @create 2019-08-04 16:58
 */
@Controller
@RequestMapping(value = "/admin/testPage")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class TestPageController {

    @RequestMapping(value = "icons")
    public String icon(){
        return "admin/testPage/icons";
    }
}
