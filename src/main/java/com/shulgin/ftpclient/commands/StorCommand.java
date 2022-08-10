package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.connections.Connection;
import com.shulgin.ftpclient.exceptions.FileUploadException;
import com.shulgin.ftpclient.FTPClient;

import java.io.File;
import java.io.FileInputStream;

public class StorCommand implements Command{
    private final FTPClient client;
    private final File file;

    public StorCommand(FTPClient client, String path) {
        this.client = client;
        this.file = new File(path);
    }

    @Override
    public String execute() throws Exception {
        String request = "STOR " + file.getName();
        String response = client.sendCommand(request);
        Connection connection = client.getConnection();
        FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
        int bytesCount = fileInputStream.available();
        byte[] buffer = new byte[1024];
        while (bytesCount > 0) {
            int readCount = fileInputStream.read(buffer);
            connection.write(buffer, 0, readCount);
            bytesCount -= readCount;
        }
        if(response == null || !response.startsWith("150")) {
            throw new FileUploadException();
        }
        return response;
    }
}
