package com.shulgin.ftpclient.connections;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Класс Connection реализует подклюение для передачи данных между клиентом и сервером.
 */
public abstract class Connection{
    protected Socket dataSocket;

    protected InputStream inData;
    protected OutputStream outData;

    protected String host;
    protected int port;
    protected volatile boolean connected;

    /**
     * Конструктор принимает параметры необходимые для подключения.
     * @param host адрес сервера
     * @param port порт сервера
     */
    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Метод записывает данные в поток для сервера.
     * @param bytes массив данных.
     * @param offset позиция, с которой начать запись.
     * @param len длина записываемых данных.
     * @throws IOException исключение при записи.
     */
    public void write(byte[] bytes, int offset, int len) throws IOException{
        outData.write(bytes, offset, len);
    }

    /**
     * Метод возвращает количество байт доступных для чтения из потока.
     * @return количество байт доступных для чтения.
     * @throws IOException исключение при чтении.
     */
    public int available() throws IOException{
        return inData.available();
    }

    /**
     * Метод читает данные из потока.
     * @param bytes массив для записи.
     * @return количество прочитанных байт.
     * @throws IOException исключение при чтении.
     */
    public int read(byte[] bytes) throws IOException{
        return inData.read(bytes);
    }

    /**
     * Метод закрытия соединения.
     */
    public void close() {
        connected = false;
        try {
            if(dataSocket != null) dataSocket.close();
            if(inData != null) inData.close();
            if(outData != null) outData.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод проверяет активность подключения.
     * @return наличие подключения.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Метод инициализирует подключение к серверу.
     * @throws IOException исключение при подключении.
     */
    public abstract void connect() throws IOException;
}
