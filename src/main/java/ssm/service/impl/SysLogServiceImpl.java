package ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.entity.SysLog;
import ssm.enums.PlatformEnum;
import ssm.mapper.SysLogMapper;
import ssm.service.SysLogService;

import java.util.List;

/**
 * @author chen
 * @create 2019-08-08 16:11
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void save(SysLog sysLog) {
        sysLogMapper.save(sysLog);
    }

    @Override
    public void asyncSaveSystemLog(PlatformEnum platform, String bussinessName) {

    }

    /**
     * 查询最新的8条日志
     * @param limit
     * @return
     */
    @Override
    public List<SysLog> findLogLimit(Integer limit) {
        return sysLogMapper.findLogLimit(limit);
    }

    /**
     * 查询所有日志
     * @return
     */
    @Override
    public List<SysLog> findAll() {
        return sysLogMapper.findAll();
    }


    @Override
    public PageInfo<SysLog> listLogByPage(Integer pageIndex, Integer pageSize) {
        //分页拦截
        PageHelper.startPage(pageIndex, pageSize);
        List<SysLog> sysLogs = sysLogMapper.findAll();

        return new PageInfo<>(sysLogs);
    }

    /**
     * 批量删除
     * @param delList
     */
    @Override
    public void deleteBatch(List<String> delList) {
        sysLogMapper.deleteBatch(delList);
    }

    @Override
    public void delete(Integer id) {
        sysLogMapper.delete(id);
    }
}
