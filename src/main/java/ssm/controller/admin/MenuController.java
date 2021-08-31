package ssm.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ssm.dto.JsonResult;
import ssm.entity.Menu;
import ssm.enums.MenuType;
import ssm.enums.ResultCodeEnum;
import ssm.service.MenuService;

import java.util.List;

/**
 * @author chen
 */
@Controller
@RequestMapping("/admin/menu")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 后台菜单列表显示
     *
     * @return
     */
    @RequestMapping(value = "")
    public String menuList(Model model)  {
        List<Menu> menuList = menuService.listMenu(null);
        model.addAttribute("menuList",menuList);
        return "admin/testPage/menu-list";
    }

    /**
     * 添加菜单内容提交
     *
     * @param menu
     * @return
     */
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult insertMenuSubmit(Menu menu)  {
        try {
            if(menu.getMenuType() == null) {
                menu.setMenuType(MenuType.TOP_MENU.getValue());
            }
            menuService.insertMenu(menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "添加失败");
        }

        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "添加成功");
    }

    /**
     * 删除菜单内容
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteMenu(@PathVariable("id") Integer id)  {
        menuService.deleteMenu(id);
        return "redirect:/admin/menu";
    }

    /**
     * 编辑菜单内容显示
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editMenuView(@PathVariable("id") Integer id)  {
        ModelAndView modelAndView = new ModelAndView();

        Menu menu =  menuService.getMenuById(id);
        modelAndView.addObject("menu",menu);

        List<Menu> menuList = menuService.listMenu(null);
        modelAndView.addObject("menuList",menuList);

        modelAndView.setViewName("admin/menu/menu-edit");
        return modelAndView;
    }


    /**
     * 编辑菜单内容提交
     *
     * @param menu
     * @return
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult editMenuSubmit(Menu menu)  {
        try {
            menuService.updateMenu(menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改失败");
        }

        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
    }



}
