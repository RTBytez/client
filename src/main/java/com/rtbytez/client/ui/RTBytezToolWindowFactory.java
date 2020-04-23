package com.rtbytez.client.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.client.ui.windows.RTBytezToolWindow;
import org.jetbrains.annotations.NotNull;

public class RTBytezToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        RTBytezClient.getInstance();
        RTBytezToolWindow myToolWindow = new RTBytezToolWindow(toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(myToolWindow.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
