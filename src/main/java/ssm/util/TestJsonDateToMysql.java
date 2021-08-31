package ssm.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ResourceUtils;
import ssm.entity.Music;
import ssm.mapper.MusicMapper;
import ssm.service.impl.MusicServiceImpl;

import java.io.File;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author chen
 * @create 2019-12-26 23:26
 */
public class TestJsonDateToMysql implements ApplicationContextAware {

    @Autowired
    private MusicMapper musicMapper ;

    public static void main(String[] args) throws  Exception {
        File jsonfile = ResourceUtils.getFile("classpath:city.json");  //通过Java自带的读取工具对文件目录下的文件进行读取
        String jsonsting  = FileUtils.readFileToString(jsonfile);      //读取到的文件转换成为String类型
        JSONArray array = JSONArray.parseArray(jsonsting);             //根据原本的类型转换为json数组
        //Map<String,Object> maps = new TreeMap<>();                     //采用treemap的原因是想让数据有序，免得数据库里面的数据杂乱无章
        Music music = new Music();
        for (int i=0;i<array.size();i++){                              //遍历循环，根据不同的json文件采用不同的方式
            JSONObject jsons = JSONObject.parseObject(array.get(i).toString());     //遍历取
            //maps.put(jsons.getString("url"),jsons.getString("pic"));             //遍历存
            String title = jsons.getString("title");
            String author = jsons.getString("author");
            String url = jsons.getString("url");
            String pic = jsons.getString("pic");
            String lrc = jsons.getString("lrc");
         /*   music.setTitle(title);
            music.setAuthor(author);
            music.setUrl(url);
            music.setPic(pic);
            music.setLrc(lrc);*/

            //musicService.save(music);
            //musicMapper.save(music);
            System.out.println(i);

        }
        //int counts = commonCityMapper.insertBatch(maps);
        System.out.println(music);
        System.out.println(music.getLrc());

        System.out.println(array.size());
        //System.out.println(list.get(0).get("130200"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }


}
