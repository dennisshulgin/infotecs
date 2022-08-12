package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.connections.Connection;
import com.shulgin.ftpclient.exceptions.FileUploadException;
import com.shulgin.ftpclient.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Класс реализует команду STOR.
 */
public class StorCommand implements Command{
    private final FTPClient client;
    private final File file;

    /**
     * Конструктор принимает на вход FTPClient и путь к файлу.
     * @param client объект клиента.
     * @param path путь к файлу.
     */
    public StorCommand(FTPClient client, String path) {
        this.client = client;
        this.file = new File(path);
    }

    /**
     * Метод выполняет поманду.
     * @return результат выполнения команды.
     * @throws FileUploadException ошибка скачивания файла.
     * @throws IOException ошибка чтения данных.
     */
    @Override
    public String execute() throws FileUploadException, IOException {
        Connection connection = client.getConnection();
        String request = "STOR " + file.getName();
        String response = client.sendCommand(request);
        // открываем соединение
        connection.connect();
        /* Открываем файл и пишем в поток сервера.
         * Пишем в поток по 1024 байта.
         */
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
