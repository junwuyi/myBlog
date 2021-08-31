package ssm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.entity.Link;
import ssm.enums.ResultCodeEnum;
import ssm.service.LinkService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author chen
 */
@Controller
@RequestMapping("/admin/link")
@Slf4j
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 后台链接列表显示
     *
     * @return modelAndView
     */
    @RequestMapping(value = "")
    public ModelAndView linkList()  {
        ModelAndView modelandview = new ModelAndView();

        List<Link> linkList = linkService.listLink(null);
        modelandview.addObject("linkList",linkList);

        modelandview.setViewName("admin/link/linkList");
        return modelandview;

    }

    /**
     * 后台添加链接页面显示
     *
     * @return modelAndView
     */
    @RequestMapping(value = "/insert")
    public ModelAndView insertLinkView()  {
        ModelAndView modelAndView = new ModelAndView();

       /* List<Link> linkList = linkService.listLink(null);
        modelAndView.addObject("linkList",linkList);*/

        modelAndView.setViewName("admin/link/insert");
        return modelAndView;
    }

    /**
     * 后台添加链接页面提交
     *
     * @param link 链接
     * @return 响应
     */
    @BussinessLog("添加链接")
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult insertLinkSubmit(Link link) {
        try {
            link.setLinkCreateTime(new Date());
            link.setLinkUpdateTime(new Date());
            //link.setLinkStatus(1);
            linkService.insertLink(link);
        } catch (Exception e) {
            log.info("添加链接失败：{}" + e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "添加失败");
        }

        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "添加成功");
    }

    /**
     * 删除链接
     *
     * @param id 链接ID
     * @return 响应
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    @BussinessLog("删除链接")
    public JsonResult deleteLink(@PathVariable("id") Integer id)  {
        try {
            Link linkCustom =  linkService.getLinkById(id);
            if (linkCustom == null) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "友链不存在或已被删除!");
            }
            // 删除
            linkService.deleteLink(id);
        } catch (Exception e) {
            log.info("删除链接失败：{}" + e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败!");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
    }

    /**
     * 编辑链接页面显示
     *
     * @param id
     * @return modelAndVIew
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editLinkView(@PathVariable("id") Integer id)  {
        ModelAndView modelAndView = new ModelAndView();

        Link linkCustom =  linkService.getLinkById(id);
        modelAndView.addObject("linkCustom",linkCustom);

        List<Link> linkList = linkService.listLink(null);
        modelAndView.addObject("linkList",linkList);

        modelAndView.setViewName("admin/link/edit");
        return modelAndView;
    }


    /**
     * 编辑链接提交
     *
     * @param link 链接
     * @return 响应
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog("编辑链接")
    public JsonResult editLinkSubmit(Link link)  {
        try {
            link.setLinkUpdateTime(new Date());
            linkService.updateLink(link);
        } catch (Exception e) {
            log.info("链接修改失败：{}" + e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
    }

    /**
     * 批量更新link状态
     *
     * @param items
     * @param linkStatus
     * @return
     */
    @RequestMapping(value = "/updateBatch", method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog("批量更新链接状态")
    public JsonResult updateBatch(@RequestParam("items") String items, Integer linkStatus) {
        List<String> itemsList = new ArrayList<>();
        //将前端传来的id字符串分割为字符串数组
        String[] strs = items.split(",");
        for (String str : strs) {
            itemsList.add(str);
        }
        try {
            if (itemsList != null && itemsList.size() > 0) {
                linkService.updateBatch(itemsList, linkStatus);
            }
        } catch (Exception e) {
            log.info("批量更新link状态失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
    }

    /**
     * 批量删除link
     * @param delitems
     * @return
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog("批量删除链接")
    public JsonResult deleteBatch(@RequestParam("delitems") String delitems) {
        List<String> delList = new ArrayList<>();
        String[] strs = delitems.split(",");
        for (String str : strs) {
            delList.add(str);
        }
        try {
            if (delList != null && delList.size() > 0) {
                linkService.deleteBatch(delList);
            }
        } catch (Exception e) {
            log.error("批量删除链接失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
    }

}
