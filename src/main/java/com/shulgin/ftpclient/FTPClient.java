package com.shulgin.ftpclient;

import com.shulgin.ftpclient.connections.Connection;
import com.shulgin.ftpclient.commands.Command;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * FTPClient класс инициализирует подключение к серверу для отправки команд.
 * Реализован паттерн "Команда".
 * Экземпляр класса передается в реализацию команды и запускает метод execute.
 */
public class FTPClient {

    private final Socket commandSocket;

    private final BufferedReader inCommand;
    private final BufferedWriter outCommand;

    private Connection connection;

    private final Charset defaultCharset = StandardCharsets.UTF_8;
    private PrintStream defaultPrintStream = System.out;

    /**
     *
     * @param host адрес подключения.
     * @param port порт подключения.
     * @throws IOException исключение при некорректном открытии соединения.
     */
    public FTPClient(String host, int port) throws IOException{
        commandSocket = new Socket(host, port);
        inCommand = new BufferedReader(new InputStreamReader(commandSocket.getInputStream()));
        outCommand = new BufferedWriter(new OutputStreamWriter(commandSocket.getOutputStream()));
        String response = inCommand.readLine();
    }

    /**
     * Метод отправки команды серверу.
     * @param command команда серверу.
     * @return ответ сервера.
     */
    public synchronized String sendCommand(String command) {
        try{
            while(inCommand.ready()) {
                defaultPrintStream.println(inCommand.readLine());
            }
            String utf8Command = new String(command.getBytes(), defaultCharset)+"\r\n";
            outCommand.write(utf8Command);
            outCommand.flush();
            return inCommand.readLine();
        } catch(Exception e) {
            defaultPrintStream.println(e.getMessage());
        }
        return null;
    }

    /**
     * Метод устанавливает канал передачи данных между сервером и клиентом.
     * Принимает на вход реализацию абстрактного класса Connection.
     * @param connection подключение к серверу для передачи данных.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Метод возвращает текущее подключение к серверу для отправки данных.
     * @return подключение к серверу для передачи данных.
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Метод для установления вывода ошибок клиента.
     * По умолчанию установлен стандартный вывод в консоль.
     * @param printStream логгер.
     */
    public void setDefaultPrintStream(PrintStream printStream) {
        this.defaultPrintStream = printStream;
    }

    /**
     * Метод выполнения команд.
     * @param command команда.
     * @return результат выполнения, полученный от сервера.
     * @throws Exception исключение при выполнении команды.
     */
    public String execute(Command command) throws Exception {
        return command.execute();
    }

    /**
     * Метод закрытия соединения.
     */
    public void close() {
        try {
            if(connection != null) connection.close();
            if(commandSocket != null) commandSocket.close();
            if(inCommand != null) inCommand.close();
            if(outCommand != null) outCommand.close();
        }catch (IOException e) {
            defaultPrintStream.println(e.getMessage());
        }
    }
}
