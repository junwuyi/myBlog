package ssm.service;

import com.github.pagehelper.PageInfo;
import ssm.entity.Article;

import java.util.HashMap;
import java.util.List;
/**
 * @author chen
 * @create 2019-07-03 17:21
 */
public interface ArticleService {

    /**
     * 分页显示
     *
     * @param pageIndex 第几页开始
     * @param pageSize  一页显示多少
     * @param criteria  查询条件
     * @return 文章列表
     */
    PageInfo<Article> pageArticle(Integer pageIndex,
                                  Integer pageSize,
                                  HashMap<String, Object> criteria);
    /**
     * 添加文章
     *
     * @param article 文章
     */
    void insertArticle(Article article);

    /**
     * 批量删除
     * @param delList
     */
    void deleteBatch(List<String> delList);

    /**
     * 批量更新文章状态
     * @param itemsList
     * @param articleStatus
     */
    void updateBatch(List<String> itemsList, Integer articleStatus);

    /**
     * 根据文章id获取文章
     * @param articleId
     * @return
     */
    Article getByArticleId(Integer articleId);

    /**
     * 更新文章
     * @param article
     */
    void updateArticleDetail(Article article);

    /**
     * 根据文章状态统计文章数量
     * @param status
     * @return
     */
    Integer countArticle(String status);

    /**
     * 根据标签id统计文章数
     * @param tagId
     * @return
     */
    Integer countArticleByTagId(Integer tagId);

    /**
     * 根据分类id统计文章数
     * @param id
     * @return
     */
    Integer countArticleByCategoryId(Integer id);

    /**
     * 更新文章的评论数
     *
     * @param articleId 文章ID
     */
    void updateCommentCount(Integer articleId);

    /**
     * 文章详情页面显示
     *
     * @param status 状态
     * @param id     文章ID
     * @return 文章
     */
    Article getArticleByStatusAndId(Integer status, Integer id);

    /**
     * 修改文章简单信息
     *
     * @param article 文章
     */
    void updateArticle(Article article);

    /**
     * 获取评论总数
     *
     * @return 数量
     */
    Integer countArticleComment();

    /**
     * 获得随机文章
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<Article> listRandomArticle(Integer limit);

    /**
     * 获得评论数较多的文章
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<Article> listArticleByCommentCount(Integer limit);

    /**
     * 获得下一篇文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Article getAfterArticle(Integer id);

    /**
     * 获得上一篇文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Article getPreArticle(Integer id);

    /**
     * 获得相关文章
     *
     * @param cateId 分类ID
     * @param limit  查询数量
     * @return 列表
     */
    List<Article> listArticleByCategoryId(Integer cateId, Integer limit);

    /**
     * 根据文章ID获得分类ID列表
     *
     * @param articleId 文章Id
     * @return 列表
     */
    List<Integer> listCategoryIdByArticleId(Integer articleId);

    /**
     * 获得相关文章
     *
     * @param cateIds 分类ID集合
     * @param limit   数量
     * @return 列表
     */
    List<Article> listArticleByCategoryIds(List<Integer> cateIds, Integer limit);

    /**
     * 获取访问量较多的文章
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<Article> listArticleByViewCount(Integer limit);

    /**
     * 获得所有的文章
     *
     * @return 列表
     */
    List<Article> listAllNotWithContent();

    /**
     * 获得浏览量总数
     *
     * @return 数量
     */
    Integer countArticleView();

    /**
     * 获得最后更新记录
     *
     * @return 文章
     */
    Article getLastUpdateArticle();
}
