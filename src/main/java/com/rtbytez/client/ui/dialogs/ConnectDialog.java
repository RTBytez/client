package com.rtbytez.client.ui.dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import com.rtbytez.client.ConnectSanitizedInput;
import com.rtbytez.client.InputSanitizer;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ConnectDialog extends DialogWrapper {

    private final JTextField uri = new JTextField();

    public ConnectDialog() {
        super(true);
        init();
        setTitle("Connect to RTBytez Server");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialog = new JPanel(new BorderLayout());
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));
        dialog.setPreferredSize(new Dimension(300, 0));
        JLabel serverURIText = new JLabel("Server URI");
        serverURIText.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(serverURIText, BorderLayout.CENTER);
        dialog.add(uri);
        return dialog;
    }

    public ConnectSanitizedInput getUriText() {
        InputSanitizer sanitizer = new InputSanitizer();
        String input = uri.getText();
        ConnectSanitizedInput connectSanitizedInput = sanitizer.sanitizeInputs(input);
        return connectSanitizedInput;
    }
}

