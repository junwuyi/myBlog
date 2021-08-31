package ssm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chen
 */
@Data
public class Link implements Serializable{


    private static final long serialVersionUID = -259829372268799508L;

    /**
     * 友情链接编号
     */
    private Integer linkId;

    /**
     * 友情链接地址
     */
    private String linkUrl;

    /**
     * 友情链接名称
     */
    private String linkName;

    /**
     * 友情链接头像
     */
    private String linkImage;

    /**
     * 友情链接描述
     */
    private String linkDescription;

    private String linkOwnerNickname;

    /**
     * 友情链接联系方式
     */
    private String linkOwnerContact;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 联系qq
     */
    private String qq;

    private Date linkUpdateTime;

    private Date linkCreateTime;

    /**
     * 友情链接排序
     */
    private Integer linkOrder;

    /**
     * 友情链接状态
     * 1 显示,   0 隐藏
     */
    private Integer linkStatus;

}