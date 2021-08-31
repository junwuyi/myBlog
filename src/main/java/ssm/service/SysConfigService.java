package ssm.service;


import ssm.entity.SysConfig;

import java.util.Map;

/**
 * 系统配置
 * @author chen
 */
public interface SysConfigService {

    /**
     * 获取系统配置
     *
     * @return
     */
    Map<String, String> getConfigs();

    /**
     * 保存单个设置选项
     *
     * @param key   key
     * @param value value
     */
    void saveConfig(String key, String value);

    /**
     * 保存多个设置选项
     *
     * @param configs options
     */
    void saveConfigs(Map<String, String> configs);

    /**
     * 移除设置选项
     *
     * @param configs configs
     */
    void removeConfig(SysConfig configs);

    /**
     * 根据key查询单个设置
     *
     * @param key key
     * @return String
     */
    String findOneConfig(String key);
}
