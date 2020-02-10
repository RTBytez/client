package com.rtbytez.client;

import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class RTBytezToolWindow {

    private JPanel rTBytezToolWindowContent;
    private JButton button1;
    private ActionButton actionButton1;
    private JButton toggle;

    public RTBytezToolWindow(ToolWindow toolWindow) {

    }

    public JPanel getContent() {
        return rTBytezToolWindowContent;
    }

    private void createUIComponents() {
        actionButton1.addNotify();
    }
}
