package ssm.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import ssm.entity.Attachment;

import java.util.HashMap;
import java.util.List;

/**
 *
 */
@Mapper
public interface AttachmentMapper {

    /**
     * 查询所有，分页
     *
     * @param page 分页信息
     * @return
     */
    List<Attachment> findAllByPage(HashMap<String, Object> criteria, Page<Attachment> page);


    /**
     * 查询所有，不分页
     *
     * @return List
     */
    List<Attachment> findAll();

    void updateById(Attachment attachment);

    /**
     * 添加附件信息到数据库
     * @param attachment
     */
    void insert(Attachment attachment);

    /**
     * 根据id查询附件信息
     * @param attachId
     * @return
     */
    Attachment selectById(Long attachId);

    /**
     * 获取附件总数
     * @return
     */
    Integer getCount();

    /**
     * 根据编号移除附件
     *
     * @param attachId attachId
     * @return Attachment
     */
    void deleteById(Long attachId);
}

