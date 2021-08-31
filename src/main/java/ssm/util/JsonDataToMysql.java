package ssm.util;

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
 * @url 1314258.cn
 */
public class JsonDataToMysql {

    private static final String URI = "jdbc:mysql://127.0.0.1/myblog?"
            + "user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 获取数据库的连接
     *
     * @return
     * @throws Exception
     */
    public static Connection connectDB() throws Exception {
        //1、加载数据库驱动
        Class.forName(DRIVER);
        //2、获取数据库连接
        Connection conn = DriverManager.getConnection(URI);
        return conn;
    }

    public static void main(String[] args) throws Exception {
        //1.利用spring提供的工具类读取资源文件
        File jsonfile = ResourceUtils.getFile("classpath:cjb.json");
        //2.读取到的文件转换成为String类型
        String jsonsting = FileUtils.readFileToString(jsonfile);
        //3.将字符串转成list集合
        List<Music> musics = JSONObject.parseArray(jsonsting, Music.class);
        //获取当前系统的时间戳
        long start = System.currentTimeMillis();
        //获取数据库连接
        Connection conn = JsonDataToMysql.connectDB();
        //开启事务，取消自动提交
        conn.setAutoCommit(false);
        String sql = "INSERT INTO music(name, artist, url, cover,lrc) "
                + " VALUES(?, ?, ?, ?, ?)";
        //预编译sql语句
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //4.遍历插入到数据库
        for (int i = 0; i < musics.size(); i++) {
            pstmt.setString(1, musics.get(i).getName());
            pstmt.setString(2, musics.get(i).getArtist());
            pstmt.setString(3, musics.get(i).getUrl());
            pstmt.setString(4, musics.get(i).getCover());
            pstmt.setString(5, musics.get(i).getLrc());
            //"攒"SQL
            pstmt.addBatch();
            //每100条执行一次
            if ((i + 1) % 100 == 0) {
                //执行sql
                pstmt.executeBatch();
                //事务提交
                conn.commit();
            }
        }

        //最后插入不能整除的
        pstmt.executeBatch();
        conn.commit();
        long end = System.currentTimeMillis();
        System.out.println("花费时间:" + (end - start));
        System.out.println("成功插入" + musics.size() + "条");
        //释放资源
        pstmt.close();
        //再把自动提交打开，避免影响其他需要自动提交的操作
        conn.setAutoCommit(true);
        conn.close();
    }
}