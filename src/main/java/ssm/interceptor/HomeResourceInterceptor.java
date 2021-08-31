package ssm.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ssm.entity.Category;
import ssm.entity.Menu;
import ssm.service.CategoryService;
import ssm.service.MenuService;
import ssm.service.SysConfigService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author chen
 */
@Component
public class HomeResourceInterceptor implements HandlerInterceptor {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MenuService menuService;

    /**
     * 在请求处理之前执行，该方法主要是用于准备资源数据的，然后可以把它们当做请求属性放到WebRequest中
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws IOException {

        // 菜单显示
        List<Menu> menuList = menuService.listMenu(1);
        request.setAttribute("menuList", menuList);
        //分类显示
        List<Category> categoryList = categoryService.listCategory();
        request.setAttribute("allCategoryList", categoryList);

        //页脚显示
        //博客基本信息显示(configs)调用配置
        Map<String, String> configs = sysConfigService.getConfigs();
        request.setAttribute("configs", configs);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)  {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e)  {

    }
}