package ssm.service;

import ssm.entity.MysqlBackup;

import java.util.List;

/**
 * @author chen
 * @create 2019-08-31 21:13
 */
public interface MysqlBackupService {

    /**
     * 查询所有备份文件
     * @return
     */
    List<MysqlBackup> findAll();
}
