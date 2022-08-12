package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.exceptions.AuthenticationException;
import com.shulgin.ftpclient.FTPClient;

/**
 * Класс реализует команду USER.
 */
public class UserCommand implements Command {

    private final FTPClient client;
    private final String username;

    /**
     * Конструктор принимает на вход FTPClient и логин пользователя.
     * @param client объект клиента.
     * @param username логин.
     */
    public UserCommand(FTPClient client, String username) {
        this.client = client;
        this.username = username;
    }

    /**
     * Метод выполняет поманду.
     * @return результат выполнения команды.
     * @throws AuthenticationException неверный логин.
     */
    @Override
    public String execute() throws Exception {
        String request = "USER " + username;
        String response = client.sendCommand(request);
        if(response == null || !response.startsWith("331")) {
            throw new AuthenticationException();
        }
        return response;
    }
}
