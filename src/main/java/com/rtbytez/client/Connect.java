package com.rtbytez.client;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rtbytez.client.dialogs.ConnectDialog;
import org.jetbrains.annotations.NotNull;

public class Connect extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {

        ConnectDialog dialog = new ConnectDialog();
        boolean ok = dialog.showAndGet(); // Hangs
        if (ok) {
            System.out.println("dialog.uri.getText() = " + dialog.getUriText());
        }
    }
}
