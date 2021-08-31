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
import ssm.entity.Comment;
import ssm.entity.SysLog;
import ssm.enums.ResultCodeEnum;
import ssm.service.SysLogService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @create 2019-09-20 18:07
 * 系统日志
 */
@Controller
@Slf4j
@RequestMapping(value = "/admin/logs")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class LogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 后台文章列表分页显示
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "")
    public String index(Model model) {
        return this.indexPage(1,  model);
    }

    /**
     * 后台日志列表分页显示
     *
     * @param page   第几页开始
     * @param model
     * @return
     */
    @RequestMapping(value = "page/{page}")
    public String indexPage(@PathVariable(value = "page") Integer page, Model model) {
        //默认显示10条
        Integer pageSize = 10;
        PageInfo<SysLog> logPageInfo = sysLogService.listLogByPage(page, pageSize);
        model.addAttribute("pageInfo", logPageInfo);
        model.addAttribute("pageUrlPrefix","/admin/logs/page");
        return "admin/log/log-list";
    }

    /**
     * 批量删除文章
     *
     * @param delitems
     * @return
     */
    @BussinessLog("批量删除日志")
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
                sysLogService.deleteBatch(delList);
            }
        } catch (Exception e) {
            log.error("批量删除日志失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
    }


    /**
     * 根据id删除日志
     *
     * @param id
     * @return
     */
    @BussinessLog("根据id删除日志")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(@RequestParam("id") Integer id) {

        try {
            sysLogService.delete(id);

        } catch (Exception e) {
            log.error("删除日志失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
    }


}
