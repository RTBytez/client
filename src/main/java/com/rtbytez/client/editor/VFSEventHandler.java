package com.rtbytez.client.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileCreateEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileDeleteEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.util.messages.MessageBus;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.common.comms.packets.file.request.RTPFileRequestAddLine;
import com.rtbytez.common.comms.packets.file.request.RTPFileRequestCreate;
import com.rtbytez.common.comms.packets.file.request.RTPFileRequestDelete;
import com.rtbytez.common.util.Console;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.rtbytez.client.util.Functions.toRelPath;

public class VFSEventHandler {

    public void register() {
        MessageBus messageBus = RTBytezClient.getInstance().getProject().getMessageBus();
        Project project = RTBytezClient.getInstance().getProject();
        RTBytezClient client = RTBytezClient.getInstance();

        messageBus.connect().subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() {
            @Override
            public void after(@NotNull List<? extends VFileEvent> events) {
                events.forEach(vFileEvent -> {
                    Console.log("Caught a " + vFileEvent.toString() + " event");
                    if (client.isOperational()) {
                        if (vFileEvent instanceof VFileCreateEvent) {
                            VFileCreateEvent vFileCreateEvent = (VFileCreateEvent) vFileEvent;
                            String path = toRelPath(vFileCreateEvent.getPath());
                            client.getPeer().emit(new RTPFileRequestCreate("file", path));
                            client.getPeer().emit(new RTPFileRequestAddLine("file", path, 1));
                        } else if (vFileEvent instanceof VFileDeleteEvent) {
                            VFileDeleteEvent vFileDeleteEvent = (VFileDeleteEvent) vFileEvent;
                            String path = toRelPath(vFileDeleteEvent.getPath());
                            client.getPeer().emit(new RTPFileRequestDelete("file", path));
                        }
                    }
                    //assert vFileEvent.getFile() != null;
                    //PsiFile psiFile = PsiManager.getInstance(project).findFile(vFileEvent.getFile());
                    //assert psiFile != null;
                    //Document document = PsiDocumentManager.getInstance(project).getDocument(psiFile);
                    //assert document != null;
                    //System.out.println(document.getText().replaceAll("\n", "\\\\n"));
                });
            }
        });
    }
}