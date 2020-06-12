package com.rtbytez.client.ui.dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class RoomJoinDialog extends DialogWrapper {
    private final JTextField roomID = new JTextField();

    public RoomJoinDialog() {
        super(true);
        init();
        setTitle("Enter Room ID");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialog = new JPanel(new BorderLayout());
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));
        dialog.setPreferredSize(new Dimension(300, 0));
        JLabel roomIDText = new JLabel("Room ID");
        roomIDText.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(roomIDText, BorderLayout.CENTER);
        dialog.add(roomID);
        return dialog;
    }

    public String getRoomID() {
        return roomID.getText();
    }
}
