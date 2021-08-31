package ssm.controller.admin;


import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.entity.Article;
import ssm.entity.Comment;
import ssm.entity.User;
import ssm.enums.ArticleStatus;
import ssm.enums.ResultCodeEnum;
import ssm.service.ArticleService;
import ssm.service.CommentService;
import ssm.service.UserService;
import ssm.util.MyUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.List;


/**
 * @author chen
 */
@Controller
@RequestMapping("/admin/comment")
@Slf4j
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    /**
     * 评论页面
     *
     * @param page 页码
     * //@param pageSize  页大小
     * @return modelAndView
     */
    @RequestMapping(value = "page/{page}")
    public String commentListView(@PathVariable(value = "page") Integer page,
                                  //@RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                  Model model) {
        //默认显示10条
        Integer pageSize = 10;
        PageInfo<Comment> commentPageInfo = commentService.listCommentByPage(page, pageSize);
        model.addAttribute("pageInfo", commentPageInfo);
        model.addAttribute("pageUrlPrefix","/admin/comment/page");
        return "admin/comment/commentList";
    }

    /**
     * 后台评论列表分页显示
     *
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "")
    public String index(String status, Model model) {
        return this.commentListView(1,model);
    }

    /**
     * 添加评论
     *
     * @param request
     * @param comment
     */
    @BussinessLog("添加评论")
    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    @ResponseBody
    public void insertComment(HttpServletRequest request, Comment comment) {
        //添加评论
        comment.setCommentIp(MyUtils.getIpAddr(request));
        comment.setCommentCreateTime(new Date());
        commentService.insertComment(comment);
        //更新文章的评论数
        //1.获取到是哪一篇文章
        Article article = articleService.getArticleByStatusAndId(null, comment.getCommentArticleId());
        articleService.updateCommentCount(article.getArticleId());
    }

    /**
     * 删除评论
     *
     * @param id 批量ID
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    @BussinessLog("批量删除评论")
    public JsonResult deleteComment(@PathVariable("id") Integer id) {
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "评论不存在或已被删除!");
        }
        //删除评论
        commentService.deleteComment(id);
        //删除其子评论
        List<Comment> childCommentList = commentService.listChildComment(id);
        if (childCommentList != null){
            for (int i = 0; i < childCommentList.size(); i++) {
                commentService.deleteComment(childCommentList.get(i).getCommentId());
            }
        }
        //更新文章的评论数
        Article article = articleService.getArticleByStatusAndId(null, comment.getCommentArticleId());
        articleService.updateCommentCount(article.getArticleId());

        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "评论删除成功");
}

    /**
     * 编辑评论页面显示
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public String editCommentView(@PathVariable("id") Integer id, Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "Admin/Comment/edit";
    }


    /**
     * 编辑评论提交
     *
     * @param comment
     * @return
     */
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editCommentSubmit(Comment comment) {
        commentService.updateComment(comment);
        return "redirect:/admin/comment";
    }


    /**
     * 回复评论页面显示
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/reply/{id}")
    public String replyCommentView(@PathVariable("id") Integer id, Model model, Principal principal) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);

        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "admin/comment/reply";
    }

    /**
     * 回复评论提交
     *
     * @param request
     * @param comment
     * @return
     */
    @BussinessLog("回复评论")
    @RequestMapping(value = "/replySubmit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult replyCommentSubmit(HttpServletRequest request, Comment comment) {
        try {
            //文章评论数+1
            Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
            article.setArticleCommentCount(article.getArticleCommentCount() + 1);
            articleService.updateArticle(article);
            //添加评论
            //为了简单没有设置子评论中的子评论（评论最多为两级关系）
            Comment lastComment = commentService.getCommentById(comment.getCommentPid());
            Integer commentPid = lastComment.getCommentPid();
            //System.out.println(commentPid);
            if (commentPid != 0) { //被回复的pid不能为0
                comment.setCommentPid(commentPid);
            }

            comment.setCommentCreateTime(new Date());
            comment.setCommentIp(MyUtils.getIpAddr(request));
            commentService.insertComment(comment);
        } catch (Exception e) {
            log.error("回复评论失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "回复评论失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "回复评论成功");
    }

}
