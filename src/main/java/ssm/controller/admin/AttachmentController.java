package ssm.controller.admin;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.entity.Attachment;
import ssm.enums.AttachLocationEnum;
import ssm.enums.ResultCodeEnum;
import ssm.service.AttachmentService;
import ssm.util.SensUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 *     后台附件控制器
 * </pre>
 * @date : 2019/8/19
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/attachment")
@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 获取upload的所有图片资源并渲染页面
     *
     * @param model model
     * @return 模板路径admin/admin_attachment
     */
    @RequestMapping
    public String attachments(Model model,
                              @RequestParam(value = "keywords", required = false) String keywords,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "18") Integer size) {
        //查询所有
        List<Attachment> allAttachments = attachmentService.findAllAttachments();
        model.addAttribute("allAttachments", allAttachments);

        return "admin/file/fileList";
    }

    /**
     * 处理获取附件详情的请求
     *
     * @param model    model
     * @param attachId 附件编号
     * @return 模板路径aadmin/file/attachment-detail
     */
    @RequestMapping(value = "/detail")
    public String attachmentDetail(Model model, @RequestParam("attachId") Long attachId) {
        Attachment attachment = attachmentService.findByAttachId(attachId);
        model.addAttribute("attachment", attachment);
        if (attachment != null) {
            model.addAttribute("isPicture", SensUtils.isPicture(attachment.getAttachSuffix()));
        }
        return "admin/file/attachment-detail";
    }

    /**
     * 跳转选择附件页面
     *
     * @param model model
     * @param page  page 当前页码
     * @return 模板路径admin/widget/_attachment-select
     */
    @RequestMapping(value = "/select")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ROOT','ROLE_USER')") // 指定角色权限才能操作方法
    public String selectAttachment(Model model,
                                   @RequestParam(value = "keywords", required = false) String keywords,
                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @RequestParam(value = "id", defaultValue = "none") String id,
                                   @RequestParam(value = "type", defaultValue = "normal") String type) {
        HashMap<String, Object> criteria = new HashMap<>();
        //查询所有
        List<Attachment> allAttachments = attachmentService.findAllAttachments();
        model.addAttribute("allAttachments", allAttachments);
        model.addAttribute("id", id);//选择器
        return "admin/file/attachment-select";
    }

    /**
     * 上传附件
     *
     * @param file    file
     * @param request request
     * @return Map
     */
    @BussinessLog("上传附件")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file,
                                      HttpServletRequest request) {
        return uploadAttachment(file, request);
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


    /**
     * froala 编辑器上传图片，返回格式
     * {"link":"http://i.froala.com/images/missing.png"}
     *
     * @param file    file
     * @param request request
     * @return Map
     */
   // @PostMapping(value = "/upload/froala", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> editorUpload(@RequestParam("file") MultipartFile file,
                                            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(1);
        Map<String, Object> upload = uploadAttachment(file, request);
        result.put("link", upload.get("url"));
        return result;
    }

    /**
     * 移除附件的请求
     *
     * @param attachId 附件编号
     * @param request  request
     * @return JsonResult
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    @BussinessLog("移除附件")
   // @RequiresPermissions("attachment:delete")
    public JsonResult removeAttachment(@RequestParam("attachId") Long attachId,
                                       HttpServletRequest request) {
        Attachment attachment = attachmentService.findByAttachId(attachId);
        String attachLocation = attachment.getAttachLocation();
        String delFileName = attachment.getAttachName();
        //String delSmallFileName = attachment.getAttachSmallName();
        boolean flag = true;
        try {
            //删除数据库中的内容
            attachmentService.remove(attachId);
            if (attachLocation != null) {
                if (attachLocation.equals(AttachLocationEnum.SERVER.getDesc())) {
                    //删除文件C:/Users/chenj/junwuyi/uploads/2019/8/1_small.jpg
                    String userPath = System.getProperties().getProperty("user.home") + "/junwuyi";
                    File mediaPath = new File(userPath, attachment.getAttachPath().substring(0));
                    File mediaPath2 = new File(userPath, attachment.getAttachSmallPath().substring(0));
                    File delFile = new File(new StringBuffer(mediaPath.getAbsolutePath()).toString());
                    File delSmallFile = new File(new StringBuffer(mediaPath2.getAbsolutePath()).toString());
                    System.out.println(delFile);
                    System.out.println(delSmallFile);
                    if (delFile.exists() && delFile.isFile()) {
                        System.gc();
                        flag = delFile.delete();
                    }
                    if (delSmallFile.exists() && delSmallFile.isFile()) {
                        System.gc();
                        flag = delSmallFile.delete();
                    }
                }
            }
            if (flag) {
                log.info("Delete file {} successfully!", delFileName);
                //logService.saveByLog(LogsRecord.REMOVE_FILE, delFileName, request);
            } else {
                log.error("Deleting attachment {} failed!", delFileName);
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "附件删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Deleting attachment {} failed: {}", delFileName, e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "附件删除失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "附件删除成功");
    }

}
