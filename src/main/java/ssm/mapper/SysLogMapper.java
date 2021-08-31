package ssm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ssm.entity.SysLog;

import java.util.List;

/**
 * @author chen
 * @create 2019-08-08 10:59
 */
@Mapper
public interface SysLogMapper {

    /**
     * 保存日志
     * @param sysLog
     */
    void save(SysLog sysLog);

    /**
     * 查询最新的8条日志
     * @param limit
     * @return
     */
    List<SysLog> findLogLimit(Integer limit);

    /**
     * 查询所有
     * @return
     */
    List<SysLog> findAll();

    /**
     * 批量删除日志
     *
     * @param ids 日志Id列表
     * @return 影响行数
     */
    Integer deleteBatch(@Param("ids") List<String> ids);

    /**
     * 根据id删除
     * @param id
     */
    void delete(Integer id);
}
