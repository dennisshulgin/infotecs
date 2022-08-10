package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.exceptions.AuthenticationException;
import com.shulgin.ftpclient.FTPClient;

public class PassCommand implements Command {
    private final FTPClient client;
    private final String password;

    public PassCommand(FTPClient client, String password) {
        this.client = client;
        this.password = password;
    }

    @Override
    public String execute() throws Exception {
        String request = "PASS " + password;
        String response = client.sendCommand(request);
        if(response == null || !response.startsWith("230")) {
            throw new AuthenticationException();
        }
        return response;
    }
}
