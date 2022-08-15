package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.connections.Connection;
import com.shulgin.ftpclient.exceptions.FileDownloadException;
import com.shulgin.ftpclient.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Класс реализует команду RETR.
 */
public class RetrCommand implements Command{
    private final FTPClient client;
    private final File file;

    /**
     * Конструктор принимает на вход FTPClient и путь к файлу на сервере.
     * @param client объект клиента.
     * @param path путь к файлу.
     */
    public RetrCommand(FTPClient client, String path) {
        this.client = client;
        this.file = new File(path);
    }

    /**
     * Метод выполняет поманду.
     * @return результат выполнения команды.
     * @throws FileDownloadException ошибка скачивания файла.
     * @throws IOException ошибка чтения данных.
     */
    @Override
    public String execute() throws FileDownloadException, IOException {
        Connection connection = client.getConnection();
        String request = "RETR " + file.getName();
        String response = client.sendCommand(request);
        // открываем соединение
        connection.connect();
        /* Создаем файл локально и записываем данный с сервера.
        * Читаем из потока по 1024 байта.
        */
        FileOutputStream fileOutputStream = new FileOutputStream(file.getName());
        int bytesCount = connection.available();
        byte[] buffer = new byte[1024];
        while (bytesCount > 0) {
            int readCount = connection.read(buffer);
            fileOutputStream.write(buffer, 0, readCount);
            bytesCount -= readCount;
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        connection.close();
        if(response == null || !response.startsWith("150")) {
            throw new FileDownloadException();
        }
        return response;
    }
}
