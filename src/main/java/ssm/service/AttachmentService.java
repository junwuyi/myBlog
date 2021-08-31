package ssm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import ssm.entity.Attachment;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     附件业务逻辑接口
 * </pre>
 * @date : 2019/8/10
 */
public interface AttachmentService {

    /**
     * 添加附件信息
     *
     * @param attachment attachment
     * @return Attachment
     */
    Attachment saveByAttachment(Attachment attachment);

    /**
     * 查询所有附件信息不分页
     *
     * @return List
     */
    List<Attachment> findAllAttachments();

    /**
     * 查询所有附件，分页
     *
     * @param criteria 查询条件
     * @param page 分页信息
     * @return Page
     */
    PageInfo<Attachment> findAllAttachments(HashMap<String, Object> criteria, Page<Attachment> page);

    /**
     * 根据编号查询
     *
     * @param attachId attachId
     * @return Attachment
     */
    Attachment findByAttachId(Long attachId);

    /**
     * 根据编号移除
     *
     * @param attachId attachId
     * @return Attachment
     */
    Attachment remove(Long attachId);

    /**
     * 上传转发
     *
     * @param file    file
     * @param request request
     * @return Map
     */
    Map<String, String> upload(MultipartFile file, HttpServletRequest request);

    /**
     * 原生服务器上传
     *
     * @param file    file
     * @param request request
     * @return Map
     */
    Map<String, String> attachUpload(MultipartFile file, HttpServletRequest request);

    /**
     * 获取附件总数
     *
     * @return Long
     */
    Integer getCount();
}
