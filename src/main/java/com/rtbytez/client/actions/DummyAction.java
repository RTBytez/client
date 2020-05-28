package com.rtbytez.client.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rtbytez.client.RTBytezClient;
import org.jetbrains.annotations.NotNull;

public class DummyAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        RTBytezClient.dummy();
    }
}
