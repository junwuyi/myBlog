package test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;

import java.io.File;

/**
 * @author chen
 * @create 2019-08-13 16:30
 *
 * 【1】获取系统属性两种方法
    Java中取得系统的属性的方法有两种：

    ① 可以使用System类中的方法：public static Properties getProperties()得到系统的各种属性，
    该方法返回一个Properties类，这个类继承自Hashtable，在该类中定义了各种属性的键值对。

    ② 直接使用System.getProperty(String key)获取所需的系统属性。
    实际上两者是等价的，System.getProperty(String key)方法内部调用了System类内部声明的Properties对象的getProperty(String key)方法。
 */
public class SystemDemo {

    public static void main(String[] args) {
       /* final StrBuilder uploadPath = new StrBuilder(System.getProperties().getProperty("E:/"));
        uploadPath.append("/junwuyi/upload/" + DateUtil.thisYear()).append("/").append(DateUtil.thisMonth() + 1).append("/");
        System.out.println(uploadPath);

        System.out.println("当前项目所在目录: " + System.getProperty("user.dir"));//D:\idea_workspace\ssm\ssmBlog
        System.out.println("用户的账户名称: " + System.getProperty("user.name"));//chenj
        System.out.println("用户的主目录: " + System.getProperty("user.home"));//C:\Users\chenj*/

        //删除文件
        String userPath = System.getProperties().getProperty("user.home") + "/junwuyi";
        File mediaPath = new File(userPath, "/uploads/2019/8/快捷键桌面.jpg".substring(0, "/uploads/2019/8/快捷键桌面.jpg".lastIndexOf('/')));
        //File delFile = new File(new StringBuffer(mediaPath.getAbsolutePath()).append("/").append("快捷键桌面.jpg").toString());
        File delSmallFile = new File(new StringBuffer(mediaPath.getAbsolutePath()).append("/").append("1_small.jpg").toString());
        System.out.println("userPath-"+userPath);
        System.out.println("mediaPath-"+mediaPath);
        System.out.println("delFile-"+delSmallFile);
        if (delSmallFile.exists() && delSmallFile.isFile()) {
            boolean flag = delSmallFile.delete();
            System.out.println("删除" + flag);
        }

        String s = "spring.jpeg".substring(0, "spring.jpeg".lastIndexOf('.')).replaceAll(" ", "_").replaceAll(",", "");
        System.out.println(s+"spring.jpeg".substring("spring.jpeg".lastIndexOf(".")+1));

        /* <div class="tg-pc tg-site">
                            <img src="https://www.mom1.cn/wp-content/uploads/2019/12/20191213051629mom1-cn74.png" alt="12-13国家公祭日，勿忘国耻"><br><a href="https://www.mom1.cn/3480.html"><p style="background-color: #2ed573;text-align: center;">心灵毒鸡汤: <script src="/m1/api/"></script>用心品味当下每一分痛苦，这样在离世时才不会有所留恋。</p></a></div>*/
    }
}
