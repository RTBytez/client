package com.rtbytez.client.editor;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.common.comms.packets.file.request.RTPFileRequestAddLine;
import com.rtbytez.common.comms.packets.file.request.RTPFileRequestModifyLine;
import com.rtbytez.common.comms.packets.file.request.RTPFileRequestRemoveLine;
import com.rtbytez.common.util.Console;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.rtbytez.client.util.Functions.toRelPath;

public class DocumentChangeHandler {

    public void register() {
        RTBytezClient client = RTBytezClient.getInstance();

        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
                if (client.isOperational()) {
                    String addedOrRemoved = "";
                    List<Integer> changedLines = new ArrayList<>();
                    Document document = event.getDocument();
                    VirtualFile file = FileDocumentManager.getInstance().getFile(document);
                    if (file instanceof LightVirtualFile) {
                        return; // Ignore these files as they don't come from a true source
                    }
                    String relPath = toRelPath(file.getPath());
                    assert file != null;
                    if (client.getFileModTracker().exists(relPath, document.getModificationStamp())) {
                        Console.log("Modification was made by REPLACER");
                        return;
                    }
                    if (event.getNewFragment().toString().contains("\n")) {
                        addedOrRemoved = "a";
                        changedLines.add(document.getLineNumber(event.getOffset()) + 2);
                    } else if (event.getNewFragment().length() == 0 && event.getOldFragment().toString().contains("\n")) {
                        addedOrRemoved = "r";
                        int numDeletedLines = Collections.frequency(Arrays.asList(event.getOldFragment().toString().split("")), "\n");
                        for (int i = 0; i < numDeletedLines; i++) {
                            changedLines.add(i + document.getLineNumber(event.getOffset()) + 2);
                        }
                    }
                    if (addedOrRemoved == "a") {
                        client.getPeer().emit(new RTPFileRequestAddLine("file", relPath, changedLines.get(0)));
                    } else if (addedOrRemoved == "r") {
                        for (int i = changedLines.size() - 1; i >= 0; i--) {
                            Integer changedLine = changedLines.get(i);
                            client.getPeer().emit(new RTPFileRequestRemoveLine("file", relPath, client.getLineMapper().lineIdOf(relPath, changedLine)));
                            client.getLineMapper().removeLine(relPath, changedLine);
                        }
                    } else {
                        int offset = event.getOffset();
                        int newLength = event.getNewLength();

                        int firstLine = document.getLineNumber(offset);
                        int lastLine = newLength == 0 ? firstLine : document.getLineNumber(offset + newLength - 1);

                        for (int i = firstLine; i <= lastLine; i++) {
                            client.getPeer().emit(new RTPFileRequestModifyLine("file", relPath, client.getLineMapper().lineIdOf(relPath, i), document.getText(new TextRange(document.getLineStartOffset(i), document.getLineEndOffset(i)))));
                        }
                    }
                }
            }
        }, () -> Console.log("Disposed"));
    }
}
