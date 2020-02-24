package com.rpy.system.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/24
 */

@Data
public class MenuTreeNode {
    private Integer id;
    private Integer pid;

    private String title;
    private String href;
    private String icon;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tyepcode;

    private Boolean spread;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String target;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuTreeNode> child=new ArrayList<>();


    /**
     * 构造主页左边的数
     * @param id
     * @param pid
     * @param title
     * @param href
     * @param icon
     * @param spread
     * @param target

     */
    public MenuTreeNode(Integer id, Integer pid, String title, String href, String icon, Boolean spread, String target,String tyepcode) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.href = href;
        this.icon = icon;
        this.spread = spread;
        this.target = target;
        this.tyepcode=tyepcode;
    }

    public static class MenuTreeNodeBuilder{
        public static List<MenuTreeNode> build(List<MenuTreeNode> data,Integer topid){
            List<MenuTreeNode> treeNodes=new ArrayList<>();
            for (MenuTreeNode d : data) {
                if(d.getPid().equals(topid)){
                    treeNodes.add(d);
                }
                for (MenuTreeNode d2 : data) {
                    if(d2.getPid().equals(d.getId())){
                        d.getChild().add(d2);
                    }
                }
            }

            return treeNodes;
        }
    }
}
