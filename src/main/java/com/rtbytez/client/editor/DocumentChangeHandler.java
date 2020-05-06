package com.rtbytez.client.editor;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.common.util.Console;
import org.jetbrains.annotations.NotNull;

public class DocumentChangeHandler {

    public void register() {
        RTBytezClient client = RTBytezClient.getInstance();

        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
                Document document = event.getDocument();
                VirtualFile file = FileDocumentManager.getInstance().getFile(document);
                assert file != null;
                if (client.getFileModTracker().exists(file.getPath(), document.getModificationStamp())) {
                    Console.log("Modification was made by REPLACER");
                    return;
                }

                int offset = event.getOffset();
                int newLength = event.getNewLength();

                // actual logic depends on which line we want to call 'changed' when '\n' is inserted
                int firstLine = document.getLineNumber(offset);
                int lastLine = newLength == 0 ? firstLine : document.getLineNumber(offset + newLength - 1);

                //Console.log("First Line: " + firstLine);
                //Console.log("Last line: " + lastLine);

            }


        }, () -> Console.log("Disposed"));
    }
}
