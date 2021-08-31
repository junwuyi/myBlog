package ssm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ssm.entity.Link;

import java.util.List;

/**
 * @author chen
 */
@Mapper
public interface LinkMapper {

    /**
     * 删除
     * @param linkId 链接ID
     * @return 影响行数
     */
    int deleteById(Integer linkId);

    /**
     * 添加
     * 
     * @param link 链接
     * @return 影响行数
     */
    int insert(Link link);

    /**
     * 根据ID查询
     * 
     * @param linkId 链接ID
     * @return 影响行数
     */
    Link getLinkById(Integer linkId);

    /**
     * 更新
     * 
     * @param link 链接ID
     * @return 影响行数
     */
    int update(Link link);

    /**
     * 获得链接总数
     * 
     * @param status 状态
     * @return 数量
     */
    Integer countLink(@Param(value = "status") Integer status);

    /**
     * 获得链接列表
     * 
     * @param status 状态
     * @return  列表
     */
    List<Link> listLink(@Param(value = "status") Integer status);

    /**
     * 批量更新link状态
     * @param ids
     * @param linkStatus
     * @return
     */
    void updateBatch(@Param("ids") List<String> ids, @Param("linkStatus") Integer linkStatus);

    /**
     * 批量删除link
     *
     * @param ids 文章Id列表
     * @return 影响行数
     */
    Integer deleteBatch(@Param("ids") List<String> ids);
}