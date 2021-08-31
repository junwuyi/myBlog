package ssm.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author chen
 * @create 2019-08-07 17:38
 */
@Data
public class SysLog {

    private Integer id;
    /**
     * 访问时间
     */
    private Date visitTime;

    /**
     * 操作者用户名
     */
    private String userName;

    /**
     * ip
     */
    private String ip;

    /**
     * 访问资源url
     */
    private String url;

    /**
     * 执行时长
     */
    private Long executionTime;

    /**
     * 访问内容
     */
    private String content;


}
