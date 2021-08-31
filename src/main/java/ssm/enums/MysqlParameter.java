package ssm.enums;

/**
 * @author chen
 * @create 2019-08-30 19:05
 * //@param hostIP MySQL数据库所在服务器地址IP
 * //@param userName 进入数据库所需要的用户名
 * //@param password 进入数据库所需要的密码
 * //@param savePath 数据库导出文件保存路径
 * //@param fileName 数据库导出文件文件名
 * // @param databaseName 要导出的数据库名
 */
public enum MysqlParameter {
    HOSTIP("127.0.0.1"),
    USERNAEM("root"),
    PASSWORD("123456"),
    DATABASE("forest_blog"),
    FILENAME("myBlog.sql");

    private String prop;
    // 构造方法：enum的构造方法只能被声明为private权限或不声明权限
    MysqlParameter(String prop) {
        this.prop = prop;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }
}
