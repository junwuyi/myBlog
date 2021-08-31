package ssm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章评论
 * @author CHEN
 */
@Data
public class Comment implements Serializable{

    private static final long serialVersionUID = -1038897351672911219L;
    /**
     * 评论id 自增
     */
    private Integer commentId;

    /**
     * 上一级
     */
    private Integer commentPid;

    private String commentPname;

    /**
     * 评论状态，0：待审核，1：正常，2：回收站
     */
    private Integer commentStatus = 1;

    /**
     * 文章ID
     */
    private Integer commentArticleId;

    /**
     * 评论人
     */
    private String commentAuthorName;

    /**
     * 评论人的邮箱
     */
    private String commentAuthorEmail;

    /**
     * 评论人的主页
     */
    private String commentAuthorUrl;

    /**
     * 评论人头像
     */
    private String commentAuthorAvatar;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论者ua信息
     */
    private String commentAgent;

    /**
     * 评论人的ip
     */
    private String commentIp;

    /**
     * 评论时间
     */
    private Date commentCreateTime;

    /**
     * 角色(管理员1，访客0)
     */
    private Integer commentRole;

    /**
     * 非数据库字段
     */
    private Article article;

}