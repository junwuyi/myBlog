package ssm.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * @author chen
 * @create 2019-12-27 2:09
 * @url 1314258.cn
 */
@Data
public class Music implements Serializable {

    private static final long serialVersionUID = -38873741878079746L;
    /**
     *
     */
    private Integer id;

    /**
     *标题
     */
    private String name;

    /**
     *作者
     */
    private String artist;

    /**
     *歌曲路径
     */
    private String url;

    /**
     *封面图
     */
    private String cover;

    /**
     *歌词
     */
    private String lrc;

}

