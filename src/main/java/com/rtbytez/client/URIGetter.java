package com.rtbytez.client;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rtbytez.client.dialogs.ConnectDialog;
import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;

public class URIGetter extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        System.out.println("Point A");
        RTBytezClient client = RTBytezClient.getInstance();
        try {
            client.getSocketClient().connect(retrieveURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
