package ssm.controller.home;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssm.entity.Article;
import ssm.entity.Tag;
import ssm.enums.ArticleStatus;
import ssm.service.ArticleService;
import ssm.service.TagService;

import java.util.HashMap;
import java.util.List;

/**
 * @author chen
 * @date 2019/9/2
 */
@Controller
public class ApiTagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    /**
     * 根据标签查询文章
     *
     * @param tagId 标签ID
     * @return 模板
     */
    @RequestMapping("/tag/{tagId}/page/{page}")
    public String getArticleListByTag(@PathVariable("tagId") Integer tagId, @PathVariable("page") Integer page, Model model) {
        //默认显示8条
        Integer pageSize = 8;
        //该标签信息
        Tag tag = tagService.getTagById(tagId);
        if (tag == null) {
            return "redirect:/404";
        }
        model.addAttribute("tag", tag);

        //文章列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("tagId", tagId);
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
        model.addAttribute("pageUrlPrefix", "/tag/"+tagId+"/page");
        return "Home/Page/articleListByTag";
    }

    @RequestMapping("/tag/{tagId}")
    public String listArticleByTags(@PathVariable("tagId") Integer tagId, Model model) {
        return this.getArticleListByTag(tagId, 1, model);
    }

}
