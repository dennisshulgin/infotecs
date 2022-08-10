package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.exceptions.TypeModeException;
import com.shulgin.ftpclient.FTPClient;

public class TypeCommand implements Command{

    public enum Type {
        A,
        I
    }

    private final FTPClient client;
    private final Type type;

    public TypeCommand(FTPClient client, Type type) {
        this.client = client;
        this.type = type;
    }

    @Override
    public String execute() throws Exception {
        String request = "TYPE " + type;
        String response = client.sendCommand(request);
        if(response == null || !response.startsWith("2")) {
            throw new TypeModeException();
        }
        return response;
    }
}
