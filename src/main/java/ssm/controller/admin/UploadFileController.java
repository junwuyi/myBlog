package ssm.controller.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UploadFileController {

    /**
     * 文件保存目录，物理路径
     */
    public final String rootPath = "E:/uploads/";

    //上传文件
    @RequestMapping(value = "/uploadFile",produces = "text/plain;charset=utf-8")
    public String uploadFile(HttpServletRequest request, @Param("file") MultipartFile file) throws IOException {
        //1.文件后缀过滤，只允许部分后缀
        //文件的完整名称,如spring.jpeg
        String filename = file.getOriginalFilename();
        //文件名,如spring
        String name = filename.substring(0, filename.indexOf("."));
        //文件后缀,如.jpeg
        String suffix = filename.substring(filename.lastIndexOf("."));

        //2.创建文件目录
        //创建年月文件夹
        //得到日历类
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR)
                + File.separator + (date.get(Calendar.MONTH) + 1));

        //目标文件
        File descFile = new File(rootPath + File.separator + dateDirs + File.separator + filename);
        int i = 1;
        //若文件存在重命名
        String newFilename = filename;
        while (descFile.exists()) {
            newFilename = name + "(" + i + ")" + suffix;
            //public String getParent()返回此抽象路径名父目录的路径名字符串；如果此路径名没有指定父目录，则返回 null。
            String parentPath = descFile.getParent();
            descFile = new File(parentPath + File.separator + newFilename);
            i++;
        }
        //判断目标文件所在的目录是否存在
        //public File getParentFile()返回此抽象路径名父目录的抽象路径名；如果此路径名没有指定父目录，则返回 null。
        if (!descFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            descFile.getParentFile().mkdirs();
        }

        //3.存储文件
        //将内存中的数据写入磁盘
        try {
            file.transferTo(descFile);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传失败，cause:{}", e);
        }
        //完整的url
        String fileUrl = "/uploads/" + dateDirs + "/" + newFilename;
        System.out.println(fileUrl);
        //返回json数据图片回显
        String[] data = {fileUrl};
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("errno",0);//0表示成功，1失败
        map.put("data",data);
        //map转化为json字符串
        String result = new JSONObject(map).toString();
        System.out.println(result);
        return result;
    }
}