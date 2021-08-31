package ssm.service;

import ssm.entity.Tag;

import java.util.List;

/**
 * @author chen
 * @create 2019-07-03 17:24
 */
public interface TagService {
    /**
     * 获得标签总数
     *
     * @return 数量
     */
    Integer countTag() ;

    /**
     * 获得标签列表
     *
     * @return 标签列表
     */
    List<Tag> listTag() ;

    /**
     * 获得标签列表
     *
     * @return 标签列表
     */
    List<Tag> listTagWithCount() ;

    /**
     * 转换标签字符串为实体集合
     *
     * @param tagList tagList
     * @return List
     */
    List<Tag> strListToTagList(String tagList);

    /**
     * 根据id获得标签信息
     *
     * @param id 标签ID
     * @return 标签
     */
    Tag getTagById(Integer id) ;

    /**
     * 添加标签
     *
     * @param tag 标签
     * @return 标签
     */
    Tag insertTag(Tag tag) ;

    /**
     * 修改标签
     *
     * @param tag 标签
     */
    void updateTag(Tag tag) ;

    /**
     * 删除标签
     *
     * @param id 标签iD
     */
    void deleteTag(Integer id) ;

    /**
     * 根据标签名获取标签
     *
     * @param name 标签名称
     * @return 标签
     */
    Tag getTagByName(String name) ;

    /**
     * 根据文章ID获得标签
     *
     * @param articleId 文章ID
     * @return 标签列表
     */
    List<Tag> listTagByArticleId(Integer articleId);

}
