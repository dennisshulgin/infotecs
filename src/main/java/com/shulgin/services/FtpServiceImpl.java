package com.shulgin.services;

import com.shulgin.User;
import com.shulgin.ftpclient.FTPClient;
import com.shulgin.ftpclient.commands.*;

public class FtpServiceImpl implements FtpService {
    private final FTPClient client;

    public FtpServiceImpl(FTPClient client) {
        this.client = client;
    }

    @Override
    public void downloadFile(User user, String path) throws Exception {
        Command[] commands = {
                new UserCommand(client, user.getUsername()),
                new PassCommand(client, user.getPassword()),
                new PasvCommand(client),
                new RetrCommand(client, path)
        };
        if(user.getType().equals("active")) {
            commands[2] = new PortCommand(client, user.getLocalHost());
        }
        executeCommands(commands);
    }

    @Override
    public void uploadFile(User user, String path) throws Exception {
        Command[] commands = {
                new UserCommand(client, user.getUsername()),
                new PassCommand(client, user.getPassword()),
                new PasvCommand(client),
                new StorCommand(client, path)
        };
        if(user.getType().equals("active")) {
            commands[2] = new PortCommand(client, user.getLocalHost());
        }
        executeCommands(commands);
    }

    private void executeCommands(Command[] commands) throws Exception{
        for(Command command : commands) {
            command.execute();
        }
    }
}
