package ssm.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 6687286913317513141L;

    /**
     * 分类编号
     */
    private Integer categoryId;

    /**
     * 分类父节点
     */
    private Integer categoryPid;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String categoryDescription;

    private Integer categoryOrder;

    /**
     * 分类图标
     */
    private String categoryIcon;

   /**
    *
    *分类路径
    */

    //private String categoryUrl;

    /**
     * 文章数量(非数据库字段)
     */
    private Integer articleCount;

    public Category(Integer categoryId, Integer categoryPid, String categoryName, String categoryDescription, Integer categoryOrder, String categoryIcon,Integer articleCount) {
        this.categoryId = categoryId;
        this.categoryPid = categoryPid;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryOrder = categoryOrder;
        this.categoryIcon = categoryIcon;
        //this.categoryUrl = categoryUrl;
        this.articleCount = articleCount;
    }

    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 未分类
     *
     * @return 分类
     */
    public static Category Default() {
        return new Category(100000000, "未分类");
    }


}