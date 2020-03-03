package com.rtbytez.client;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.util.ui.components.BorderLayoutPanel;
import com.rtbytez.client.dialogs.ConnectDialog;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class RTBytezToolWindow {

    private JPanel rTBytezToolWindowContent;
    private BorderLayoutPanel borderLayoutPanel;

    public RTBytezToolWindow(ToolWindow toolWindow) {

    }

    public JPanel getContent() {
        return rTBytezToolWindowContent;
    }

    private void createUIComponents() {
        Dimension size = new Dimension(198, 28);
        AnAction connectButton = new AnAction("Connect", "Connect to RTBytez Server", AllIcons.Actions.Execute) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                ConnectDialog connection = new ConnectDialog();
                connection.getUriText();
            }
        };
        AnAction disconnectButton = new AnAction("Disconnect", "Disconnect from RTBytez Server", AllIcons.Actions.Exit) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction refreshButton = new AnAction("Refresh", "Refresh", AllIcons.Actions.Refresh) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction filesButton = new AnAction("Files", "Files", AllIcons.Actions.NewFolder) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction membersButton = new AnAction("Members", "Members", AllIcons.Actions.Profile) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction conflictsButton = new AnAction("Conflicts", "Conflicts", AllIcons.Actions.Cancel) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction commitButton = new AnAction("Commit", "Commit", AllIcons.Actions.Menu_saveall) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction pushButton = new AnAction("Push", "Push", AllIcons.Actions.Upload) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction createRoomButton = new AnAction("Create Room", "Create Room", AllIcons.Actions.New) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction serverManagerButton = new AnAction("Server Manager", "Server Manager", AllIcons.Actions.Lightning) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };

        Separator separator = new Separator();

        ActionGroup group = new DefaultActionGroup(
                connectButton, refreshButton, separator, createRoomButton, serverManagerButton, filesButton, membersButton,
                conflictsButton, commitButton, pushButton
        );

        ActionManager actionManager = ActionManager.getInstance();
        ActionToolbar actionToolbar = actionManager.createActionToolbar(ActionPlaces.UNKNOWN, group, true);
        borderLayoutPanel = new BorderLayoutPanel();
        borderLayoutPanel.addToTop(actionToolbar.getComponent());
    }
}

