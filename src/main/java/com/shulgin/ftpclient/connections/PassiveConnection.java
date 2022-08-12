package com.shulgin.ftpclient.connections;

import java.io.IOException;
import java.net.Socket;

/**
 * Класс PassiveConnection реализует пассивное подключение,
 * при котором FTP сервер ожидает подключение на заданный порт.
 */
public class PassiveConnection extends Connection{

    /**
     * Конструктор пассивного подключения.
     * @param host ip-адрес сервера.
     * @param port порт сервера.
     */
    public PassiveConnection(String host, int port) throws IOException{
        super(host, port);
        dataSocket = new Socket(host, port);
    }

    /**
     * Метод подключается к серверу.
     */
    @Override
    public void connect() throws IOException{
        inData = dataSocket.getInputStream();
        outData = dataSocket.getOutputStream();
        connected = true;
    }
}
