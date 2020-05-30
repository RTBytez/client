package com.rtbytez.client;

import com.rtbytez.client.socket.ConnectionData;

public class ConnectSanitizedInput {
    private String host;
    private String username;
    private String port;
    private String password;

    public ConnectSanitizedInput(String host, String port, String username) {
        this.host = host;
        this.username = username;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ConnectSanitizedInput{" +
                "host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", port='" + port + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public ConnectionData toConnectionData() {
        return new ConnectionData(getHost(), Integer.parseInt(getPort()), getUsername(), getPassword());
    }
}
