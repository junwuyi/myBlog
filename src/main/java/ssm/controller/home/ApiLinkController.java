package ssm.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ssm.entity.Article;
import ssm.entity.Link;
import ssm.enums.LinkStatus;
import ssm.service.ArticleService;
import ssm.service.LinkService;

import java.util.Date;
import java.util.List;

/**
 * @date 2019/9/5
 */
@Controller
public class ApiLinkController {
    @Autowired
    private LinkService linkService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/applyLink")
    public String applyLinkView(Model model) {
        //侧边栏显示
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/applyLink";
    }


    @RequestMapping(value = "/applyLinkSubmit", method = {RequestMethod.POST})
    @ResponseBody
    public void applyLinkSubmit(Link link) {
        link.setLinkStatus(LinkStatus.HIDDEN.getValue());
        link.setLinkCreateTime(new Date());
        link.setLinkUpdateTime(new Date());
        linkService.insertLink(link);
    }
}
