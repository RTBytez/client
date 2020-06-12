package com.rtbytez.client.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rtbytez.client.ui.dialogs.RoomCreateDialog;
import org.jetbrains.annotations.NotNull;

public class RoomCreateGetter extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("Performed!");
    }

    public void showRoomID(String roomID) {
        RoomCreateDialog roomCreateDialog = new RoomCreateDialog(roomID);
        roomCreateDialog.showAndGet();
        roomCreateDialog.displayRoomID();
    }
}
