package ssm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     附件
 * </pre>
 *
 * @author : chen
 * @date : 2019/7/10
 */
@Data
public class Attachment implements Serializable {

    private static final long serialVersionUID = 3060117944880138063L;

    /**
     * 附件编号
     */
    private Long attachId;

    /**
     * 附件名
     */
    private String attachName;


    /**
     * 附件路径
     */
    private String attachPath;

    /**
     * 附件缩略图路径
     */
    private String attachSmallPath;

    /**
     * 附件类型
     */
    private String attachType;

    /**
     * 附件后缀
     */
    private String attachSuffix;

    /**
     * 上传时间
     */
    private Date attachCreated;

    /**
     * 附件大小
     */
    private String attachSize;

    /**
     * 附件长宽
     */
    private String attachWh;

    /**
     * 附件存储地址
     */
    private String attachLocation;

    /**
     * 附件来源，0：上传，1：外部链接
     */
    private Integer attachOrigin = 0;
}
