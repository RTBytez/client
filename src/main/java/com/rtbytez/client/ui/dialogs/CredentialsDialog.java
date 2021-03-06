package com.rtbytez.client.ui.dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CredentialsDialog extends DialogWrapper {
    final JTextField username = new JTextField();
    final JTextField password = new JTextField();

    public CredentialsDialog() {
        super(true);
        init();
        setTitle("Enter Credentials");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        JPanel dialog = (JPanel) frame.getContentPane();
        dialog.setLayout(new BorderLayout());
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));
        dialog.setPreferredSize(new Dimension(300, 50));
        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        dialog.add(usernameLabel);
        dialog.add(username);
        dialog.add(passwordLabel);
        dialog.add(password);
        return dialog;
    }

    public ArrayList<String> getCredentials() {
        ArrayList<String> credentials = new ArrayList<>();
        createCenterPanel();
        credentials.add(username.getText());
        credentials.add(password.getText());
        return credentials;
    }

}
