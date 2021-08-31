package ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ssm.entity.Menu;
import ssm.mapper.MenuMapper;
import ssm.service.MenuService;

import java.util.List;

/**
 * @author chen
 */
@Service
public class MenuServiceImpl implements MenuService {

    private static final String MENUS_CACHE_NAME = "menus";

    @Autowired(required = false)
    private MenuMapper menuMapper;

    @Override
    @Cacheable(value = MENUS_CACHE_NAME, key = "'menus_all_'+#menuStatus")
    public List<Menu> listMenu(Integer menuStatus) {
        List<Menu> menuList = menuMapper.listMenu(menuStatus);
        return menuList;
    }

    @Override
    @CacheEvict(value = MENUS_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public Menu insertMenu(Menu menu) {
        menuMapper.insert(menu);
        return menu;
    }

    @Override
    @CacheEvict(value = MENUS_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public void deleteMenu(Integer id) {
        menuMapper.deleteById(id);
    }

    @Override
    @CacheEvict(value = MENUS_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public void updateMenu(Menu menu) {
        menuMapper.update(menu);
    }

    @Override
    @Cacheable(value = MENUS_CACHE_NAME, key = "'menus_id_'+#id", unless = "#result == null")
    public Menu getMenuById(Integer id) {
        return menuMapper.getMenuById(id);
    }
}
