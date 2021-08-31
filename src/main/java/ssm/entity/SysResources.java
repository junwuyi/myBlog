package ssm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//import javax.persistence.Transient;

/**
 *  chen
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
@Data
public class SysResources implements Serializable {
    private Long id;
    private String name;
    private String type;
    private String url;
    private String permission;
    // 父节点id
    @JsonProperty
    private Long parentId;
    private Integer sort;
    private Boolean external;
    private Boolean available;
    private String icon;
    private Date createTime;
    private Date updateTime;

    //@Transient
    private String checked;
    //@Transient
    private SysResources parent;
    //@Transient
    private List<SysResources> nodes;
}
