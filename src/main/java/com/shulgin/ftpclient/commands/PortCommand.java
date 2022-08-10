package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.FTPUtils;
import com.shulgin.ftpclient.connections.ActiveConnection;
import com.shulgin.ftpclient.connections.Connection;
import com.shulgin.ftpclient.exceptions.ActiveModeException;
import com.shulgin.ftpclient.FTPClient;

public class PortCommand implements Command {

    private final FTPClient client;
    private final String host;
    private int connectTimes = 3;

    public PortCommand(FTPClient client, String host) {
        this.client = client;
        this.host = host;

    }

    @Override
    public String execute() throws Exception {
        if(client.getConnection() != null) {
            client.getConnection().close();
        }
        int port = 1;
        String localhost = FTPUtils.getFtpIp(host, port);
        String request = "PORT " + localhost;
        Connection connection = new ActiveConnection(port);
        new Thread(connection).start();
        while(connectTimes > 0 && !connection.isConnected()) {
            connectTimes--;
            Thread.sleep(500);
        }
        client.setConnection(connection);
        String response = client.sendCommand(request);
        if(response == null || !response.startsWith("200")) {
            throw new ActiveModeException();
        }
        return response;
    }
}
