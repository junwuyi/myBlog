/*
package test.mysql.backup;

import com.fasterxml.jackson.core.JsonParser;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

*/
/**
 * @author chen
 * @create 2019-12-26 23:26
 *//*

public class TestJsonDateToMysql {

    private static final String url = "jdbc:mysql://localhost:3306/sys?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "zsy0702";
    private static Connection con;
    static Connection getconnect()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return con;
    }
    public static void moreinsertdata(Connection con)//插入数据进入数据库中
    {
        JsonParser parser = new JsonParser() ;
        JsonObject object;
        try
        {
            object = (JsonObject) parser.parse(new FileReader("D:\\Java\\testdate.json"));
            JsonArray array = object.get("rows").getAsJsonArray();
            for(int i=0;i<array.size();i++)
            {
                JsonObject arrayObject = array.get(i).getAsJsonObject();
                PreparedStatement psql = con.prepareStatement("insert into b_sim (number,name,floor,position,starttime)" + "values(?,?,?,?,?)");

                psql.setInt(1, arrayObject.get("number").getAsInt());
                psql.setString(2, arrayObject.get("name").getAsString().toString());
                psql.setString(3, arrayObject.get("floor").getAsString().toString());
                psql.setString(4, arrayObject.get("position").getAsString().toString());
                //下面是关于json中出现时间格式的数值插入到mysql的相关操作
                String date = arrayObject.get("starttime").getAsString().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date myDate = dateFormat.parse(date);
                Format format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String str=format.format(myDate);
                psql.setString(5, str);
                psql.executeUpdate();
                psql.close();
            }
        } catch (JsonIOException e1) {
            e1.printStackTrace();
        } catch (JsonSyntaxException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
        Connection con = TestJsonDateToMysql.getconnect();
        moreinsertdata(con);
    }
}
*/
