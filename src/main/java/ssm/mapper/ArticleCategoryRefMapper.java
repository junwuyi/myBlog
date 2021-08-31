package ssm.mapper;

import org.apache.ibatis.annotations.Param;
import ssm.entity.ArticleCategoryRef;
import ssm.entity.Category;

import java.util.List;

/**
 * @author chen
 * @create 2019-07-05 12:56
 */
public interface ArticleCategoryRefMapper {

    /**
     * 根据文章ID获得分类列表
     *
     * @param articleId 文章ID
     * @return 分类列表
     */
    List<Category> listCategoryByArticleId(Integer articleId);


    /**
     * 添加文章和分类关联记录
     * @param record 关联对象
     * @return 影响行数
     */
    int insert(ArticleCategoryRef record);

    /**
     * 根据分类ID统计文章数
     * @param categoryId 分类ID
     * @return 文章数量
     */
    int countArticleByCategoryId(Integer categoryId);

    /**
     * 根据文章id删除文章和分类关联记录
     * @param articleId
     */
    Integer deleteByArticleId(Integer articleId);

    /**
     * 批量删除文章和分类关联记录
     *
     * @param ids 文章Id列表
     * @return 影响行数
     */
    Integer deleteBatch(@Param("ids") List<String> ids);

    /**
     * 根据文章ID查询分类ID
     *
     * @param articleId 文章ID
     * @return 分类ID列表
     */
    List<Integer> selectCategoryIdByArticleId(Integer articleId);
}
