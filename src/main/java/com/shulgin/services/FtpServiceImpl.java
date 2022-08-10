package com.shulgin.services;

import com.shulgin.ftpclient.FTPClient;
import com.shulgin.ftpclient.commands.*;

public class FtpServiceImpl implements FtpService{
    private FTPClient client;

    public FtpServiceImpl(FTPClient client) {
        this.client = client;
    }

    @Override
    public void auth(String username, String password) throws Exception {
        Command[] commands = {
                new UserCommand(client, username),
                new PassCommand(client, password)
        };
        executeCommands(commands);
    }

    @Override
    public void downloadFile(String path) throws Exception {

    }

    @Override
    public void uploadFile(String path) throws Exception{

    }

    private void executeCommands(Command[] commands) throws Exception{
        for(Command command : commands) {
            command.execute();
        }
    }
}
