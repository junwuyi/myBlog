package ssm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ssm.entity.Role;
import ssm.service.RoleService;

import java.util.List;

/**
 * <pre>
 *     后台角色管理控制器
 * </pre>
 *
 * @author : chen
 * @date : 2019/8/10
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/role")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class RoleController {

    @Autowired
    private RoleService roleService;

  /*  @Autowired
    private PermissionService permissionService;

    @Autowired
    private LocaleMessageUtil localeMessageUtil;*/

    /**
     * 查询所有角色并渲染role页面
     *
     * @return 模板路径admin/role/roleList
     */
    @RequestMapping(value = "")
    public String roles(Model model) {
        //角色列表
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        //封装权限
       // roles.forEach(role -> role.setPermissions(permissionService.listPermissionsByRoleId(role.getId())));
        return "admin/role/roleList";
    }

    /**
     * 新增/修改角色
     *
     * @param role role对象
     * @return 重定向到/admin/role
     *//*
    @PostMapping(value = "/save")
    public String saveRole(@ModelAttribute Role role,
                           @RequestParam(value = "ids", required = false) List<Integer> permissionList) {
        try {
            if (permissionList != null && permissionList.size() != 0) {
                List<Permission> permissions = new ArrayList<>(permissionList.size());
                for (Integer permissionId : permissionList) {
                    permissions.add(new Permission(permissionId));
                }
                role.setPermissions(permissions);
            }
            roleService.saveByRole(role);
        } catch (Exception e) {
            log.error("修改角色失败：{}", e.getMessage());
        }
        return "redirect:/admin/role";
    }

    *//**
     * 删除角色
     *
     * @param roleId 角色Id
     * @return JsonResult
     *//*
    @GetMapping(value = "/remove")
    @ResponseBody
    public JsonResult checkDelete(@RequestParam("roleId") Integer roleId) {
        //判断这个角色有没有用户
        Integer userCount = roleService.countUserByRoleId(roleId);
        if (userCount != 0) {
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.role.delete-failed"));
        }
        roleService.removeByRoleId(roleId);
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "");
    }


    *//**
     * 跳转到修改页面
     *
     * @param roleId roleId
     * @param model  model
     * @return 模板路径admin/admin_role
     *//*
    @GetMapping(value = "/edit")
    public String toEditRole(Model model, @RequestParam("roleId") Integer roleId) {
        //更新的角色
        Role role = roleService.findByRoleId(roleId);
        //当前角色的权限列表
        role.setPermissions(permissionService.listPermissionsByRoleId(roleId));
        model.addAttribute("updateRole", role);

        //所有权限
        List<Permission> permissions = permissionService.findAll();
        model.addAttribute("permissions", permissions);
        return "admin/admin_role_edit";
    }*/
}
