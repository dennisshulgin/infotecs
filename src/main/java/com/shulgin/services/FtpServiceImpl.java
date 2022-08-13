package com.shulgin.services;

import com.shulgin.entity.User;
import com.shulgin.ftpclient.FTPClient;
import com.shulgin.ftpclient.commands.*;

/**
 * Класс реализует интерфейс FtpService. Скачивание и загрузки файла.
 */
public class FtpServiceImpl implements FtpService {
    private final FTPClient client;

    /**
     * Конструктор принимает на вход FtpClient.
     * @param client клиент.
     */
    public FtpServiceImpl(FTPClient client) {
        this.client = client;
    }

    /**
     * Метод для скачивания файла.
     * @param user данные пользователя.
     * @param path путь к файлу.
     * @throws Exception исключение при скачивании.
     */
    @Override
    public void downloadFile(User user, String path) throws Exception {
        Command[] commands = {
                new UserCommand(client, user.getUsername()),
                new PassCommand(client, user.getPassword()),
                new PasvCommand(client),
                new RetrCommand(client, path)
        };
        if(user.getType().equals("active")) {
            commands[2] = new PortCommand(client, user.getLocalHost());
        }
        executeCommands(commands);
    }

    /**
     * Метод для загрузки файла.
     * @param user данные пользователя.
     * @param path путь к файлу.
     * @throws Exception исключение при загрузке.
     */
    @Override
    public void uploadFile(User user, String path) throws Exception {
        Command[] commands = {
                new UserCommand(client, user.getUsername()),
                new PassCommand(client, user.getPassword()),
                new PasvCommand(client),
                new StorCommand(client, path)
        };
        if(user.getType().equals("active")) {
            commands[2] = new PortCommand(client, user.getLocalHost());
        }
        executeCommands(commands);
    }

    /**
     * Метод выполняет набор команд.
     * @param commands команды.
     * @throws Exception исключение при обработке команд.
     */
    private void executeCommands(Command[] commands) throws Exception{
        for(Command command : commands) {
            command.execute();
        }
    }
}
