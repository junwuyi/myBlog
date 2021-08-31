package ssm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.entity.SysResources;
import ssm.mapper.SysResourceMapper;
import ssm.service.ResourceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chen
 * @create 2019-07-27 16:25
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    public List<SysResources> findAll() {
        List<SysResources> sysResources = sysResourceMapper.findAll();
        return sysResources;
    }
      /*  //取得数据
        List<HashMap<Object, Object>> resultMap = sysResourceMapper.findAll();
        System.out.println("resultMap"+resultMap);
        //定义一个Map集合 存储按指定顺序排列好的菜单
        Map<String, List<Map<Object,Object>>> temp = new HashMap<String, List<Map<Object,Object>>>();

        for (HashMap<Object,Object> map : resultMap) {
            //如果temp的键包含这个parentid
            if(temp.containsKey(map.get("parent_id").toString())){
                //那么把所有相同parentid的数据全部添加到该parentid键下
                temp.get(map.get("parent_id").toString()).add(map);
            }else{
                //初始化temp  第一次用
                List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
                list.add(map);
                temp.put(map.get("parent_id").toString(), list);
            }
        }
        //定义一个完整菜单列表
        ArrayList<Map<Object,Object>> array = new ArrayList<Map<Object,Object>>();

        for (HashMap<Object, Object> hashMap : resultMap) {
            //如果temp中的键与当前id一致
            if(temp.containsKey(hashMap.get("id").toString())){
                //说明temp是当前id菜单的子菜单
                hashMap.put("nodes", temp.get(hashMap.get("id").toString()));
            }
            //遇到顶级菜单就添加进完整菜单列表
            if(hashMap.get("parent_id").toString().equals("0")){
                array.add(hashMap);
            }

        }
        System.out.println("array-"+array);
        return JSONArray.toJSONString(array);

    }*/

    /*public static void main(String[] args) {
        ResourceServiceImpl resourceService = new ResourceServiceImpl();
        String all = resourceService.findAll();
        System.out.println(all);
    }*/

}
