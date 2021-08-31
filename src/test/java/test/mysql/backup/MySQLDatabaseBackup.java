package test.mysql.backup;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import ssm.entity.User;
import ssm.util.DatabaseBackupUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MySQL数据库备份测试
 *
 * @author
 */
public class MySQLDatabaseBackup {

    public static void main(String[] args){
        try {
            //用户目录
            final StrBuilder uploadPath = new StrBuilder(System.getProperties().getProperty("user.home"));
            uploadPath.append("/junwuyi/mysql_backup/" + DateUtil.thisYear()).append("/").append(DateUtil.thisMonth() + 1).append("/");
            final File mediaPath = new File(uploadPath.toString());
            if (!mediaPath.exists()) {
                mediaPath.mkdirs();
            }
            System.out.println("文件路径："+uploadPath);
            Date date = new Date();
            System.out.println("备份时间："+date);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("作者："+user.getUserNickname());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String prefix = simpleDateFormat.format(date).toString();
            String fileName = prefix+".sql";
            System.out.println("文件名："+fileName);

            long start = System.currentTimeMillis();
            if (DatabaseBackupUtil.exportDatabaseTool("127.0.0.1", "root", "123456", uploadPath.toString(),
                    fileName, "forest_blog")) {
                System.out.println("数据库成功备份！！！");
            } else {
                System.out.println("数据库备份失败！！！");
            }
            long end = System.currentTimeMillis();
            System.out.println("共耗时：" + (end - start) + "毫秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
