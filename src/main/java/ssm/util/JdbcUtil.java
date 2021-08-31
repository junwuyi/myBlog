package ssm.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;
import ssm.entity.Music;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author chen
 * @create 2019-12-27 2:09
 */
public class JdbcUtil {

    private static final String URI = "jdbc:mysql://47.100.62.120:3306/myblog?"
            + "user=root&password=Ok.123456&useUnicode=true&characterEncoding=UTF-8";

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    public static Connection connectDB() throws Exception {
        //1、加载数据库驱动
        Class.forName(DRIVER);
        //2、获取数据库连接
        Connection conn = DriverManager.getConnection(URI);

        return conn;
    }

    public static void addMusic(Music music) throws Exception {
        Connection conn = JdbcUtil.connectDB();
        String sql = "INSERT INTO music(name, artist, url, cover,lrc) "
                + " VALUES(?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, music.getName());
        pstmt.setString(2, music.getArtist());
        pstmt.setString(3, music.getUrl());
        pstmt.setString(4, music.getCover());
        pstmt.setString(5, music.getLrc());

        pstmt.execute();
    }

    public static void main(String[] args) throws  Exception {
        File jsonfile = ResourceUtils.getFile("classpath:cjb.json");  //通过Java自带的读取工具对文件目录下的文件进行读取
        String jsonsting  = FileUtils.readFileToString(jsonfile);      //读取到的文件转换成为String类型
        //JSONArray array = JSONArray.parseArray(jsonsting);             //根据原本的类型转换为json数组
        //System.out.println(array);
        //Map<String,Object> maps = new TreeMap<>();                     //采用treemap的原因是想让数据有序，免得数据库里面的数据杂乱无章
       // Music music = new Music();

        //将字符串转成list集合
        List<Music> musics = JSONObject.parseArray(jsonsting, Music.class);//把字符串转换成集合
        for (Music m : musics) {
            addMusic(m);
        }
       /* musics.forEach(music -> addMusic(music));*/
        System.out.println("成功"+musics.size());



     /*   for (int i=0;i<array.size();i++){                              //遍历循环，根据不同的json文件采用不同的方式
            JSONObject jsons = JSONObject.parseObject(array.get(i).toString());     //遍历取
            //maps.put(jsons.getString("url"),jsons.getString("pic"));             //遍历存
            String title = jsons.getString("title");
            String author = jsons.getString("author");
            String url = jsons.getString("url");
            String pic = jsons.getString("pic");
            String lrc = jsons.getString("musicId");
            lrc = "http://music.163.com/api/song/media?id="+lrc;
            Music music = new Music();
            music.setName(title);
            music.setArtist(author);
            music.setUrl(url);
            music.setCover(pic);
            music.setLrc(lrc);

            //addMusic(music);
            //System.out.println(i);

        }*/
        //int counts = commonCityMapper.insertBatch(maps);
        //System.out.println(music);
        //System.out.println(music.getLrc());

        //System.out.println(array.size());
        //System.out.println(list.get(0).get("130200"));
    }

}