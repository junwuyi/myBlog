package ssm.service;

import com.github.pagehelper.PageInfo;
import ssm.entity.Music;
import ssm.entity.SysLog;

import java.util.List;

/**
 * @author chen
 * @create 2019-08-08 16:10
 */
public interface MusicService {

    void save(Music music);

    void update(Music music);

    /*查询所有*/
    List<Music> findAll();

    /**
     * 获取所有列表
     *
     * @param pageIndex 第几页开始
     * @param pageSize  一页显示数量
     * @return 列表
     */
    PageInfo<Music> listMusicByPage(Integer pageIndex, Integer pageSize);

    Music getMusicById(Integer id);

    void delMusicById(Integer id);

}
