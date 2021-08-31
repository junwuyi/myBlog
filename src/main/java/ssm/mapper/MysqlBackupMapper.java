package ssm.mapper;

import ssm.entity.MysqlBackup;

import java.util.List;

/**
 * @author chen
 * @create 2019-08-31 21:18
 */
public interface MysqlBackupMapper {

    /**
     * 查询所有文件
     * @return
     */
    public List<MysqlBackup> findAll();
}
