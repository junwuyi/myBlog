package ssm.mapper;

import org.apache.ibatis.annotations.Mapper;
import ssm.entity.SysConfig;

import java.util.List;

/**
 * @author chen
 * @create 2019-08-12 15:44
 */
@Mapper
public interface SysConfigMapper {

    /**
     * 查询所有
     * @return
     */
    List<SysConfig> findAll();

    /**
     * 根据key查询单个SysConfig
     *
     * @param key key
     * @return SysConfig
     */
    SysConfig findSysConfigByConfigKey(String key);

    void insert(SysConfig sysConfig);

    /**
     * 更新设置
     * @param sysConfig
     */
    void updateById(SysConfig sysConfig);

    void deleteById(SysConfig sysConfig);
}
