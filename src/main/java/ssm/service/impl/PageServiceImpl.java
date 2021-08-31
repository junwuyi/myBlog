package ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ssm.entity.Page;
import ssm.mapper.PageMapper;
import ssm.service.PageService;

import java.util.List;

/**
 * @author chen
 * @date 2017/9/7
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired(required = false)
    private PageMapper pageMapper;

    @Override
    @Cacheable(value = "pages", key = "'page_key_status_'+#status+'_'+#key")
    public Page getPageByKey(Integer status, String key) {
        return pageMapper.getPageByKey(status, key);
    }

    @Override
    @Cacheable(value = "pages", key = "'page_id_'+#id")
    public Page getPageById(Integer id) {
        return pageMapper.getPageById(id);
    }

    @Override
    @Cacheable(value = "pages", key = "'list_page_'+#status")
    public List<Page> listPage(Integer status) {
        return pageMapper.listPage(status);
    }


    @Override
    @CacheEvict(value = "pages", allEntries = true)
    public void insertPage(Page page) {
        pageMapper.insert(page);
    }

    @Override
    @CacheEvict(value = "pages", allEntries = true)
    public void deletePage(Integer id) {
        pageMapper.deleteById(id);
    }

    @Override
    @CacheEvict(value = "pages", allEntries = true)
    public void updatePage(Page page) {
        pageMapper.update(page);
    }
}
