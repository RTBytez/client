package com.rtbytez.client;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RTBytezToolWindow {

    private JPanel rTBytezToolWindowContent;
    private JPanel actionToolbarPanel;

    public RTBytezToolWindow(ToolWindow toolWindow) {

    }

    public JPanel getContent() {
        return rTBytezToolWindowContent;
    }

    private void createUIComponents() {
        ActionGroup group = new DefaultActionGroup(
                new AnAction("Connect", "Connect to RTBytez Server", AllIcons.Actions.Execute) {
                    @Override
                    public void actionPerformed(@NotNull AnActionEvent e) {

                    }
                }
        );
        ActionManager actionManager = ActionManager.getInstance();
        ActionToolbar actionToolbar = actionManager.createActionToolbar(ActionPlaces.UNKNOWN, group, false);
        actionToolbarPanel = new JPanel();
        actionToolbarPanel.add(actionToolbar.getComponent());
    }
}

