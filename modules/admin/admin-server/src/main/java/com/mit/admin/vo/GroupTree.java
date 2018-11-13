package com.mit.admin.vo;

import com.mit.common.vo.TreeNode;

/**
 *
 * @author shuyy
 * @date 2018/11/8 11:23
 * @company mitesofor
*/
public class GroupTree extends TreeNode {
    String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
