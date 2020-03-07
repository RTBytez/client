package com.rtbytez.client;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rtbytez.client.dialogs.CredentialsDialog;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CredentialsGetter extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }

    public ArrayList<String> retrieveCredentials() {
        CredentialsDialog credentials = new CredentialsDialog();
        credentials.showAndGet();
        return credentials.getCredentials();
    }
}

