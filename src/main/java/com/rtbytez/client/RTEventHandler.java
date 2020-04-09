package com.rtbytez.client;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;

public class RTEventHandler {

    public RTEventHandler() {
        init();
    }

    public void init() {
        Project project = RTBytezClient.getInstance().getProject();
        MessageBus messageBus = project.getMessageBus();

        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerListener() {
            @Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                Console.log("FileOpenedEvent");
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                Console.log("FileClosedEvent");
            }

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                Console.log("SelectionChangedEvent");
            }
        });
    }
}
