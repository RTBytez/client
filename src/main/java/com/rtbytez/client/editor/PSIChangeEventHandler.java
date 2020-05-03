package com.rtbytez.client.editor;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.PsiTreeChangeListener;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.common.util.Console;
import org.jetbrains.annotations.NotNull;

public class PSIChangeEventHandler {

    public void register() {
        Project project = RTBytezClient.getInstance().getProject();

        PsiFileFactory.getInstance(project).createFileFromText();

        PsiManager.getInstance(project).addPsiTreeChangeListener(new PsiTreeChangeListener() {

            @Override
            public void childReplaced(@NotNull PsiTreeChangeEvent event) {
                Console.log("PSI Event: childReplaced - " + event.toString());
                System.out.println(event.getNewChild().getText());
            }

            @Override
            public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }

            @Override
            public void childMoved(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }

            @Override
            public void propertyChanged(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }

            @Override
            public void beforeChildAddition(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }

            @Override
            public void beforeChildRemoval(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }

            @Override
            public void beforeChildReplacement(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }

            @Override
            public void beforeChildMovement(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }

            @Override
            public void beforeChildrenChange(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }

            @Override
            public void beforePropertyChange(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }

            @Override
            public void childAdded(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }

            @Override
            public void childRemoved(@NotNull PsiTreeChangeEvent event) {
                // Not listening
            }
        });
    }

}
