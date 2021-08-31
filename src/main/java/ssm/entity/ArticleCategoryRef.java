package ssm.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 文章分类关联表
 * @author chen
 * @create 2019-07-05 13:15
 */
@Data
public class ArticleCategoryRef implements Serializable {

    private Integer articleId;

    private Integer categoryId;

    public ArticleCategoryRef() {
    }

    public ArticleCategoryRef(Integer articleId, Integer categoryId) {
        this.articleId = articleId;
        this.categoryId = categoryId;
    }
}
