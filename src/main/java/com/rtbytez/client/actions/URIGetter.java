package com.rtbytez.client.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.client.ui.dialogs.ConnectDialog;
import org.jetbrains.annotations.NotNull;

public class URIGetter extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        System.out.println("Point A");
        RTBytezClient client = RTBytezClient.getInstance();
    }

    public String retrieveURI() {
        ConnectDialog dialog = new ConnectDialog();
        boolean ok = dialog.showAndGet(); // Hangs
        if (ok) {
            return dialog.getUriText();
        } else {
            return "";
        }
    }
}
