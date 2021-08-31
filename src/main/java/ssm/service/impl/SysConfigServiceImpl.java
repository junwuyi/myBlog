package ssm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ssm.entity.SysConfig;
import ssm.mapper.SysConfigMapper;
import ssm.service.SysConfigService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统配置
 * @author chen
 */
/**
 *
 * 缓存机制说明：所有的查询结果都放进了缓存，也就是把MySQL查询的结果放到了redis中去，
 * 然后第二次发起该条查询时就可以从redis中去读取查询的结果，从而不与MySQL交互，从而达到优化的效果，
 * redis的查询速度之于MySQL的查询速度相当于 内存读写速度 /硬盘读写速度
 *
 * @Cacheable(value="xxx" key="zzz")注解：标注该方法查询的结果进入缓存，再次访问时直接读取缓存中的数据
 * 1.对于有参数的方法，指定value(缓存区间)和key(缓存的key)；
 * 	   对于无参数的方法，只需指定value,存到数据库中数据的key通过com.ssm.utils.RedisCacheConfig中重写的generate()方法生成。
 * 2.调用该注解标识的方法时，会根据value和key去redis缓存中查找数据，如果查找不到，则去数据库中查找，然后将查找到的数据存放入redis缓存中；
 * 3.向redis中填充的数据分为两部分：
 * 		1).用来记录xxx缓存区间中的缓存数据的key的xxx~keys(zset类型)
 * 		2).缓存的数据，key：数据的key；value：序列化后的从数据库中得到的数据
 * 4.第一次执行@Cacheable注解标识的方法，会在redis中新增上面两条数据
 * 5.非第一次执行@Cacheable注解标识的方法，若未从redis中查找到数据，则执行从数据库中查找，并：
 * 		1).新增从数据库中查找到的数据
 * 		2).在对应的zset类型的用来记录缓存区间中键的数据中新增一个值，新增的value为上一步新增的数据的key
 */
@Slf4j
@Service
public class SysConfigServiceImpl implements SysConfigService {

    private static final String SYSCONFIG_CACHE_NAME = "configs";

    @Autowired
    private SysConfigMapper sysConfigMapper;

    /**
     * 获取系统配置
     *
     * @return
     */
    @Override
    @Cacheable(value = SYSCONFIG_CACHE_NAME, key = "'configs_all'")
    public Map<String, String> getConfigs() {
        Map<String, String> sysConfig = new HashMap<>();
        List<SysConfig> sysConfigList = sysConfigMapper.findAll();
        if (null != sysConfigList) {
            sysConfigList.forEach(option -> sysConfig.put(option.getConfigKey(), option.getConfigValue()));
        }
        return sysConfig;
    }

    /**
     * 批量保存设置
     *
     * @param configs configs
     */
    @Override
    @CacheEvict(value = SYSCONFIG_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public void saveConfigs(Map<String, String> configs) {
        if (null != configs && !configs.isEmpty()) {
            /*for (String key : configs.keySet()) {
                String value = configs.get(key);
                System.out.println(key + "--" + value);
            }*/
            configs.forEach((k, v) -> saveConfig(k, v));
        }
    }

    /**
     * 保存单个设置选项
     *
     * @param key   key
     * @param value value
     */
    /**
     * @CacheEvict()注解:移除指定缓存区间的一个或者多个缓存对象
     * @param value + key 或者 value + allEntries=true
     * 1.value + key 移除value缓存区间内的键为key的数据
     * 2.value + allEntries=true 移除value缓存区间内的所有数据
     */
    @Override
    @CacheEvict(value = SYSCONFIG_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public void saveConfig(String key, String value) {
        SysConfig sysConfig = null;
     /*   if (StringUtils.equals(value, "")) {
            sysConfig = new SysConfig();
            sysConfig.setConfigKey(key);
            this.removeConfig(sysConfig);
        } else {*/
            if (StringUtils.isNotEmpty(key)) {
                //如果查询到有该设置选项则做更新操作，反之保存新的设置选项
                if (null == sysConfigMapper.findSysConfigByConfigKey(key)) {
                    sysConfig = new SysConfig();
                    sysConfig.setConfigKey(key);
                    sysConfig.setConfigValue(value);
                    sysConfigMapper.insert(sysConfig);
                } else {
                    sysConfig = sysConfigMapper.findSysConfigByConfigKey(key);
                    sysConfig.setConfigValue(value);
                    sysConfig.setUpdateTime(new Date());
                    sysConfigMapper.updateById(sysConfig);
                }
            }
       /* }*/
    }
    @CacheEvict(value = SYSCONFIG_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public void removeConfig(SysConfig sysConfig) {
        sysConfigMapper.deleteById(sysConfig);
    }

    /**
     * 根据key查询单个设置选项
     *
     * @param key key
     * @return String
     */
    @Override
    @Cacheable(value = SYSCONFIG_CACHE_NAME, key = "'configs_key_'+#key", unless = "#result == null")
    public String findOneConfig(String key) {
        SysConfig sysConfig = sysConfigMapper.findSysConfigByConfigKey(key);
        if (null != sysConfig) {
            return sysConfig.getConfigValue();
        }
        return null;
    }

}
