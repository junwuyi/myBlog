package ssm.controller.admin;

import cn.hutool.http.HtmlUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.ArticleParam;
import ssm.dto.JsonResult;
import ssm.entity.Article;
import ssm.entity.Category;
import ssm.entity.Tag;
import ssm.entity.User;
import ssm.enums.ArticleStatus;
import ssm.enums.ResultCodeEnum;
import ssm.service.ArticleService;
import ssm.service.CategoryService;
import ssm.service.TagService;
import ssm.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * @author chen
 * 后台文章管理
 */
@Controller
@Slf4j
@RequestMapping(value = "/admin/article")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * 后台文章列表分页显示
     *
     * @param page   第几页开始
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "page/{page}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ROOT')") // 指定角色权限才能操作方法
    public String indexPage(@PathVariable(value = "page") Integer page,
                            @RequestParam(required = false,defaultValue = "1") String status, Model model) {

        //默认显示8条
        Integer pageSize = 8;
        HashMap<String, Object> criteria = new HashMap<>(1);
        if (status == null) {
            model.addAttribute("pageUrlPrefix", "/admin/article/page");
        } else {
            criteria.put("status", status);//   /admin/article/1?status=1
            model.addAttribute("pageUrlPrefix", "/admin/article/page");
            model.addAttribute("pageUrlSuffix", "?status=" + status);
        }
        PageInfo<Article> articlePageInfo = articleService.pageArticle(page, pageSize, criteria);
        model.addAttribute("pageInfo", articlePageInfo);
        model.addAttribute("active", status);

        Integer count1 = articleService.countArticle("1");
        model.addAttribute("count1", count1);

        Integer count2 = articleService.countArticle("2");
        model.addAttribute("count2", count2);

        Integer count3 = articleService.countArticle("3");
        model.addAttribute("count3", count3);

        Integer count0 = articleService.countArticle("0");
        model.addAttribute("count0", count0);

        return "admin/article/articleList";
    }

    /**
     * 后台文章列表分页显示
     *
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ROOT')") // 指定角色权限才能操作方法
    public String index(String status, Model model) {
        return this.indexPage(1, status, model);
    }

    /**
     * 后台添加文章页面显示
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/insert")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ROOT')")  // 指定角色权限才能操作方法
    public String insertArticleView(Model model) {
        //所有的分类
        List<Category> categoryList = categoryService.listCategory();
        //所有的标签
        List<Tag> tagList = tagService.listTag();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("tagList", tagList);
        return "admin/article/insert";
    }

    /**
     * 后台添加文章提交操作
     *
     * @param articleParam
     * @return
     */
    @BussinessLog("添加文章")
    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ROOT')") // 指定角色权限才能操作方法
    public JsonResult insertArticleSubmit(HttpSession session, ArticleParam articleParam, @RequestParam("tagList") String tagList) {

        try {
            Article article = new Article();
            //用户ID
            User user = (User) session.getAttribute("user");
            if (user != null) {
                article.setArticleUserId(user.getUserId());
            }
            //文章摘要(截取内容长度的150)
            int summaryLength = 150;
            String summaryText = HtmlUtil.cleanHtmlTag(articleParam.getArticleContent());
            if (summaryText.length() > summaryLength) {
                String summary = summaryText.substring(0, summaryLength);
                article.setArticleSummary(summary);
            } else {
                article.setArticleSummary(summaryText);
            }
            //设置排序号
            article.setArticleOrder(articleParam.getArticleOrder());
            //设置标题
            article.setArticleTitle(articleParam.getArticleTitle());
            //文章内容
            article.setArticleContent(articleParam.getArticleContent());
            //文章状态
            article.setArticleStatus(articleParam.getArticleStatus());
            //文章缩略图
            article.setArticleThumbnail(articleParam.getArticleThumbnail());
            //填充分类
            List<Category> categoryList = new ArrayList<>();
            if (articleParam.getArticleParentCategoryId() != null) {
                categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
            }
            if (articleParam.getArticleChildCategoryId() != null) {
                categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
            }
            article.setCategoryList(categoryList);
            //填充标签
            if (StringUtils.isNotEmpty(tagList)) {
                List<Tag> tags = tagService.strListToTagList(StringUtils.deleteWhitespace(tagList));
                article.setTagList(tags);
            }

            //添加文章
            articleService.insertArticle(article);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "发布成功");
        } catch (Exception e) {
            log.error("保存文章失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "发布失败");
        }
    }

    /**
     * 跳转到编辑文章页面
     * @return
     */
    @RequestMapping("/edit/{articleId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ROOT')") // 指定角色权限才能操作方法
    public String editArticle(@PathVariable("articleId") Integer articleId, Model model) {

        Article article = articleService.getByArticleId(articleId);
        model.addAttribute("article", article);

        //得到当前文章的用户名
        String userName = userService.getUserById(article.getArticleUserId()).getUserName();

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println(userName+":"+ user.getUsername());
        //得到当前用户的权限集合
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
      /*  System.out.println("权限集合:"+authorities);
        for (GrantedAuthority authority : authorities) {
            System.out.println("权限集合："+authority.getAuthority()+" "+authorities.toString().contains("ROLE_ROOT"));
        }
*/
        //超级管理员和该篇文章的作者有权修改
        if (Objects.equals(user.getUsername(), userName)||authorities.toString().contains("ROLE_ROOT")) {
            //所有的分类
            List<Category> categoryList = categoryService.listCategory();
            model.addAttribute("categoryList", categoryList);

            //所有的标签
            List<Tag> tagList = tagService.listTag();
            model.addAttribute("tagList", tagList);

            return "admin/article/edit";
        }
        return "redirect:/403";
    }

    /**
     * 编辑文章提交
     *
     * @param articleParam
     * @return
     */

    @BussinessLog("修改文章")
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ROOT')") // 指定角色权限才能操作方法
    public JsonResult editArticleSubmit(ArticleParam articleParam, @RequestParam("tagList") String tagList) {
        try {
            Article article = new Article();
            article.setArticleId(articleParam.getArticleId());
            //设置排序号
            article.setArticleOrder(articleParam.getArticleOrder());
            //设置标题
            article.setArticleTitle(articleParam.getArticleTitle());
            //文章内容
            article.setArticleContent(articleParam.getArticleContent());
            //文章状态
            article.setArticleStatus(articleParam.getArticleStatus());
            //文章缩略图
            article.setArticleThumbnail(articleParam.getArticleThumbnail());

            //文章摘要
            int summaryLength = 150;
            String summaryText = HtmlUtil.cleanHtmlTag(article.getArticleContent());
            if (summaryText.length() > summaryLength) {
                String summary = summaryText.substring(0, summaryLength);
                article.setArticleSummary(summary);
            } else {
                article.setArticleSummary(summaryText);
            }
            //填充分类
            List<Category> categoryList = new ArrayList<>();
            if (articleParam.getArticleParentCategoryId() != null) {
                categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
            }
            if (articleParam.getArticleChildCategoryId() != null) {
                categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
            }
            article.setCategoryList(categoryList);
            //填充标签
            if (StringUtils.isNotEmpty(tagList)) {
                List<Tag> tags = tagService.strListToTagList(StringUtils.deleteWhitespace(tagList));
                article.setTagList(tags);
            }
            articleService.updateArticleDetail(article);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
        } catch (Exception e) {
            log.error("保存文章失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改失败");
        }

    }

    /**
     * 文章搜索
     *
     * @param keyword
     * @param pageIndex
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping(value = "/search")
    public String search(
            @RequestParam("keyword") String keyword,
            @RequestParam("field") String field,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
        //文章列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        criteria.put("keyword", keyword);
        PageInfo<Article> articlePageInfo = articleService.pageArticle(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", articlePageInfo);


        model.addAttribute("pageUrlPrefix", "/search?pageIndex");
        return "Home/Page/search";
    }

    /**
     * 批量删除文章
     *
     * @param delitems
     * @return
     */
    @BussinessLog("批量删除文章")
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteBatch(@RequestParam("delitems") String delitems) {
        //定义一个集合用来存储前端的id值
        List<String> delList = new ArrayList<>();
        //将前端传来的id字符串分割为字符串数组
        String[] strs = delitems.split(",");

        //遍历数组并把每一个元素添加到list集合中
        for (String str : strs) {
            delList.add(str);
        }
        try {
            if (delList != null && delList.size() > 0) {
                articleService.deleteBatch(delList);
            }
        } catch (Exception e) {
            log.error("批量删除文章失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
    }

    /**
     * 批量更新文章状态
     *
     * @param items
     * @param articleStatus
     * @return
     */
    @RequestMapping(value = "/updateBatch", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateBatch(@RequestParam("items") String items, Integer articleStatus) {
        List<String> itemsList = new ArrayList<>();
        String[] strs = items.split(",");
        for (String str : strs) {
            itemsList.add(str);
        }
        try {
            if (itemsList != null && itemsList.size() > 0) {
                articleService.updateBatch(itemsList, articleStatus);
            }
        } catch (Exception e) {
            log.info("批量更新文章状态失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
    }


    /**
     * 删除文章
     *
     * @param id 文章ID
     *//*
    @RequestMapping(value = "/delete/{id}")
    public void deleteArticle(@PathVariable("id") Integer id) {
        articleService.deleteArticle(id);
    }
    */
}



