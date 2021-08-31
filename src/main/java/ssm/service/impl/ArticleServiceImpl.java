package ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssm.entity.*;
import ssm.enums.ArticleCommentStatus;
import ssm.mapper.*;
import ssm.service.ArticleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 文章Servie实现
 *
 * @author chen
 * @date 2019/7/5
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private static final String ARTICLES_CACHE_NAME = "articles";

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleCategoryRefMapper articleCategoryRefMapper;

    @Autowired
    private ArticleTagRefMapper articleTagRefMapper;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 分页查询
     * @param pageIndex 第几页开始
     * @param pageSize  一页显示多少
     * @param criteria  查询条件
     * @return
     */
    @Override
    //@Cacheable(value = ARTICLES_CACHE_NAME, key = "'page_article'")
    public PageInfo<Article> pageArticle(Integer pageIndex,
                                         Integer pageSize,
                                         HashMap<String, Object> criteria) {
        //分页拦截
        PageHelper.startPage(pageIndex,pageSize);
        List<Article> articleList = articleMapper.findAll(criteria);
        //for(int i=0,len=list.size();i<len;i++)
        for (int i=0,len=articleList.size(); i < len; i++) {
            //封装CategoryList
            List<Category> categoryList = articleCategoryRefMapper.listCategoryByArticleId(articleList.get(i).getArticleId());
            if (categoryList == null || categoryList.size() == 0) {
                categoryList = new ArrayList<>();
                //未分类
                categoryList.add(Category.Default());
            }
            articleList.get(i).setCategoryList(categoryList);

           //查询文章所属作者
            User user = userMapper.getUserById(articleList.get(i).getArticleUserId());
            articleList.get(i).setUser(user);
            //封装TagList
//            List<Tag> tagList = articleTagRefMapper.listTagByArticleId(articleList.get(i).getArticleId());
//            articleList.get(i).setTagList(tagList);
        }
        return new PageInfo<>(articleList);
    }

    /**
     * 添加文章
     * @param article 文章
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = ARTICLES_CACHE_NAME, allEntries = true)
    public void insertArticle(Article article) {
        //添加文章
        article.setArticleCreateTime(new Date());
        article.setArticleUpdateTime(new Date());
        article.setArticleIsComment(ArticleCommentStatus.ALLOW.getValue());
        article.setArticleViewCount(0);
        article.setArticleLikeCount(0);
        article.setArticleCommentCount(0);
        articleMapper.insert(article);
        //添加分类和文章关联
        for (int i = 0; i < article.getCategoryList().size(); i++) {
            ArticleCategoryRef articleCategoryRef = new ArticleCategoryRef(article.getArticleId(), article.getCategoryList().get(i).getCategoryId());
            articleCategoryRefMapper.insert(articleCategoryRef);
        }
        //添加标签和文章关联
        if (article.getTagList() != null) {
            for (int i = 0; i < article.getTagList().size(); i++) {
                //postTagRefMapper.insert(new PostTagRef(post.getPostId(), post.getTags().get(i).getTagId()));
                articleTagRefMapper.insert(new ArticleTagRef(article.getArticleId(),article.getTagList().get(i).getTagId()));
            }
        }

    }

    /**
     * 批量删除文章
     * @param delList
     */
    @Override
    @CacheEvict(value = ARTICLES_CACHE_NAME, allEntries = true)
    public void deleteBatch(List<String> delList) {

        articleCategoryRefMapper.deleteBatch(delList);

        articleTagRefMapper.deleteBatch(delList);

        commentMapper.deleteBatch(delList);

        articleMapper.deleteBatch(delList);


       /* Post post = this.findByPostId(postId);
        if (post != null) {
            postTagRefMapper.deleteByPostId(postId);
            postCategoryRefMapper.deleteByPostId(postId);
            postMapper.deleteById(post.getPostId());
        }*/
    }

    /**
     * 批量更新文章状态
     * @param itemsList
     * @param articleStatus
     */
    @Override
    @CacheEvict(value = ARTICLES_CACHE_NAME, allEntries = true)
    public void updateBatch(List<String> itemsList, Integer articleStatus) {

        try {
            articleMapper.updateBatch(itemsList, articleStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文章id获取文章
     * @param articleId
     * @return
     */
    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'article_id_'+#articleId")
    public Article getByArticleId(Integer articleId) {

        Article article = articleMapper.getByArticleId(articleId);
        if (article != null) {
            //查询出该文章已有的分类信息
            List<Category> categoryList = articleCategoryRefMapper.listCategoryByArticleId(article.getArticleId());
            //查询出该文章已有的标签信息
            List<Tag> tagList = articleTagRefMapper.listTagByArticleId(article.getArticleId());
            article.setCategoryList(categoryList);
            article.setTagList(tagList);
        }
        return article;
    }

    /**
     * 更新文章
     * @param article
     */
    @Override
    @CacheEvict(value = ARTICLES_CACHE_NAME, allEntries = true)
    public void updateArticleDetail(Article article) {
        //设置更新时间为当前时间
        article.setArticleUpdateTime(new Date());
        //更新文章
        articleMapper.update(article);

        if (article.getCategoryList() != null) {
            //删除分类和文章关联
            articleCategoryRefMapper.deleteByArticleId(article.getArticleId());
            //添加新的文章和分类关联
            for (int i = 0; i < article.getCategoryList().size(); i++) {
                articleCategoryRefMapper.insert(new ArticleCategoryRef(article.getArticleId(), article.getCategoryList().get(i).getCategoryId()));
            }
        }

        if (article.getTagList() != null) {
            //删除标签和文章关联
            articleTagRefMapper.deleteByArticleId(article.getArticleId());
            // 添加新的文章和标签关联
            for (int i = 0; i<article.getTagList().size(); i++) {
                articleTagRefMapper.insert(new ArticleTagRef(article.getArticleId(), article.getTagList().get(i).getTagId()));
            }
        }
    }

    /**
     * 根据文章状态统计文章数量
     * @param status
     * @return
     */
    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'article_status_'+#status")
    public Integer countArticle(String status) {
      return  articleMapper.countArticle(status);
    }

    /**
     * 根据标签id统计文章数
     * @param tagId
     * @return
     */
    @Override
    public Integer countArticleByTagId(Integer tagId) {
        return articleTagRefMapper.countArticleByTagId(tagId);
    }

    /**
     * 根据分类id统计文章数
     * @param id
     * @return
     */
    @Override
    public Integer countArticleByCategoryId(Integer id) {
        return articleCategoryRefMapper.countArticleByCategoryId(id);
    }

    @Override
    @CacheEvict(value = ARTICLES_CACHE_NAME, allEntries = true)
    public void updateCommentCount(Integer articleId) {
        articleMapper.updateCommentCount(articleId);
    }

    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'article_status_id_'+#status+'_'+#id")
    public Article getArticleByStatusAndId(Integer status, Integer id) {
        Article article = articleMapper.getArticleByStatusAndId(status, id);
        if (article != null) {
            List<Category> categoryList = articleCategoryRefMapper.listCategoryByArticleId(article.getArticleId());
            List<Tag> tagList = articleTagRefMapper.listTagByArticleId(article.getArticleId());
            article.setCategoryList(categoryList);
            article.setTagList(tagList);
        }
        return article;
    }

    /**
     * 修改文章简单信息
     *
     * @param article 文章
     */
    @Override
    @CacheEvict(value = ARTICLES_CACHE_NAME, allEntries = true)
    public void updateArticle(Article article) {
        articleMapper.update(article);
    }

    /**
     * 文章评论总数
     * @return
     */
    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'article_comment'")
    public Integer countArticleComment() {
        Integer count = 0;
        try {
            count = articleMapper.countArticleComment();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计文章评论数失败, cause:{}", e);
        }
        return count;
    }

    @Override
    public List<Article> listRandomArticle(Integer limit) {
        return articleMapper.listRandomArticle(limit);
    }

    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'article_comment_count_'+#limit")
    public List<Article> listArticleByCommentCount(Integer limit) {
        return articleMapper.listArticleByCommentCount(limit);
    }

    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'list_article_view_count_'+#limit")
    public List<Article> listArticleByViewCount(Integer limit) {
        return articleMapper.listArticleByViewCount(limit);
    }

    @Override
    public List<Article> listAllNotWithContent() {
        return articleMapper.listAllNotWithContent();
    }

    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'article_after_'+#id")
    public Article getAfterArticle(Integer id) {
        return articleMapper.getAfterArticle(id);
    }

    /**
     * 上一篇
     * @param id 文章ID
     * @return
     */
    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'article_pre_'+#id")
    public Article getPreArticle(Integer id) {
        return articleMapper.getPreArticle(id);
    }

    @Override
    public List<Article> listArticleByCategoryId(Integer cateId, Integer limit) {
        return articleMapper.findArticleByCategoryId(cateId, limit);
    }

    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'article_category_'+#articleId")
    public List<Integer> listCategoryIdByArticleId(Integer articleId) {
        return articleCategoryRefMapper.selectCategoryIdByArticleId(articleId);
    }

    /**
     * 根据分类ID
     *
     * @param cateIds 分类ID集合
     * @param limit       查询数量
     * @return 文章列表
     */
    @Override
    public List<Article> listArticleByCategoryIds(List<Integer> cateIds, Integer limit) {
        if (cateIds == null || cateIds.size() == 0) {
            return null;
        }
        return articleMapper.findArticleByCategoryIds(cateIds, limit);
    }

    /**
     * 文章访问量
     * @return
     */
    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'article_view_count'")
    public Integer countArticleView() {
        Integer count = 0;
        try {
            count = articleMapper.countArticleView();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计文章访问量失败, cause:{}", e);
        }
        return count;
    }

    //最后更新文章时间
    @Override
    @Cacheable(value = ARTICLES_CACHE_NAME, key = "'last_update_time'")
    public Article getLastUpdateArticle() {
        return articleMapper.getLastUpdateArticle();
    }

}
