package ssm.controller.home;

import cn.hutool.http.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.entity.Article;
import ssm.entity.Comment;
import ssm.enums.ArticleStatus;
import ssm.enums.Role;
import ssm.service.ArticleService;
import ssm.service.CommentService;
import ssm.util.MyUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RestController
public class ApiCommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    /**
     * '添加评论
     *
     * @param request
     * @param comment
     */
    @BussinessLog("添加评论")
    @RequestMapping(value = "/comment", method = {RequestMethod.POST})
    public JsonResult insertComment(HttpServletRequest request, Comment comment) {
        //添加评论
        comment.setCommentCreateTime(new Date());
        comment.setCommentIp(MyUtils.getIpAddr(request));
        if (request.getSession().getAttribute("user") != null) {
            comment.setCommentRole(Role.ADMIN.getValue());
        } else {
            comment.setCommentRole(Role.VISITOR.getValue());
        }
        //设置头像信息
        comment.setCommentAuthorAvatar(MyUtils.getGravatar(comment.getCommentAuthorEmail()));

        //过滤字符，防止XSS攻击
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        comment.setCommentAuthorName(HtmlUtil.escape(comment.getCommentAuthorName()));
        comment.setCommentAuthorEmail(HtmlUtil.escape(comment.getCommentAuthorEmail()));
        comment.setCommentAuthorUrl(HtmlUtil.escape(comment.getCommentAuthorUrl()));
        try {
            commentService.insertComment(comment);
            //更新文章的评论数
            Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
            articleService.updateCommentCount(article.getArticleId());
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().fail();
        }
        return new JsonResult().ok();
    }


}
