package com.rtbytez.client.util;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.LocalTimeCounter;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.client.file.Line;
import com.rtbytez.common.util.Console;

import java.net.MalformedURLException;

public class Functions {

    private Functions() {

    }

    public static void replace(String path, Line line) throws MalformedURLException {
        //String[] split = path.split("/");
        //String directory;
        //if (split.length != 1) {
        //    String[] directories = Arrays.copyOf(split, split.length - 1);
        //    directory = String.join("/", directories);
        //} else {
        //    directory = "";
        //}
        //String fileName = split[split.length - 1];

        RTBytezClient client = RTBytezClient.getInstance();
        VirtualFile virtualFile = ProjectRootManager.getInstance(client.getProject()).getContentRoots()[0].findFileByRelativePath(path);
        if (virtualFile != null) {
            PsiFile psiFile = PsiManager.getInstance(client.getProject()).findFile(virtualFile);
            DocumentImpl document = (DocumentImpl) PsiDocumentManager.getInstance(client.getProject()).getDocument(psiFile);
            int lineStartOffset = document.getLineStartOffset(line.getLineNumber() - 1);
            int lineEndOffset = document.getLineEndOffset(line.getLineNumber() - 1);
            WriteCommandAction.runWriteCommandAction(client.getProject(), () -> {
                long l = LocalTimeCounter.currentTime();
                client.getFileModTracker().addCache(virtualFile.getPath(), l);
                document.replaceString(lineStartOffset, lineEndOffset, line.getText(), l, false);
                PsiDocumentManager.getInstance(client.getProject()).commitDocument(document);
            });
        } else {
            Console.log("REPLACER", "Couldn't find the file: " + path);
        }
    }
}
