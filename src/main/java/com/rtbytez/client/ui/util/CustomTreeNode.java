package com.rtbytez.client.ui.util;

import javax.swing.*;

public class CustomTreeNode {
    private final Icon icon;
    private final String name;

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
