package ssm.controller.home;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssm.entity.*;
import ssm.enums.ArticleStatus;
import ssm.enums.LinkStatus;
import ssm.enums.NoticeStatus;
import ssm.service.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 前台主页的controller
 */
@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;


    public void showMenu(Model model){
        //获得网站概况
        List<String> siteBasicStatistics = new ArrayList<String>();
        siteBasicStatistics.add(articleService.countArticle(ArticleStatus.PUBLISH.getValue().toString()) + "");
        siteBasicStatistics.add(articleService.countArticleComment() + "");
        siteBasicStatistics.add(categoryService.countCategory() + "");
        siteBasicStatistics.add(tagService.countTag() + "");
        siteBasicStatistics.add(linkService.countLink(LinkStatus.NORMAL.getValue()) + "");
        siteBasicStatistics.add(articleService.countArticleView() + "");
        model.addAttribute("siteBasicStatistics", siteBasicStatistics);
        //最后更新的文章
        Article lastUpdateArticle = articleService.getLastUpdateArticle();
        model.addAttribute("lastUpdateArticle", lastUpdateArticle);
    }

    @RequestMapping(value = {"page/{page}"})
    public String index(@PathVariable(value = "page") Integer page, Model model) {
        //默认显示8条
        Integer pageSize = 8;
        HashMap<String, Object> criteria = new HashMap<>(1);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        //文章列表
        PageInfo<Article> articleList = articleService.pageArticle(page, pageSize, criteria);
        model.addAttribute("pageInfo", articleList);

        //公告
        List<Notice> noticeList = noticeService.listNotice(NoticeStatus.RELEASE.getValue());
        model.addAttribute("noticeList", noticeList);
        //友情链接
        List<Link> linkList = linkService.listLink(LinkStatus.NORMAL.getValue());
        model.addAttribute("linkList", linkList);

        //侧边栏显示
        //标签列表显示
        List<Tag> allTagList = tagService.listTag();
        model.addAttribute("allTagList", allTagList);
        //最新评论
        List<Comment> recentCommentList = commentService.listRecentComment(10);
        model.addAttribute("recentCommentList", recentCommentList);
        model.addAttribute("pageUrlPrefix", "/page");
        showMenu(model);
        return "Home/index";
    }



    @RequestMapping(value = "")
    public String indexPage(Integer page, Model model) {
        return this.index(1, model);
    }

    @RequestMapping(value = "/search")
    public String search(
            @RequestParam("keywords") String keywords,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "8") Integer pageSize, Model model) {
        //文章列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        criteria.put("keywords", keywords);
        PageInfo<Article> articlePageInfo = articleService.pageArticle(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", articlePageInfo);

        //侧边栏显示
        //标签列表显示
        List<Tag> allTagList = tagService.listTag();
        model.addAttribute("allTagList", allTagList);
        //获得随机文章
        List<Article> randomArticleList = articleService.listRandomArticle(8);
        model.addAttribute("randomArticleList", randomArticleList);
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        //最新评论
        List<Comment> recentCommentList = commentService.listRecentComment(10);
        model.addAttribute("recentCommentList", recentCommentList);
        model.addAttribute("pageUrlPrefix", "/search?pageIndex");
        return "Home/Page/search";
    }

    @RequestMapping("highlight")
    public String highlight(){

        return "Home/Page/highlight";
    }

}




