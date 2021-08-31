package ssm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chen
 * @create 2019-08-12 15:33
 */
@Data
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 5088697673359856350L;

    private Integer id;
    /**
     * 设置项名称
     */
    private String configKey;

    /**
     * 设置项的值
     */
    private String configValue;

    private Date createTime;
    private Date updateTime;
}
