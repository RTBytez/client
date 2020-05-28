package com.rtbytez.client.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rtbytez.client.ConnectSanitizedInput;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class URIGetter extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        //TODO: Fix
        //System.out.println("Point A");
        //RTBytezClient client = RTBytezClient.getInstance();
        //CredentialsGetter credentialsGetter = new CredentialsGetter();
        //try {
        //    ConnectSanitizedInput input = retrieveURI();
        //    if (input != null) {
        //        ArrayList<String> credentials = credentialsGetter.retrieveCredentials();
        //        input.setUsername(credentials.get(0));
        //        input.setPassword(credentials.get(1));
        //        client.getSocketClient().connect(input.getHost());
        //    }
        //} catch (URISyntaxException e) {
        //    e.printStackTrace();
        //}
    }

    public ConnectSanitizedInput retrieveURI() {
        //TODO: Fix
        //ConnectDialog dialog = new ConnectDialog();
        //boolean ok = dialog.showAndGet(); // Hangs
        //if (ok) {
        //    return dialog.getUriText();
        //} else {
        //    return null;
        //}
        throw new NotImplementedException();
    }
}
