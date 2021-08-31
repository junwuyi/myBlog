package ssm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.dto.SensConst;
import ssm.enums.ResultCodeEnum;
import ssm.service.SysConfigService;

import java.util.Map;

/**
 * @author chen
 * @create 2019-08-12 16:00
 */
@Controller
@Slf4j
@RequestMapping(value = "/admin/config")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class ConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 所有设置
     * @param model
     * @return
     */
    @RequestMapping(value = "")
    public String findAll(Model model){
        Map<String, String> configs = sysConfigService.getConfigs();
        model.addAttribute("configs", configs);
        return "admin/config/listConfig";
    }

    /**
     * 保存设置选项
     *
     * @param config config
     * @return JsonResult
     */
    @RequestMapping(value = "save")
    @ResponseBody
    @BussinessLog("保存设置选项")
    public JsonResult saveOptions(@RequestParam Map<String, String> config) {

        System.out.println("config"+config);
        try {
            sysConfigService.saveConfigs(config);
            //刷新options
            //configuration.setSharedVariable("options", optionsService.findAllOptions());
            SensConst.OPTIONS.clear();
            SensConst.OPTIONS = sysConfigService.getConfigs();
            log.info("所保存的设置选项列表：" + config);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "保存成功");
        } catch (Exception e) {
            log.error("保存设置选项失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "保存失败");
        }

    }
}
