package ssm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chen
 */
@Data
public class Notice implements Serializable{

    private static final long serialVersionUID = -4901560494243593100L;

    /**
     * 公告id
     */
    private Integer noticeId;

    /**
     * 公告id
     */
    private String noticeTitle;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告创建时间
     */
    private Date noticeCreateTime;

    /**
     * 公告修改时间
     */
    private Date noticeUpdateTime;

    /**
     * 公告状态
     * 1 显示，0 隐藏
     */
    private Integer noticeStatus;

    /**
     * 公告排序
     */
    private Integer noticeOrder;
}