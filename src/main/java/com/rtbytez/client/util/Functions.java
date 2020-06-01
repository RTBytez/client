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
import com.intellij.util.LocalTimeCounter;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.common.util.Console;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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

    public static PsiFile psiFileFromString(String path) {
        VirtualFile file = getVirtualFile(path);
        if (file != null) {
            return getPsiFile(file);
        }
        Project project = RTBytezClient.getInstance().getProject();
        AtomicReference<PsiFile> psiFile = new AtomicReference<>();
        return ApplicationManager.getApplication().runWriteAction((Computable<PsiFile>) () -> {

            VirtualFile currentDirectory = ProjectRootManager.getInstance(project).getContentRoots()[0];

            String regexString = "/";
            String[] splitFileName = path.split(regexString);
            String fileTypeExtension = splitFileName[splitFileName.length - 1].split(Pattern.quote("."))[1];
            for (int i = 0; i < splitFileName.length - 1; i++) {
                try {
                    ArrayList<String> fileNames = new ArrayList<>();
                    for (VirtualFile v : currentDirectory.getChildren()) {
                        fileNames.add(v.getName());
                    }
                    if (!fileNames.contains(splitFileName[i])) {
                        currentDirectory = currentDirectory.createChildDirectory(null, splitFileName[i]);
                    } else {
                        for (VirtualFile v : currentDirectory.getChildren()) {
                            if (splitFileName[i].equals(v.getName())) {
                                currentDirectory = v;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
            System.out.println(FileTypeManager.getInstance().getStdFileType("Type:" + fileTypeExtension));
            psiFile.set(psiFileFactory.createFileFromText(splitFileName[splitFileName.length - 1], FileTypeManager.getInstance().getStdFileType(fileTypeExtension), ""));
            Objects.requireNonNull(PsiManager.getInstance(project).findDirectory(currentDirectory)).add(psiFile.get());
            return psiFile.get();
        });
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

    public static String[] getFilePaths() {
        ArrayList<String> filePaths = new ArrayList<>();
        ArrayList<String> ignoredFiles = new ArrayList<>();
        Set<VirtualFile> filesInProject = getFilesInProject();
        for (VirtualFile v : filesInProject) {
            if (v.getName().equals(".gitignore")) {
                try {
                    String path = toRelPath(v.getPath().substring(0, v.getPath().length() - 10));
                    String ignoredFilesString = new String(v.contentsToByteArray());
                    ignoredFilesString = ignoredFilesString.replace("\r", "").replace("\n", "").replace("//", "/");
                    for (String s : ignoredFilesString.split("/")) {
                        ignoredFiles.add(path + s);
                    }
                } catch (IOException e) {
                    System.out.println("oof");
                }
            }

            filePaths.add(toRelPath(v.getPath()));
        }
        for (VirtualFile v : filesInProject) {
            String s = toRelPath(v.getPath());
            if (ignoredFiles.contains(s) || ignoredFiles.contains(v.getName())) {
                filePaths.remove(s);
                System.out.println("Removed " + v.getName() + "!");
            }
        }
        String[] filePathsArray = new String[filePaths.size()];
        for (int i = 0; i < filePaths.size(); i++) {
            filePathsArray[i] = filePaths.get(i);
        }
        return filePathsArray;
    }

    public static Set<VirtualFile> getFilesInProject() {
        Set<VirtualFile> virtualFiles = new HashSet<>();
        Project project = RTBytezClient.getInstance().getProject();
        String basepath = ProjectRootManager.getInstance(project).getContentRoots()[0].getCanonicalPath() + "/";
        VirtualFile virtualFile = getVirtualFile(toRelPath(basepath));
        ArrayList<VirtualFile> toBeScanned = new ArrayList<>(Arrays.asList(virtualFile.getChildren()));
        while (toBeScanned.size() != 0) {
            VirtualFile v = toBeScanned.get(0);
            if (v.getChildren().length == 0) {
                virtualFiles.add(v);
                toBeScanned.remove(v);
            } else {
                toBeScanned.addAll(Arrays.asList(v.getChildren()));
                toBeScanned.remove(v);
            }

        }
        return virtualFiles;
    }


    public static void safeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
