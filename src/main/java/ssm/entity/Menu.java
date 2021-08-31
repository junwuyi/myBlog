package ssm.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 489914127235951698L;
    /**
     * 编号 自增
     */
    private Integer menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单路径
     */
    private String menuUrl;

    /**
     * 菜单层级
     */
    private Integer menuLevel = 1;

    /**
     * 图标，可选，部分主题可显示
     */
    private String menuIcon;

    /**
     * 菜单类型(1前台顶部菜单,2前台主要菜单,3前台底部菜单)
     */
    private Integer menuType;

    /**
     * 打开方式
     */
    private String menuTarget;

    /**
     * 状态
     * 0 不显示 ，1 显示
     */
    private Integer menuStatus;

    /**
     * 排序编号
     */
    private Integer menuOrder;

}