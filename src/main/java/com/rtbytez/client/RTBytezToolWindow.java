package com.rtbytez.client;

import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class RTBytezToolWindow {

    private JPanel rTBytezToolWindowContent;
    private JButton addConnectionButton;
    private ActionButton actionButton1;
    private JButton toggle;

    public RTBytezToolWindow(ToolWindow toolWindow) {

    }

    public JPanel getContent() {
        return rTBytezToolWindowContent;
    }

    private void createUIComponents() {
        addConnectionButton.addActionListener(event -> {
            // Template for wake
        });
    }

    public void onAddConnectionButtonClick() {
        Connect currConnection = new Connect();
        currConnection.actionPerformed(null);

    }
}

