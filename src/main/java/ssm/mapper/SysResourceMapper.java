package ssm.mapper;

import org.apache.ibatis.annotations.Mapper;
import ssm.entity.SysResources;

import java.util.HashMap;
import java.util.List;

/**
 * @author chen
 * @create 2019-07-27 16:04
 */
@Mapper
public interface SysResourceMapper {

    List<SysResources> findAll();
}
