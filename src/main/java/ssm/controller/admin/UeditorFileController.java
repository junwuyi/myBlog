package ssm.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.baidu.ueditor.ActionEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssm.entity.Attachment;
import ssm.enums.ResultCodeEnum;
import ssm.service.AttachmentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("")
public class UeditorFileController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 文件保存目录，物理路径
     * C:/Users/chenj/junwuyi/uploads
     */
    public final String rootPath = "C:/Users/chenj/junwuyi/uploads";

    @RequestMapping(value="/plugins/ueditor/upload/enter",method=RequestMethod.GET)
    public void enterUE(HttpServletRequest request,HttpServletResponse response) {
        try {
            request.setCharacterEncoding( "utf-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Type" , "text/html");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ueditor上传图片的方法
     * @param upfile 上传图片的文件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/uploadimage",method=RequestMethod.POST,produces = "text/plain;charset=utf-8")
    @ResponseBody
    public Map<String, Object> uploadNewsImg(@RequestParam(value="upfile",required=false) MultipartFile upfile, HttpServletRequest request, HttpServletResponse response) {
        //1.文件后缀过滤，只允许部分后缀
        //文件的完整名称,如spring.jpeg
        String filename = upfile.getOriginalFilename();
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
            upfile.transferTo(descFile);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传失败，cause:{}", e);
        }
        //完整的url
        String fileUrl = "/uploads/" + dateDirs + "/" + newFilename;
        //返回json数据图片回显
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("state", "SUCCESS");
        result.put("url", fileUrl);
        result.put("original", "");
        result.put("type", suffix);
        result.put("size", upfile.getSize());
        result.put("title", newFilename);
        return result;
    }


    /**
     * ueditor文件上传方法(图片，视频，等)
     * {
         "state": "SUCCESS",
         "url": "upload/demo.jpg",
         "title": "demo.jpg",
         "original": "demo.jpg"
        }
     * @param upfile
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/ueditor/uploadfile",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam(value="upfile",required=false) MultipartFile upfile,HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>(1);
        Map<String, Object> upload = uploadAttachment(upfile, request);
        result.put("state", "SUCCESS");
        result.put("url", upload.get("url"));
        result.put("title", upload.get("fileName"));
        result.put("size", upfile.getSize());
        result.put("type", upload.get("size"));
        return result;
    }

    public Map<String, Object> uploadAttachment(@RequestParam("file") MultipartFile file,
                                                HttpServletRequest request) {
        final Map<String, Object> result = new HashMap<>(3);
        if (!file.isEmpty()) {
            try {
                final Map<String, String> resultMap = attachmentService.upload(file, request);
                if (resultMap == null || resultMap.isEmpty()) {
                    log.error("File upload failed");
                    result.put("success", ResultCodeEnum.FAIL.getCode());
                    result.put("message", "上传失败");
                    return result;
                }
                //保存在数据库
                Attachment attachment = new Attachment();
                attachment.setAttachName(resultMap.get("fileName"));
                //attachment.setAttachSmallName(resultMap.get("fileSmallName"));
                attachment.setAttachPath(resultMap.get("filePath"));
                attachment.setAttachSmallPath(resultMap.get("smallPath"));
                attachment.setAttachType(file.getContentType());
                attachment.setAttachSuffix(resultMap.get("suffix"));
                attachment.setAttachCreated(DateUtil.date());
                attachment.setAttachSize(resultMap.get("size"));
                attachment.setAttachWh(resultMap.get("wh"));
                attachment.setAttachLocation(resultMap.get("location"));
                attachmentService.saveByAttachment(attachment);
                log.info("Upload file {} to {} successfully", resultMap.get("fileName"), resultMap.get("filePath"));
                result.put("success", ResultCodeEnum.SUCCESS.getCode());
                result.put("message", "上传成功");
                result.put("url", attachment.getAttachPath());
                result.put("filename", resultMap.get("filePath"));
                //logService.saveByLog(LogsRecord.UPLOAD_FILE, resultMap.get("fileName"), request);
            } catch (Exception e) {
                log.error("Upload file failed:{}", e.getMessage());
                result.put("success", ResultCodeEnum.FAIL.getCode());
                result.put("message", "附件上传失败");
            }
        } else {
            log.error("File cannot be empty!");
        }
        return result;
    }
}