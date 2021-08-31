package ssm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chen
 * @create 2019-08-31 20:58
 */
@Data
public class MysqlBackup implements Serializable {
    //编号
    private Integer id;
    //操作者编号
    private Integer userId;
    //备份后的文件名
    private String fileName;
    //备份时间
    private Date backupTime;
    //路径
    private String path;

    //非数据库字段 操作者
    private User user;

}
