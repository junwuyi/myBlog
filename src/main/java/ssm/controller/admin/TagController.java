package ssm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.entity.Tag;
import ssm.enums.ResultCodeEnum;
import ssm.service.ArticleService;
import ssm.service.TagService;

import java.util.List;


/**
 * @author chen
 */
@Controller
@Slf4j
@RequestMapping("/admin/tags")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class TagController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    /**
     * 后台标签列表显示
     *
     * @return
     */
    @RequestMapping(value = "")
    public ModelAndView index() {
        ModelAndView modelandview = new ModelAndView();
        //获取所有的标签和该标签对应的文章数量
        List<Tag> tagList = tagService.listTagWithCount();
        modelandview.addObject("tagList", tagList);

        modelandview.setViewName("admin/tag/index");
        return modelandview;

    }


    /**
     * 后台添加分类页面显示
     *
     * @param tag
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult insertTagSubmit(Tag tag) {
        try {
            //检查该标签是否存在
            if (tagService.getTagByName(tag.getTagName()) != null) {
                //存在
                log.error("该分类存在：{}");
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "标签存在");
            }

            tagService.insertTag(tag);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "添加失败");
        }
    }

    /**
     * 跳转到编辑标签页面
     * @param id
     * @return
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id) {
        ModelAndView modelandview = new ModelAndView();
        //获取所有的标签和该标签对应的文章数量
        List<Tag> tagList = tagService.listTagWithCount();
        modelandview.addObject("tagList", tagList);

        Tag tag = tagService.getTagById(id);
        if (tag == null) {
            //throw new RuntimeException("根据id获取标签失败");
            log.error("根据id获取标签失败：{}");
        }
        modelandview.addObject("tag", tag);
        modelandview.setViewName("admin/tag/edit");
        return modelandview;
    }

    /**
     * 修改标签信息
     * @param tag
     * @return
     */
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog("修改标签")
    public JsonResult editSubmit(Tag tag) {
        try {
            tagService.updateTag(tag);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("修改失败: {}" + e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改失败");
        }
    }

    /**
     * 删除标签
     * @param tagId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteTag", method = RequestMethod.POST)
    @BussinessLog("删除标签")
    public JsonResult deleteTag(@RequestParam Integer tagId) {
        try {
            Integer count = articleService.countArticleByTagId(tagId);
            if (count != 0) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "该标签含有文章不可删除");
            }
            tagService.deleteTag(tagId);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败");
        }

    }
}