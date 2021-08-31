package ssm.service;

import com.github.pagehelper.PageInfo;
import ssm.entity.SysLog;
import ssm.enums.PlatformEnum;

import java.util.List;

/**
 * @author chen
 * @create 2019-08-08 16:10
 */
public interface SysLogService {

    void save(SysLog sysLog);

    void asyncSaveSystemLog(PlatformEnum platform, String bussinessName);

    /**
     * 查询最新的8条日志
     *
     * @param limit
     * @return
     */
    List<SysLog> findLogLimit(Integer limit);

    /*查询所有*/
    List<SysLog> findAll();

    /**
     * 获取所有日志列表
     *
     * @param pageIndex 第几页开始
     * @param pageSize  一页显示数量
     * @return 列表
     */
    PageInfo<SysLog> listLogByPage(Integer pageIndex, Integer pageSize);

    /**
     * 批量删除
     * @param delList
     */
    void deleteBatch(List<String> delList);

    /**
     *
     * @param id
     */
    void delete(Integer id);
}
