package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.connections.Connection;
import com.shulgin.ftpclient.exceptions.FileDownloadException;
import com.shulgin.ftpclient.FTPClient;

import java.io.File;
import java.io.FileOutputStream;

public class RetrCommand implements Command{
    private final FTPClient client;
    private final File file;

    public RetrCommand(FTPClient client, String path) {
        this.client = client;
        this.file = new File(path);
    }

    @Override
    public String execute() throws Exception {
        String request = "RETR " + file.getName();
        String response = client.sendCommand(request);
        Connection connection = client.getConnection();
        FileOutputStream fileOutputStream = new FileOutputStream(file.getName());
        int bytesCount = connection.available();
        byte[] buffer = new byte[1024];
        while (bytesCount > 0) {
            int readCount = connection.read(buffer);
            fileOutputStream.write(buffer, 0, readCount);
            bytesCount -= readCount;
        }
        fileOutputStream.close();
        if(response == null || !response.startsWith("150")) {
            throw new FileDownloadException();
        }
        return response;
    }
}
