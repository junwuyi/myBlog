package ssm.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ssm.dto.SensConst;
import ssm.entity.Attachment;
import ssm.enums.AttachLocationEnum;
import ssm.enums.BlogPropertiesEnum;
import ssm.mapper.AttachmentMapper;
import ssm.service.AttachmentService;
import ssm.util.SensUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     附件业务逻辑实现类
 * </pre>
 *
 * @date : 2019/8/10
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private static final String ATTACHMENTS_CACHE_NAME = "attachments";

    @Autowired(required = false)
    private AttachmentMapper attachmentMapper;

    /**
     * 新增附件信息
     *
     * @param attachment attachment
     * @return Attachment
     */
    @Override
    @CacheEvict(value = ATTACHMENTS_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public Attachment saveByAttachment(Attachment attachment) {
        if (attachment != null && attachment.getAttachId() != null) {
            attachmentMapper.updateById(attachment);
        } else {
            attachmentMapper.insert(attachment);
        }
        return attachment;
    }

    /**
     * 获取所有附件信息
     *
     * @return List
     */
    @Override
    @Cacheable(value = ATTACHMENTS_CACHE_NAME, key = "'attachment'")
    public List<Attachment> findAllAttachments() {
        return attachmentMapper.findAll();
    }

    @Override
    public PageInfo<Attachment> findAllAttachments(HashMap<String, Object> criteria, Page<Attachment> page) {
        return null;
    }

    @Override
    @Cacheable(value = ATTACHMENTS_CACHE_NAME, key = "'attachment_id_'+#attachId")
    public Attachment findByAttachId(Long attachId) {
        return attachmentMapper.selectById(attachId);
    }

    /**
     * 根据编号移除附件
     *
     * @param attachId attachId
     * @return Attachment
     */
    @Override
    @CacheEvict(value = ATTACHMENTS_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public Attachment remove(Long attachId) {
        Attachment attachment = this.findByAttachId(attachId);
        if (attachment != null) {
            attachmentMapper.deleteById(attachment.getAttachId());
        }
        return attachment;
    }

    @Override
    public Map<String, String> upload(MultipartFile file, HttpServletRequest request) {
        Map<String, String> resultMap;
        String attachLoc = SensConst.OPTIONS.get(BlogPropertiesEnum.ATTACH_LOC.getProp());
        if (StrUtil.isEmpty(attachLoc)) {
            attachLoc = "server";
        }
        switch (attachLoc) {
           /* case "qiniu":
                resultMap = this.attachQiNiuUpload(file, request);
                break;
            case "upyun":
                resultMap = this.attachUpYunUpload(file, request);
                break;*/
            default:
                resultMap = this.attachUpload(file, request);
                break;
        }
        return resultMap;
    }


    /**
     * 原生服务器上传
     *
     * @param file    file
     * @param request request
     * @return Map
     */
    @Override
    public Map<String, String> attachUpload(MultipartFile file, HttpServletRequest request) {
        final Map<String, String> resultMap = new HashMap<>(6);
        try {
            //用户目录
            final StrBuilder uploadPath = new StrBuilder(System.getProperties().getProperty("user.home"));
            uploadPath.append("/junwuyi/uploads/" + DateUtil.thisYear()).append("/").append(DateUtil.thisMonth() + 1).append("/");
            final File mediaPath = new File(uploadPath.toString());
            if (!mediaPath.exists()) {
                if (!mediaPath.mkdirs()) {
                    resultMap.put("success", "0");
                    return resultMap;
                }
            }
            System.out.println(uploadPath);//C:\Users\chenj/junwuyi/upload/2019/8/
            //不带后缀
            String nameWithOutSuffix = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.')).replaceAll(" ", "_").replaceAll(",", "");

            //文件后缀(没有".")//（类型）jpg
            final String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);

            /**
             * 快捷键桌面.jpg    快捷键桌面_small.jpg
             */
            //文件名带后缀
            String fileName = nameWithOutSuffix + "." + fileSuffix;
            //压缩文件名带后缀
            String fileSmallName = nameWithOutSuffix + "_small." + fileSuffix;
            System.out.println("fileSmallName-" + fileSmallName);

            //判断文件名是否已存在
            File descFile = new File(mediaPath.getAbsoluteFile(), fileName.toString());
            int i = 1;
            while (descFile.exists()) {
                nameWithOutSuffix = nameWithOutSuffix + "(" + i + ")";
                descFile = new File(mediaPath.getAbsoluteFile(), nameWithOutSuffix + "." + fileSuffix);
                i++;
            }
            //将内存中的数据写入磁盘
            file.transferTo(descFile);

            //文件原路径
            final StrBuilder fullPath = new StrBuilder(mediaPath.getAbsolutePath());
            fullPath.append("/");
            fullPath.append(nameWithOutSuffix + "." + fileSuffix);

            //压缩文件路径
            final StrBuilder fullSmallPath = new StrBuilder(mediaPath.getAbsolutePath());
            fullSmallPath.append("/");
            fullSmallPath.append(nameWithOutSuffix);
            fullSmallPath.append("_small.");
            fullSmallPath.append(fileSuffix);

            //压缩图片
            Thumbnails.of(fullPath.toString()).size(256, 256).keepAspectRatio(false).toFile(fullSmallPath.toString());

            //映射路径
            final StrBuilder filePath = new StrBuilder("/uploads/");
            filePath.append(DateUtil.thisYear());
            filePath.append("/");
            filePath.append(DateUtil.thisMonth() + 1);
            filePath.append("/");
            filePath.append(nameWithOutSuffix + "." + fileSuffix);

            //缩略图映射路径 快捷键桌面.jpg   //快捷键桌面_small.jpg
            final StrBuilder fileSmallPath = new StrBuilder("/uploads/");
            fileSmallPath.append(DateUtil.thisYear());
            fileSmallPath.append("/");
            fileSmallPath.append(DateUtil.thisMonth() + 1);
            fileSmallPath.append("/");
            fileSmallPath.append(nameWithOutSuffix);
            fileSmallPath.append("_small.");
            fileSmallPath.append(fileSuffix);

            final String size = SensUtils.parseSize(new File(fullPath.toString()).length());
            final String wh = SensUtils.getImageWh(new File(fullPath.toString()));

            resultMap.put("fileName", fileName.toString());
            //resultMap.put("fileSmallName", fileSmallName.toString());
            resultMap.put("filePath", filePath.toString());
            resultMap.put("smallPath", fileSmallPath.toString());
            resultMap.put("suffix", fileSuffix);
            resultMap.put("size", size);
            resultMap.put("wh", wh);
            resultMap.put("location", AttachLocationEnum.SERVER.getDesc());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 获取附件总数
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return attachmentMapper.getCount();
    }

}
