package ssm.controller.admin;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.entity.Category;
import ssm.enums.ResultCodeEnum;
import ssm.service.ArticleService;
import ssm.service.CategoryService;

import java.util.List;


/**
 * @author chen
 */
@Controller
@Slf4j
@RequestMapping("/admin/category")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class CategoryController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 后台分类列表显示
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView categoryList()  {
        ModelAndView modelandview = new ModelAndView();
        //获得分类列表和该分类的文章数量
        List<Category> categoryList = categoryService.listCategoryWithCount();
        modelandview.addObject("categoryList",categoryList);
        modelandview.setViewName("admin/category/categoryList");
        return modelandview;

    }


    /**
     * 后台添加分类提交
     *
     * @param category
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog("添加分类")
    public JsonResult insertCategorySubmit(Category category) {
        try {
            //检查分类是否存在
            if (categoryService.getCategoryByName(category.getCategoryName()) != null) {
                //存在
                log.error("该分类存在：{}");
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "分类存在");
            }
            categoryService.insertCategory(category);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加失败：{}");
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "添加失败");
        }
    }

    /**
     * 删除分类
     *
     * @param categoryId
     * @return
     */
    @BussinessLog("删除分类")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteCategory(@RequestParam Integer categoryId)  {
        //禁止删除有文章的分类
        Integer count = articleService.countArticleByCategoryId(categoryId);
        //有文章
        if (count != 0) {
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "该分类暂有文章不可删除");
        }

        Integer childCount = categoryService.findChildCategory(categoryId).size();
        if (childCount != 0) {
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "该分类暂含有子分类不可删除");
        }
        categoryService.deleteCategory(categoryId);
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
    }

    /**
     * 编辑分类页面显示
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editCategoryView(@PathVariable("id") Integer id)  {
        ModelAndView modelAndView = new ModelAndView();

        Category category =  categoryService.getCategoryById(id);
        if (category == null) {
            //throw new RuntimeException("没有获取到分类");
            log.error("没有获取到分类");
        }
        modelAndView.addObject("category",category);

        List<Category> categoryList = categoryService.listCategoryWithCount();
        if (categoryList == null) {
           // throw new RuntimeException("没有获取到分类和该分类的文章数");
            log.error("没有获取到分类和该分类的文章数");
        }
        modelAndView.addObject("categoryList",categoryList);

        modelAndView.setViewName("admin/category/categoryEdit");
        return modelAndView;
    }

    /**
     * 编辑分类提交
     *
     * @param category 分类
     * @return 重定向
     */
    @BussinessLog("编辑分类")
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult editCategorySubmit(Category category)  {

        try {
            categoryService.updateCategory(category);
        } catch (Exception e) {
            log.error("修改分类失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
    }
}
