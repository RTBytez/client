package com.rtbytez.client;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.util.ui.components.BorderLayoutPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;

public class RTBytezToolWindow {

    private JPanel rTBytezToolWindowContent;
    private BorderLayoutPanel borderLayoutPanel;
    private JList list1;

    public RTBytezToolWindow(ToolWindow toolWindow) {

    }

    public JPanel getContent() {
        rTBytezToolWindowContent.setBorder(BorderFactory.createEmptyBorder());
        return rTBytezToolWindowContent;
    }

    private void createUIComponents() {
        AnAction connectButton = new AnAction("Connect", "Connect to RTBytez Server", AllIcons.Actions.Execute) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                URIGetter uriGetter = new URIGetter();
                CredentialsGetter credentialsGetter = new CredentialsGetter();
                String uri = uriGetter.retrieveURI();
                ArrayList<String> credentials = credentialsGetter.retrieveCredentials();
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
        AnAction filesButton = new AnAction("Files", "Files", AllIcons.Nodes.CopyOfFolder) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction membersButton = new AnAction("Members", "Members", AllIcons.General.User) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction conflictsButton = new AnAction("Conflicts", "Conflicts", AllIcons.Actions.Cancel) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction commitButton = new AnAction("Commit", "Commit", AllIcons.Actions.Commit) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction pushButton = new AnAction("Push", "Push", AllIcons.Vcs.Push) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction createRoomButton = new AnAction("Create Room", "Create room", AllIcons.Actions.New) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        AnAction serverManagerButton = new AnAction("Server Manager", "Server manager", AllIcons.Actions.Lightning) {
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
        ActionToolbar actionToolbar = actionManager.createActionToolbar("RTBytez Top", group, true);
        borderLayoutPanel = new BorderLayoutPanel();
        borderLayoutPanel.addToTop(actionToolbar.getComponent());
    }
}

