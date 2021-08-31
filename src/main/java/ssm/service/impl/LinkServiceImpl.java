package ssm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ssm.entity.Link;
import ssm.mapper.LinkMapper;
import ssm.service.LinkService;

import java.util.List;

/**
 *
 * @author chen
 * @date 2017/9/4
 */
@Service
@Slf4j
public class LinkServiceImpl implements LinkService {
	
	@Autowired(required = false)
	private LinkMapper linkMapper;

	/**
	 * 根据链接状态统计链接个数
	 * @param status 状态
	 * @return
	 */
	@Override
	@Cacheable(value = "links", key = "'link_status_'+#status")
	public Integer countLink(Integer status)  {
		return linkMapper.countLink(status);
	}
	
	@Override
	@Cacheable(value = "links", key = "'link_list_'+#status")
	public List<Link> listLink(Integer status)  {
		return linkMapper.listLink(status);
	}

	@Override
	@CacheEvict(value = "links", allEntries = true,beforeInvocation = true)
	public void insertLink(Link link)  {
		linkMapper.insert(link);
	}

	@Override
	@CacheEvict(value = "links", allEntries = true,beforeInvocation = true)
	public void deleteLink(Integer id)  {
		linkMapper.deleteById(id);
	}

	@Override
	@CacheEvict(value = "links", allEntries = true,beforeInvocation = true)
	public void updateLink(Link link)  {
		linkMapper.update(link);
	}

	@Override
	@Cacheable(value = "links", key = "'link_id_'+#id")
	public Link getLinkById(Integer id)  {
		return linkMapper.getLinkById(id);
	}

	/**
	 * 批量更新link状态
	 *
	 * @param itemsList
	 * @param linkStatus
	 * @return
	 */
	@Override
	@CacheEvict(value = "links", allEntries = true,beforeInvocation = true)
	public void updateBatch(List<String> itemsList, Integer linkStatus) {

		try {
			linkMapper.updateBatch(itemsList, linkStatus);
		} catch (Exception e) {
			log.info("log service{批量更新link状态失败}" + e.getMessage());
		}
	}

	/**
	 * 批量删除
	 * @param delList
	 */
	@Override
	@CacheEvict(value = "links", allEntries = true,beforeInvocation = true)
	public void deleteBatch(List<String> delList) {
		linkMapper.deleteBatch(delList);
	}

}
