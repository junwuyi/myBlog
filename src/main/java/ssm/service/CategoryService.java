package ssm.service;

import ssm.entity.Category;

import java.util.Collection;
import java.util.List;

/**
 * @author chen
 * @create 2019-07-03 17:24
 */
public interface CategoryService {

    /**
     * 获得分类总数
     *
     * @return
     */
    Integer countCategory();


    /**
     * 获得分类列表
     *
     * @return 分类列表
     */
    List<Category> listCategory();

    /**
     * 获得分类列表和该分类的文章数量
     *
     * @return 分类列表
     */
    List<Category> listCategoryWithCount();

    /**
     * 删除分类
     *
     * @param id ID
     */

    void deleteCategory(Integer id);

    /**
     * 根据id查询分类信息
     *
     * @param id     ID
     * @return 分类
     */
    Category getCategoryById(Integer id);

    /**
     * 添加分类
     *
     * @param category 分类
     * @return 分类
     */
    Category insertCategory(Category category);

    /**
     * 更新分类
     *
     * @param category 分类
     */
    void updateCategory(Category category);

    /**
     * 根据分类名获取分类
     *
     * @param name 名称
     * @return 分类
     */
    Category getCategoryByName(String name);

    /**
     * 获得某个分类的子分类Id
     *
     * @param cateId 分类Id
     * @return 子分类Id
     */
    Integer selectChildCateId(Integer cateId);

    List<Category> findChildCategory(Integer id);
}
