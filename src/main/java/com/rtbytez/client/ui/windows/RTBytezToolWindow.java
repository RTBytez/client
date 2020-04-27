package com.rtbytez.client.ui.windows;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.util.ui.components.BorderLayoutPanel;
import com.rtbytez.client.actions.CredentialsGetter;
import com.rtbytez.client.actions.URIGetter;
import com.rtbytez.client.ui.util.TreeController;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;

public class RTBytezToolWindow {

    public JTree rTBytezTree;
    public boolean isConnected = false;
    public boolean isServerManager = true;
    public boolean isRoomOperator = true;
    private JPanel rTBytezToolWindowContent;
    private BorderLayoutPanel borderLayoutPanel;
    private final TreeController treeController = new TreeController(rTBytezTree);

    public RTBytezToolWindow(ToolWindow toolWindow) {
    }

    public JPanel getContent() {
        rTBytezToolWindowContent.setBorder(BorderFactory.createEmptyBorder());
        treeController.setupTree();
        return rTBytezToolWindowContent;
    }

    private void createUIComponents() {
        JButton button = new JButton();
        JLabel buttonLabel = new JLabel("BUtton");
        button.add(buttonLabel);
        AnAction connectButton = new AnAction("Connect", "Connect to RTBytez Server", AllIcons.Actions.Execute) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                URIGetter uriGetter = new URIGetter();
                CredentialsGetter credentialsGetter = new CredentialsGetter();
                String uri = uriGetter.retrieveURI();
                ArrayList<String> credentials = credentialsGetter.retrieveCredentials();
                isConnected = true;
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                e.getPresentation().setVisible(!isConnected);
            }
        };
        AnAction disconnectButton = new AnAction("Disconnect", "Disconnect from RTBytez Server", AllIcons.Actions.Exit) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                isConnected = false;
                isRoomOperator = false;
                isServerManager = false;
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                e.getPresentation().setVisible(isConnected);
            }
        };
        AnAction refreshButton = new AnAction("Refresh", "Refresh", AllIcons.Actions.Refresh) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                System.out.println("actionPerformed called");
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                e.getPresentation().setVisible(isConnected);
            }
        };
        AnAction filesButton = new AnAction("Files", "Files", AllIcons.Nodes.CopyOfFolder) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                e.getPresentation().setEnabled(isRoomOperator);
            }
        };
        AnAction membersButton = new AnAction("Members", "Members", AllIcons.General.User) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                e.getPresentation().setEnabled(isRoomOperator);
            }
        };
        AnAction conflictsButton = new AnAction("Conflicts", "Conflicts", AllIcons.Actions.Cancel) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                e.getPresentation().setEnabled(isRoomOperator);
            }
        };
        AnAction commitButton = new AnAction("Commit", "Commit", AllIcons.Actions.Commit) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                e.getPresentation().setEnabled(isRoomOperator);
            }
        };
        AnAction pushButton = new AnAction("Push", "Push", AllIcons.Vcs.Push) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                e.getPresentation().setEnabled(isRoomOperator);
            }
        };
        AnAction createRoomButton = new AnAction("Create Room", "Create room", AllIcons.General.Add) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                e.getPresentation().setVisible(isConnected);
            }
        };
        AnAction serverManagerButton = new AnAction("Server Manager", "Server manager", AllIcons.Actions.Lightning) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                e.getPresentation().setVisible(isServerManager);
            }
        };

        Separator separator = new Separator();
        ActionGroup finalGroup = new DefaultActionGroup(
                connectButton, disconnectButton, refreshButton, separator, createRoomButton,
                separator, membersButton, filesButton, commitButton, pushButton, conflictsButton,
                separator, serverManagerButton
        );
        ActionManager actionManager = ActionManager.getInstance();
        ActionToolbar actionToolbar = actionManager.createActionToolbar("RTBytez Top", finalGroup, true);
        borderLayoutPanel = new BorderLayoutPanel();
        borderLayoutPanel.addToTop(actionToolbar.getComponent());
    }

    private void createTree() {
        rTBytezTree.getModel();
    }
}
