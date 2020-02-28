package com.rtbytez.client;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rtbytez.client.dialogs.ConnectDialog;

public class URIGetter extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        retrieveURI();

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
