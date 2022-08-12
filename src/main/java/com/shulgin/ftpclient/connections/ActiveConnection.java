package com.shulgin.ftpclient.connections;


import java.io.*;
import java.net.ServerSocket;

/**
 * Класс ActiveConnection реализует активное подключение,
 * при котором FTP сервер делает запрос на подключение.
 */
public class ActiveConnection extends Connection{

    private final ServerSocket serverSocket;

    /**
     * Конструктор активного подключения
     * @param port порт, который будет открыт для подключения.
     */
    public ActiveConnection(int port) throws IOException{
        super(null, port);
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    /**
     * Метод открывет сокет и ждёт подключения от FTP-сервера.
     * @throws IOException исключение при подключении.
     */
    @Override
    public void connect() throws IOException{
        dataSocket = serverSocket.accept();
        inData = dataSocket.getInputStream();
        outData = dataSocket.getOutputStream();
        connected = true;
    }
}
