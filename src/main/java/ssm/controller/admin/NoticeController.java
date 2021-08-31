package ssm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.entity.Notice;
import ssm.enums.NoticeStatus;
import ssm.enums.ResultCodeEnum;
import ssm.service.NoticeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/admin/notice")
@Slf4j
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 后台公告列表显示
     *
     * @return
     */
    @RequestMapping(value = "")
    public String index(Model model) {
        List<Notice> noticeList = noticeService.listNotice(null);
        model.addAttribute("noticeList", noticeList);
        return "admin/notice/noticeList";

    }

    /**
     * 添加公告显示
     *
     * @return
     */
    @RequestMapping(value = "/insert")
    public String insertNoticeView() {
        return "admin/notice/insert";
    }

    /**
     * 添加公告提交
     *
     * @param notice
     * @return
     */
    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog("添加公告")
    public JsonResult insertNoticeSubmit(Notice notice) {
        try {
            notice.setNoticeCreateTime(new Date());
            notice.setNoticeUpdateTime(new Date());
            notice.setNoticeStatus(NoticeStatus.RELEASE.getValue());
            notice.setNoticeOrder(1);
            noticeService.insertNotice(notice);
        } catch (Exception e) {
            log.error("添加通知失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "添加通知失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"通知发布成功");
    }

    /**
     * 删除公告
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    @BussinessLog("删除公告")
    public JsonResult deleteNotice(@PathVariable("id") Integer id) {
        try {
            Notice notice = noticeService.getNoticeById(id);
            if (notice == null) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "该通知不存在,或已被删除");
            }
            noticeService.deleteNotice(id);
        } catch (Exception e) {
            log.error("删除通知失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
    }

    /**
     * 编辑公告页面显示
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public String editNoticeView(@PathVariable("id") Integer id, Model model) {
        Notice notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "admin/notice/edit";
    }


    /**
     * 编辑公告提交
     * @param notice
     * @return
     */
    @BussinessLog("编辑公告")
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult editNoticeSubmit(Notice notice) {
        try {
            notice.setNoticeUpdateTime(new Date());
            noticeService.updateNotice(notice);
        } catch (Exception e) {
            log.error("修改通知失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改通知失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"修改成功");
    }

    //@RequiresPermissions("notice:release")
    @RequestMapping(value = "/release/{id}", method = RequestMethod.POST)
    //@BussinessLog("发布公告通知")
    @ResponseBody
    public JsonResult release(@PathVariable Integer id) {
        try {
            Notice notice = new Notice();
            notice.setNoticeId(id);
            notice.setNoticeStatus(NoticeStatus.RELEASE.getValue());
            noticeService.updateNotice(notice);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "通知发布失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"通知发布成功");
    }

    //@RequiresPermissions("notice:withdraw")
    @RequestMapping(value = "/withdraw/{id}", method = RequestMethod.POST)
    @BussinessLog("撤回公告通知")
    @ResponseBody
    public JsonResult withdraw(@PathVariable Integer id) {
        try {
            Notice notice = new Notice();
            notice.setNoticeId(id);
            notice.setNoticeStatus(NoticeStatus.NOT_RELEASE.getValue());
            noticeService.updateNotice(notice);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"通知撤回失败！");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"该通知已撤回！");
    }

    /**
     * 批量删除通知
     * @param delitems
     * @return
     */
    @BussinessLog("批量删除通知")
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteBatch(@RequestParam("delitems") String delitems) {
        List<String> delList = new ArrayList<>();
        String[] strs = delitems.split(",");
        for (String str : strs) {
            delList.add(str);
        }
        try {
            if (delList != null && delList.size() > 0) {
                noticeService.deleteBatch(delList);
            }
        } catch (Exception e) {
            log.error("批量删除通知失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
    }


}
