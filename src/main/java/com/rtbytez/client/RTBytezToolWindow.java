package com.rtbytez.client;

import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class RTBytezToolWindow {

    private JPanel rTBytezToolWindowContent;
    private JButton addConnectionButton;
    private ActionButton actionButton1;
    private JButton toggle;
    static boolean foo = false;

    public RTBytezToolWindow(ToolWindow toolWindow) {

    }

    public JPanel getContent() {
        addConnectionButton.addActionListener(event -> {
            onAddConnectionButtonClick();
        });
        return rTBytezToolWindowContent;
    }



    public void onAddConnectionButtonClick() {
        URIGetter currConnection = new URIGetter();
        System.out.println(currConnection.retrieveURI());
    }
}

