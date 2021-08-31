package ssm.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import ssm.enums.CommonParamsEnum;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     常用工具
 * </pre>
 *
 * @date : 2017/12/22
 */
@Slf4j
public class SensUtils {

    /**
     * 转换文件大小
     *
     * @param size size
     * @return String
     */
    public static String parseSize(long size) {
        if (size < CommonParamsEnum.NOT_FOUND.getValue()) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        if (size < CommonParamsEnum.NOT_FOUND.getValue()) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < CommonParamsEnum.NOT_FOUND.getValue()) {
            size = size * 100;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
        } else {
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
        }
    }

    /**
     * 获取文件创建时间
     *
     * @param srcPath 文件绝对路径
     * @return 时间
     */
    public static Date getCreateTime(String srcPath) {
        Path path = Paths.get(srcPath);
        BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes attr;
        try {
            attr = basicview.readAttributes();
            Date createDate = new Date(attr.creationTime().toMillis());
            return createDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.set(1970, 0, 1, 0, 0, 0);
        return cal.getTime();
    }

    /**
     * 获取文件长和宽
     *
     * @param file file
     * @return String
     */
    public static String getImageWh(File file) {
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(file));
            return image.getWidth() + "x" + image.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取主题下的模板文件名
     *
     * @param theme theme
     * @return List
     */
    public static List<String> getTplName(String theme) {
        List<String> tpls = new ArrayList<>();
        try {
            //获取项目根路径
            File basePath = new File(ResourceUtils.getURL("classpath:").getPath());
            //获取主题路径
            File themesPath = new File(basePath.getAbsolutePath(), "templates/themes/" + theme);
            File modulePath = new File(themesPath.getAbsolutePath(), "module");
            File[] baseFiles = themesPath.listFiles();
            File[] moduleFiles = modulePath.listFiles();
            if (null != moduleFiles) {
                for (File file : moduleFiles) {
                    if (file.isFile() && file.getName().endsWith(".ftl")) {
                        tpls.add("module/" + file.getName());
                    }
                }
            }
            if (null != baseFiles) {
                for (File file : baseFiles) {
                    if (file.isFile() && file.getName().endsWith(".ftl")) {
                        tpls.add(file.getName());
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取主题模板失败：{}", e.getMessage());
        }
        return tpls;
    }

    /**
     * 获取当前时间
     *
     * @return 字符串
     */
    public static String getStringDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(new Date());
        return dateString;
    }

    public static String getStringDate(Date date, String format) {
        Long unixTime = Long.parseLong(String.valueOf(date.getTime() / 1000));
        return Instant.ofEpochSecond(unixTime).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 导出为文件
     *
     * @param data     内容
     * @param filePath 保存路径
     * @param fileName 文件名
     */
    public static void postToFile(String data, String filePath, String fileName) throws IOException {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile() + "/" + fileName, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != bufferedWriter) {
                bufferedWriter.close();
            }
            if (null != fileWriter) {
                fileWriter.close();
            }
        }
    }



    /**
     * 访问路径获取json数据
     *
     * @param enterUrl 路径
     * @return String
     */
    public static String getHttpResponse(String enterUrl) {
        BufferedReader in = null;
        StringBuffer result = null;
        try {
            URI uri = new URI(enterUrl);
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            connection.connect();
            result = new StringBuffer();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 百度主动推送
     *
     * @param blogUrl 博客地址
     * @param token   百度推送token
     * @param urls    文章路径
     * @return String
     */
    public static String baiduPost(String blogUrl, String token, String urls) {
        String url = "http://data.zz.baidu.com/urls?site=" + blogUrl + "&token=" + token;
        String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            //建立URL之间的连接
            URLConnection conn = new URL(url).openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("Host", "data.zz.baidu.com");
            conn.setRequestProperty("User-Agent", "curl/7.12.1");
            conn.setRequestProperty("Content-Length", "83");
            conn.setRequestProperty("Content-Type", "text/plain");

            //发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //获取conn对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            out.print(urls.trim());
            //进行输出流的缓冲
            out.flush();
            //通过BufferedReader输入流来读取Url的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 判断某个后缀的文是否是图片类型
     *
     * @param suffix 后缀
     * @return 是否
     */
    public static Boolean isPicture(String suffix) {
        String allowSuffix = ".bmp .jpg .jpeg .png .gif";
        /*
        * public int indexOf(String str)返回指定子字符串在此字符串中第一次出现处的索引。没有返回-1
        * */
        if (suffix != null) {
            if (allowSuffix.indexOf(suffix) != -1) {
                return true;
            }
        }
        return false;
    }


    /**
     * 根据后缀返回对应的媒体文件图片
     * 针对非图片的媒体文件(.doc .zip等)
     *
     * @param suffix 后缀
     * @return 是否
     */
    public static String getMediaPicture(String suffix) {
        //文本文件后缀【document】：.txt .doc. .docx .wps .pdf .html
        //音乐文件后缀【audio】：.mp3 .wma .wmv
        //视频文件后缀【video】：.mp4 .avi .rm .rmvb .flv .mpg .mov .mkv
        //压缩文件后缀【archive】：.zip .rar .gz
        //代码文件后缀【code】：.sql .java .c .py .html .css .js .cpp .php .jsp .asp .exe .h .net .lib
        //表格文件后缀【spreadsheet】：.xls .xlsx
        //PPT文件后缀【interactive】：.ppt .pptx
        String documentSuffix = ".txt .doc. .md .docx .wps .pdf";
        String audioSuffix = ".mp3 .wma .wmv";
        String videoSuffix = ".mp4 .avi .rm .rmvb .flv .mpg .mov .mkv";
        String archiveSuffix = ".zip .rar .gz";
        String spreadsheetSuffix = ".xls .xlsx";
        String interactiveSuffix = ".ppt .pptx";
        String codeSuffix = ".sql .java .c .py .html .css .js .cpp .php .jsp .asp .exe .h .net .lib";

        String urlPrefix = "/static/images/media/";
        if(suffix == null || suffix == "") {
            return urlPrefix + "default.png";
        }
        if (documentSuffix.indexOf(suffix) != -1) {
            return urlPrefix + "document.png";
        } else if (audioSuffix.indexOf(suffix) != -1) {
            return urlPrefix + "audio.png";
        } else if (videoSuffix.indexOf(suffix) != -1) {
            return urlPrefix + "video.png";
        } else if (archiveSuffix.indexOf(suffix) != -1) {
            return urlPrefix + "archive.png";
        } else if (spreadsheetSuffix.indexOf(suffix) != -1) {
            return urlPrefix + "spreadsheet.png";
        } else if (interactiveSuffix.indexOf(suffix) != -1) {
            return urlPrefix + "interactive.png";
        } else if (codeSuffix.indexOf(suffix) != -1) {
            return urlPrefix + "code.png";
        } else {
            return urlPrefix + "default.png";
        }
    }

}
