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
    public PassiveConnection(String host, int port) {
        super(host, port);
    }

    /**
     * Метод подключается к серверу.
     */
    @Override
    public void run() {
        try {
            dataSocket = new Socket(host, port);
            inData = dataSocket.getInputStream();
            outData = dataSocket.getOutputStream();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        connected = true;
        while (connected);
    }
}
