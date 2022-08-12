package com.shulgin.ftpclient.commands;

import com.shulgin.ftpclient.exceptions.AuthenticationException;
import com.shulgin.ftpclient.FTPClient;

/**
 * Класс реализует команду PASS.
 */
public class PassCommand implements Command {
    private final FTPClient client;
    private final String password;

    /**
     * Конструктор принимает на вход FTPClient и пароль пользователя.
     * @param client объект клиента.
     * @param password пароль.
     */
    public PassCommand(FTPClient client, String password) {
        this.client = client;
        this.password = password;
    }

    /**
     * Метод выполняет поманду.
     * @return результат выполнения команды.
     * @throws AuthenticationException неверный пароль.
     */
    @Override
    public String execute() throws AuthenticationException {
        String request = "PASS " + password;
        String response = client.sendCommand(request);
        if(response == null || !response.startsWith("230")) {
            throw new AuthenticationException();
        }
        return response;
    }
}
