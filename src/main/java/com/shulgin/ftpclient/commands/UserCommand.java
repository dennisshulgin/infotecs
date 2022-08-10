package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.exceptions.AuthenticationException;
import com.shulgin.ftpclient.FTPClient;

public class UserCommand implements Command {

    private final FTPClient client;
    private final String username;

    public UserCommand(FTPClient client, String username) {
        this.client = client;
        this.username = username;
    }

    @Override
    public String execute() throws Exception {
        String request = "USER " + username;
        String response = client.sendCommand(request);
        if(response == null || !response.startsWith("331")) {
            throw new AuthenticationException();
        }
        return response;
    }
}
