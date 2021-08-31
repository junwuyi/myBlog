package ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.entity.Music;
import ssm.mapper.MusicMapper;
import ssm.service.MusicService;

import java.util.List;

/**
 * @author chen
 * @create 2019-12-26 15:02
 */
@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicMapper musicMapper;

    @Override
    public void save(Music music) {
        musicMapper.save(music);
    }

    @Override
    public void update(Music music) {
        musicMapper.update(music);
    }

    @Override
    public List<Music> findAll() {
        return musicMapper.findAll();
    }

    @Override
    public PageInfo<Music> listMusicByPage(Integer pageIndex, Integer pageSize) {
        //分页拦截
        PageHelper.startPage(pageIndex, pageSize);
        List<Music> musics = musicMapper.findAll();

        return new PageInfo<>(musics);
    }

    @Override
    public Music getMusicById(Integer id) {
        return musicMapper.getMusicById(id);
    }

    @Override
    public void delMusicById(Integer id) {
        musicMapper.delMusicById(id);
    }
}
