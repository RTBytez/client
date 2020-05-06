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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DocumentChangeHandler {

    public void register() {
        RTBytezClient client = RTBytezClient.getInstance();

        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
                String addedOrRemoved;
                List<Integer> changedLines = new ArrayList<>();
                Document document = event.getDocument();
                VirtualFile file = FileDocumentManager.getInstance().getFile(document);
                if (client.getFileModTracker().exists(file.getPath(), document.getModificationStamp())) {
                    Console.log("Modification was made by REPLACER");
                }
                if (event.getNewFragment().toString().equals("\n")) {
                    addedOrRemoved = "a";
                    changedLines.add(document.getLineNumber(event.getOffset()) + 2);
                } else if (event.getNewFragment().length() == 0 && event.getOldFragment().toString().contains("\n")) {
                    addedOrRemoved = "r";
                    int numDeletedLines = Collections.frequency(Arrays.asList(event.getOldFragment().toString().split("")), "\n");
                    for (int i = 0; i < numDeletedLines; i++) {
                        changedLines.add(i + document.getLineNumber(event.getOffset()) + 2);
                    }
                }

                int offset = event.getOffset();
                int newLength = event.getNewLength();

                // actual logic depends on which line we want to call 'changed' when '\n' is inserted
                int firstLine = document.getLineNumber(offset);
                int lastLine = newLength == 0 ? firstLine : document.getLineNumber(offset + newLength - 1);

                //Console.log("First Line: " + firstLine);
                //Console.log("Last line: " + lastLine);

            }


        }, () -> {
            // no clue what this is but it seems to work?
        });
    }
}
