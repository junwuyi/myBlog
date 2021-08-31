package ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ssm.entity.Notice;
import ssm.mapper.NoticeMapper;
import ssm.service.NoticeService;

import java.util.List;

/**
 * @author chen
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired(required = false)
    private NoticeMapper noticeMapper;

    @Override
    @Cacheable(value = "notice", key = "'list_noticle_status_'+#status")
    public List<Notice> listNotice(Integer status)  {
        return noticeMapper.listNotice(status);
    }

    @Override
    @CacheEvict(value = "notice", allEntries = true,beforeInvocation = true)
    public void insertNotice(Notice notice)  {
        noticeMapper.insert(notice);
    }

    @Override
    @CacheEvict(value = "notice", allEntries = true,beforeInvocation = true)
    public void deleteNotice(Integer id)  {
        noticeMapper.deleteById(id);
    }

    @Override
    @CacheEvict(value = "notice", allEntries = true,beforeInvocation = true)
    public void updateNotice(Notice notice)  {
        noticeMapper.update(notice);
    }

    @Override
    @Cacheable(value = "notice", key = "'list_noticle_id_'+#id")
    public Notice getNoticeById(Integer id)  {
        return noticeMapper.getNoticeById(id);
    }

    @Override
    @CacheEvict(value = "notice", allEntries = true,beforeInvocation = true)
    public void deleteBatch(List<String> delList) {
        noticeMapper.deleteBatch(delList);
    }

}
