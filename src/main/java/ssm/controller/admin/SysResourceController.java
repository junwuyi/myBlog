package ssm.controller.admin;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ssm.entity.SysResources;
import ssm.service.ResourceService;

import java.util.List;

/**
 * @author chen
 * @create 2019-07-27 16:18
 */
@Controller
@RequestMapping(value = "admin/resources")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class SysResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("")
    public String getMenuTree(Model model){
        List<SysResources> all = resourceService.findAll();
        String s = JSON.toJSON(all).toString();
        /* System.out.println("---------"+menus);*/
        model.addAttribute("menus", s);

        return "admin/resources/list";
    }


}
