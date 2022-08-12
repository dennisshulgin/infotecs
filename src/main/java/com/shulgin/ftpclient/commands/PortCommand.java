package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.FTPUtils;
import com.shulgin.ftpclient.connections.ActiveConnection;
import com.shulgin.ftpclient.connections.Connection;
import com.shulgin.ftpclient.exceptions.ActiveModeException;
import com.shulgin.ftpclient.FTPClient;

import java.io.IOException;

/**
 * Класс реализует команду PORT.
 */
public class PortCommand implements Command {

    private final FTPClient client;
    private final String host;

    /**
     * Конструктор принимает на вход FTPClient и хост пользователя.
     * @param client объект клиента.
     * @param host локальный хост.
     */
    public PortCommand(FTPClient client, String host) {
        this.client = client;
        this.host = host;
    }

    /**
     * Метод выполняет поманду.
     * @return результат выполнения команды.
     * @throws ActiveModeException ошибка при установлении активного режима.
     * @throws IOException ошибка при открытии соединения.
     */
    @Override
    public String execute() throws ActiveModeException, IOException {
        // генерируем порт в диапазоне от 49152 до 65534
        int port = FTPUtils.generateFtpPort();
        // собираем локальный адрес для отправки на сервер
        String localhost = FTPUtils.getFtpIp(host, port);

        Connection connection = new ActiveConnection(port);
        String request = "PORT " + localhost;
        String response = client.sendCommand(request);
        client.setConnection(connection);

        if(response == null || !response.startsWith("200")) {
            throw new ActiveModeException();
        }
        return response;
    }
}
