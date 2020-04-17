package com.rtbytez.client;

import javax.swing.*;

public class CustomTreeNode {
    private Icon icon;
    private String name;

    public CustomTreeNode(String name, Icon icon) {
        this.icon = icon;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Icon getIcon() {
        return icon;
    }
}
