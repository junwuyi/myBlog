package ssm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ssm.entity.Tag;
import ssm.mapper.ArticleTagRefMapper;
import ssm.mapper.TagMapper;
import ssm.service.TagService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @create 2019-07-06 10:16
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagRefMapper articleTagRefMapper;

    /**
     * 统计标签个数
     * @return
     */
    @Override
    @Cacheable(value = "tags", key = "'count_tag'")
    public Integer countTag() {
        return tagMapper.countTag();
    }

    /**
     * 获取标签列表
     * @return
     */
    @Override
    @Cacheable(value = "tags", key = "'list_tag'")
    public List<Tag> listTag() {
        List<Tag> tagList = null;
        try {
            tagList = tagMapper.listTag();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得所有标签失败, cause:{}", e);
        }
        return tagList;
    }

    /**
     * 获取所有的标签和该标签对应文章数量
     * @return
     */
    @Override
    @Cacheable(value = "tags", key = "'list_tag_count'")
    public List<Tag> listTagWithCount() {
        List<Tag> tagList = null;
        try {
            tagList = tagMapper.listTag();
            for (int i = 0; i < tagList.size(); i++) {
                Integer count = articleTagRefMapper.countArticleByTagId(tagList.get(i).getTagId());
                tagList.get(i).setArticleCount(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取所有的标签和该标签对应文章数量, cause:{}", e);
        }
        return tagList;
    }

    /**
     * 转换标签字符串为实体集合
     *
     * @param tagList tagList
     * @return List
     */
    @Override
    public List<Tag> strListToTagList(String tagList) {
        //public String[] split(String regex)根据给定正则表达式的匹配拆分此字符串。
        String[] tags = tagList.split(",");
        List<Tag> tagsList = new ArrayList<>();
        for (String tag : tags) {
            //根据标签名查询标签
            Tag t = getTagByName(tag);
            Tag nt = null;
            //存在这样的标签直接添加的数组中
            if (null != t) {
                tagsList.add(t);
            } else {
                //不存在
                nt = new Tag();
                nt.setTagName(tag);
                //nt.setTagUrl(tag);
                tagsList.add(insertTag(nt));
            }
        }
        return tagsList;
    }


    /**
     * 根据id获取标签
     * @param id 标签ID
     * @return
     */
    @Override
    @Cacheable(value = "tags", key = "'tag_id_'+#id")
    public Tag getTagById(Integer id) {
        return tagMapper.getTagById(id);
    }

    /**
     * 添加新标签
     * @param tag 标签
     * @return
     */
    @Override
    @CacheEvict(value = "tags",allEntries = true)
    public Tag insertTag(Tag tag) {
        try {
            tagMapper.insert(tag);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加标签失败, tag:{}, cause:{}", tag, e);
        }
        return tag;
}

    @Override
    @CacheEvict(value = "tags",allEntries = true)
    public void updateTag(Tag tag) {

        tagMapper.update(tag);

    }

    /**
     * 根据id删除标签
     * @param id 标签iD
     */
    @Override
    @CacheEvict(value = "tags",allEntries = true)
    public void deleteTag(Integer id) {
        tagMapper.deleteById(id);
    }

    /**
     * 根据标签名获取标签
     * @param name 标签名称
     * @return
     */
    @Override
    @Cacheable(value = "tags", key = "'tag_name_'+#name")
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    @Cacheable(value = "tags", key = "'tag_article_id_'+#articleId")
    public List<Tag> listTagByArticleId(Integer articleId) {
        return null;
    }
}
