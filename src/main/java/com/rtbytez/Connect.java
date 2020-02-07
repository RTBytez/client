package com.rtbytez;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Connect extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent e) {

    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Using the event, create and show a dialog
        Project currentProject = event.getProject();

        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField uriField = new JTextField();
        JLabel passwordText = new JLabel("Password:");
        JLabel uriText = new JLabel("UrI: ");

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Username:"));
        myPanel.add(usernameField);
        myPanel.add(Box.createVerticalBox()); // a spacer
        myPanel.add(passwordText);
        passwordText.setHorizontalAlignment(JLabel.CENTER);
        myPanel.add(passwordField);
        uriText.setHorizontalAlignment(JLabel.CENTER);
        myPanel.add(uriText);
        myPanel.add(uriField);


        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Credentials", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String uri = uriField.getText();
        }
    }
}
