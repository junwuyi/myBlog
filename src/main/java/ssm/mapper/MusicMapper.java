package ssm.mapper;

import org.apache.ibatis.annotations.Mapper;
import ssm.entity.Music;

import java.util.List;

/**
 * @author chen
 * @create 2019-12-26 14:37
 */
@Mapper
public interface MusicMapper {

    /**
     * 查询所有
     * @return
     */
    List<Music> findAll();

    void save(Music music);

    void update(Music music);

    void delMusicById(Integer id);

    Music getMusicById(Integer id);
}
