package com.rtbytez.client.editor;


import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.util.messages.MessageBus;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.common.util.Console;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VFSEventHandler {

    public void register() {
        MessageBus messageBus = RTBytezClient.getInstance().getProject().getMessageBus();
        messageBus.connect().subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() {
            @Override
            public void after(@NotNull List<? extends VFileEvent> events) {
                events.forEach(vFileEvent -> Console.log("Caught a " + vFileEvent.toString() + " event"));
            }
        });
    }

}