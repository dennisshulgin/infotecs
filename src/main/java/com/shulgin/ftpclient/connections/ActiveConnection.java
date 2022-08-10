package com.shulgin.ftpclient.connections;

import java.io.*;
import java.net.ServerSocket;

/**
 * Класс ActiveConnection реализует активное подключение,
 * при котором FTP сервер делает запрос на подключение.
 */
public class ActiveConnection extends Connection{

    /**
     * Конструктор активного подключения
     * @param port порт, который будет открыт для подключения.
     */
    public ActiveConnection(int port) {
        super(null, port);
    }

    /**
     * Метод открывет сокет и ждёт подключения от FTP-сервера.
     */
    @Override
    public void run() {
        InputStream is;
        OutputStream os;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            dataSocket = serverSocket.accept();
            is = dataSocket.getInputStream();
            os = dataSocket.getOutputStream();
            inData = new BufferedInputStream(is);
            outData = new BufferedOutputStream(os);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        connected = true;
        while (connected);
    }
}
