package com.rtbytez.client.ui.dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class RoomCreateDialog extends DialogWrapper {
    private String roomID;

    public RoomCreateDialog(String roomID) {
        super(true);
        this.roomID = roomID;
        init();
        setTitle("Enter Credentials");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialog = new JPanel(new BorderLayout());
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));
        dialog.setPreferredSize(new Dimension(300, 0));
        JLabel roomIDText = new JLabel("Room Joined! Room ID is: " + roomID);
        roomIDText.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(roomIDText, BorderLayout.CENTER);
        return dialog;
    }

    public void displayRoomID() {
        createCenterPanel();
    }

}
