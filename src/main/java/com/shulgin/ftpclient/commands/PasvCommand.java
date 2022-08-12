package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.connections.Connection;
import com.shulgin.ftpclient.connections.PassiveConnection;
import com.shulgin.ftpclient.FTPUtils;
import com.shulgin.ftpclient.exceptions.PassiveModeException;
import com.shulgin.ftpclient.FTPClient;

/**
 * Класс реализует команду PASV.
 */
public class PasvCommand implements Command{
    private final FTPClient client;

    /**
     * Конструктор принимает на вход FTPClient.
     * @param client объект клиента.
     */
    public PasvCommand(FTPClient client) {
        this.client = client;
    }

    /**
     * Метод выполняет поманду.
     * @return результат выполнения команды.
     * @throws PassiveModeException ошибка при установлении пассивного режима.
     */
    @Override
    public String execute() throws Exception {
        String request = "PASV";
        String response = client.sendCommand(request);

        if(response == null || !response.startsWith("227")) {
            throw new PassiveModeException();
        }
        // получаем подстроку, содержащую адрес 12,34,56,78,89,90
        String ipString = FTPUtils.getIpString(response);
        // получаем адрес для подключения 12.34.56.78
        String host = FTPUtils.parseIp(ipString);
        // получаем порт 89 >> 8 + 90
        int port = FTPUtils.parsePort(ipString);
        Connection connection = new PassiveConnection(host, port);
        // подключаемся к северу
        connection.connect();
        client.setConnection(connection);
        return response;
    }
}
