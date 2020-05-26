package com.rtbytez.client.util;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.util.LocalTimeCounter;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.common.util.Console;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class Functions {

    private Functions() {

    }

    public static VirtualFile getVirtualFile(String path) {
        RTBytezClient client = RTBytezClient.getInstance();
        return ProjectRootManager.getInstance(client.getProject()).getContentRoots()[0].findFileByRelativePath(path);
    }

    public static PsiFile getPsiFile(VirtualFile virtualFile) {
        return ApplicationManager.getApplication().runReadAction((Computable<PsiFile>) () -> PsiManager.getInstance(RTBytezClient.getInstance().getProject()).findFile(virtualFile));
    }

    public static DocumentImpl getDocument(String path) {
        RTBytezClient client = RTBytezClient.getInstance();
        VirtualFile virtualFile = getVirtualFile(path);
        if (virtualFile != null) {
            PsiFile psiFile = getPsiFile(virtualFile);
            assert psiFile != null;
            return (DocumentImpl) PsiDocumentManager.getInstance(client.getProject()).getDocument(psiFile);
        }
        return null;
    }

    public static void replace(String path, int lineNumber, String text) {
        RTBytezClient client = RTBytezClient.getInstance();
        DocumentImpl document = getDocument(path);
        if (document != null) {
            int lineStartOffset = document.getLineStartOffset(lineNumber - 1);
            int lineEndOffset = document.getLineEndOffset(lineNumber - 1);
            WriteCommandAction.runWriteCommandAction(client.getProject(), () -> {
                long l = LocalTimeCounter.currentTime();
                client.getFileModTracker().addCache(path, l);
                document.replaceString(lineStartOffset, lineEndOffset, text, l, false);
                PsiDocumentManager.getInstance(client.getProject()).commitDocument(document);
            });
        } else {
            Console.log("DOCUMENT_EDITOR", "Couldn't run a replacement because we couldn't find the file: " + path);
        }
    }

    public static void addLine(String path, int afterLineNumber) {
        RTBytezClient client = RTBytezClient.getInstance();
        DocumentImpl document = getDocument(path);
        if (document != null) {
            int offset = document.getLineEndOffset(afterLineNumber);
            WriteCommandAction.runWriteCommandAction(client.getProject(), () -> {
                long l = LocalTimeCounter.currentTime();
                client.getFileModTracker().addCache(path, l);
                document.replaceString(offset, offset, "\n", l, false);
                PsiDocumentManager.getInstance(client.getProject()).commitDocument(document);
            });
        } else {
            Console.log("DOCUMENT_EDITOR", "Couldn't add a line because we couldn't find the file: " + path);
        }
    }

    public static void removeLine(String path, int lineNumber) {
        RTBytezClient client = RTBytezClient.getInstance();
        DocumentImpl document = getDocument(path);
        if (document != null) {
            int startingOffset = document.getLineStartOffset(lineNumber - 1);
            int endingOffset = document.getLineEndOffset(lineNumber - 1);
            WriteCommandAction.runWriteCommandAction(client.getProject(), () -> {
                long l = LocalTimeCounter.currentTime();
                client.getFileModTracker().addCache(path, l);
                document.replaceString(startingOffset, endingOffset, "", l, false);
                PsiDocumentManager.getInstance(client.getProject()).commitDocument(document);
            });
        } else {
            Console.log("DOCUMENT_EDITOR", "Couldn't remove a line because we couldn't find the file: " + path);
        }
    }

    public static PsiFile psiFileFromString(String fileName) {
        Project project = RTBytezClient.getInstance().getProject();
        VirtualFile currentDirectory = ProjectRootManager.getInstance(project).getContentRoots()[0];
        String regexString = "/|" + Pattern.quote(".");
        String[] splitFileName = fileName.split(regexString);
        String fileTypeExtension = splitFileName[splitFileName.length - 1];
        for (int i = 0; i < splitFileName.length - 2; i++) {
            try {
                currentDirectory = currentDirectory.createChildDirectory(null, splitFileName[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
        PsiFile psiFile = psiFileFactory.createFileFromText(splitFileName[splitFileName.length - 2], FileTypeManager.getInstance().getStdFileType(fileTypeExtension), "");
        PsiDirectoryFactory.getInstance(project).createDirectory(currentDirectory).add(psiFile);
        return psiFile;
    }

    public static String toRelPath(String fullPath) {
        Project project = RTBytezClient.getInstance().getProject();
        String rootDirectory = ProjectRootManager.getInstance(project).getContentRoots()[0].getCanonicalPath() + "/";
        String s = fullPath.substring(rootDirectory.length());
        Console.log("RELPATH", s);
        return s;
    }

    public static boolean isValidURI(String uri) {
        try {
            new URI(uri);
        } catch (URISyntaxException e) {
            return false;
        }
        return true;
    }
}
