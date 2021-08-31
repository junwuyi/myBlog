package ssm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chen
 * @create 2019-08-31 20:30
 * 博客数据备份
 */
@Controller
@Slf4j
@RequestMapping(value = "/admin/backup")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class MysqlBackupController {

    @RequestMapping(value = "list")
    public String listBackupFile(){
        return null;
    }
}
