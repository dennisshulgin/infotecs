package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.connections.PassiveConnection;
import com.shulgin.ftpclient.FTPUtils;
import com.shulgin.ftpclient.exceptions.PassiveModeException;
import com.shulgin.ftpclient.FTPClient;

public class PasvCommand implements Command{
    private final FTPClient client;

    public PasvCommand(FTPClient client) {
        this.client = client;
    }

    @Override
    public String execute() throws Exception {
        if(client.getConnection() != null) {
            client.getConnection().close();
        }
        String request = "PASV";
        String response = client.sendCommand(request);
        if(response == null || !response.startsWith("227")) {
            throw new PassiveModeException();
        }
        String ipString = FTPUtils.getIpString(response);
        String host = FTPUtils.parseIp(ipString);
        int port = FTPUtils.parsePort(ipString);
        PassiveConnection passiveConnection = new PassiveConnection(host, port);
        new Thread(passiveConnection).start();
        client.setConnection(passiveConnection);
        return response;
    }
}
