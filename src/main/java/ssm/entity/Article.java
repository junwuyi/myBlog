package ssm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chen
 * @create 2019-07-03 15:52
 */
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 5207865247400761539L;

    private Integer articleId;//id

    private Integer articleUserId;//博主（发布人）

    private String articleTitle;//标题

    private Integer articleViewCount;//阅读量

    private Integer articleCommentCount;//评论量

    private Integer articleLikeCount;//喜欢量

    private Date articleCreateTime;//发布时间

    private Date articleUpdateTime;//修改时间

    private Integer articleIsComment;//是否可以评论 1可以 0不可以

    private String articleKeyWords;//关键字

    /**
     * 状态
     * 1 已发布
     * 0 草稿
     * 2 回收站
     */
    private Integer articleStatus;

    private Integer articleOrder;//排序

    private String articleContent;//内容

    private String articleSummary; //摘要
    //article_thumbnail
    private String articleThumbnail;// 缩略图

    //以下是非数据库字段
    private User user;//发布人

    private List<Tag> tagList;//标签

    private List<Category> categoryList;//分类

}
