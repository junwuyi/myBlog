package ssm.controller.home;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssm.entity.Article;
import ssm.entity.Category;
import ssm.entity.Tag;
import ssm.enums.ArticleStatus;
import ssm.service.ArticleService;
import ssm.service.CategoryService;
import ssm.service.TagService;

import java.util.HashMap;
import java.util.List;


/**
 * 文章分类目录的controller
 *
 */
@Controller
public class ApiCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    /**
     * 根据分类查询文章
     *
     * @param cateId 分类ID
     * @return 模板
     */
    @RequestMapping("/category/{cateId}/page/{page}")
    public String getArticleListByCategory(@PathVariable("cateId") Integer cateId,
                                           @PathVariable(value = "page") Integer page,
                                           Model model) {
        //默认显示8条
        Integer pageSize = 8;
        //该分类信息
        Category category = categoryService.getCategoryById(cateId);
        if (category == null) {
            return "redirect:/404";
        }
        model.addAttribute("category", category);

        //文章列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("categoryId", cateId);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        PageInfo<Article> articlePageInfo = articleService.pageArticle(page, pageSize, criteria);
        model.addAttribute("pageInfo", articlePageInfo);

        //侧边栏
        //标签列表显示
        List<Tag> allTagList = tagService.listTag();
        model.addAttribute("allTagList", allTagList);
        //获得随机文章
        List<Article> randomArticleList = articleService.listRandomArticle(8);
        model.addAttribute("randomArticleList", randomArticleList);
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        model.addAttribute("pageUrlPrefix", "/category/"+cateId+"/page");
        return "Home/Page/articleListByCategory";
    }

    @RequestMapping("/category/{cateId}")
    public String listArticleByCategory(@PathVariable("cateId") Integer cateId, Model model) {
        return this.getArticleListByCategory(cateId, 1, model);
    }

}
