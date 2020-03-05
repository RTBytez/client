package com.rtbytez.client.dialogs;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class CredentialsDialog extends DialogWrapper {
    private JTextField username = new JTextField();
    private JTextField password = new JTextField();

    protected CredentialsDialog(@Nullable Project project, boolean canBeParent) {
        super(canBeParent);
        init();
        setTitle("Enter Credentials");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialog = new JPanel(new BorderLayout());
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));
        dialog.setPreferredSize(new Dimension(300, 100));
        JTextField username = new JTextField(2);
        JTextField password = new JTextField(2);
        dialog.add(username);
        dialog.add(password);
        return dialog;
    }
}
