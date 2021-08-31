package ssm.service;

import ssm.entity.SysResources;

import java.util.HashMap;
import java.util.List;

/**
 * @author chen
 * @create 2019-07-27 16:20
 */
public interface ResourceService {

    List<SysResources> findAll();
}
