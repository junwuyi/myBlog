package ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.entity.MysqlBackup;
import ssm.mapper.MysqlBackupMapper;
import ssm.service.MysqlBackupService;

import java.util.List;

/**
 * @author chen
 * @create 2019-08-31 21:14
 */
@Service
public class MysqlBackupServiceImpl implements MysqlBackupService {

    @Autowired
    private MysqlBackupMapper mysqlBackupMapper;
    /**
     * 查询所有备份文件
     * @return
     */
    @Override
    public List<MysqlBackup> findAll() {
        return mysqlBackupMapper.findAll();
    }
}
