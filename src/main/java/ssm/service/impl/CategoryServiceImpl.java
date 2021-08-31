package ssm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ssm.entity.Category;
import ssm.mapper.ArticleCategoryRefMapper;
import ssm.mapper.CategoryMapper;
import ssm.service.CategoryService;

import java.util.List;


/**
 * 分类管理
 *
 * @author chen
 * @date 2019/8/24
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleCategoryRefMapper articleCategoryRefMapper;

    /**
     * 统计文章分类总数
     * @return
     */
    @Override
    @Cacheable(value = "category", key = "'count_category'")
    public Integer countCategory() {
        Integer count = 0;
        try {
            count = categoryMapper.countCategory();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计分类失败, cause:{}", e);
        }
        return count;
    }

    /**
     * 获取分类列表
     * @return
     */
    @Override
    @Cacheable(value = "category", key = "'list_category'")
    public List<Category> listCategory() {
        List<Category> categoryList = null;
        try {
            categoryList = categoryMapper.listCategory();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得分类列表失败, cause:{}", e);
        }
        return categoryList;
    }

    /**
     * 获得分类列表和该分类的文章数量
     * @return
     */
    @Override
    @Cacheable(value = "category", key = "'list_category_with_count'")
    public List<Category> listCategoryWithCount() {
        List<Category> categoryList = categoryMapper.listCategory();
        try {
            if (categoryList != null) {
                for (int i = 0; i<categoryList.size(); i++ ) {
                    int count = articleCategoryRefMapper.countArticleByCategoryId(categoryList.get(i).getCategoryId());
                    categoryList.get(i).setArticleCount(count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得分类列表和该分类的文章数量失败, cause:{}", e);
        }
        return categoryList;
    }

    @Override
    @CacheEvict(value = "category", allEntries = true)
    public void deleteCategory(Integer id) {
        try {
            categoryMapper.deleteCategory(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除分类失败, cause:{}", e);
        }
    }

    /**
     * 根据id查询分类
     * @param id     ID
     * @return
     */
    @Override
    @Cacheable(value = "category", key = "'category_id_'+#id")
    public Category getCategoryById(Integer id) {
        Category category = null;
        try {
            category = categoryMapper.getCategoryById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据分类ID获得分类失败, id:{}, cause:{}", id, e);
        }
        return category;
    }

    /**
     * 添加分类
     * @param category 分类
     * @return
     */
    @Override
    @CacheEvict(value = "category", allEntries = true)
    public Category insertCategory(Category category) {
        try {
            categoryMapper.insert(category);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建分类失败, category:{}, cause:{}", category, e);
        }
        return category;
    }

    /**
     * 修改分类
     * @param category 分类
     */
    @Override
    @CacheEvict(value = "category", allEntries = true)
    public void updateCategory(Category category) {
        categoryMapper.update(category);
    }

    /**
     * 根据分类名称获取分类
     * @param name 名称
     * @return
     */
    @Override
    @Cacheable(value = "category", key = "'category_name_'+#name")
    public Category getCategoryByName(String name) {

        Category category = null;
        try {
            category = categoryMapper.getCategoryByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据分类名称获取分类失败, category:{}, cause:{}", category, e);
        }

        return category;
    }

    @Override
    public Integer selectChildCateId(Integer cateId) {
        return null;
    }

    @Override
    @Cacheable(value = "category", key = "'child_category_'+#id")
    public List<Category> findChildCategory(Integer id) {
        return categoryMapper.findChildCategory(id);
    }

    /**
     * 获得某个分类的子分类Id
     *
     * @param cateId 分类Id
     * @return 子分类Id
     */
    //@Override
   /* public Integer selectChildCateId(Integer cateId) {

        return categoryMapper.selectChildCateIds(cateId);
    }*/
}
