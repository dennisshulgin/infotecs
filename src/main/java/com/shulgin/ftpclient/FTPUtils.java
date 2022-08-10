package com.shulgin.ftpclient;

import java.util.StringJoiner;

/**
 * Класс FTPUtils содержит статические методы для работы с ответами сервера.
 */
public class FTPUtils {
    /**
     * Метод получает на вход строку ответа от сервера по типу
     * 227 Entering Passive Mode (192,168,254,253,233,92)
     * и преобразует её к виду 192,168,254,253,233,92.
     * @param response - строка ответа сервера, содержащая ip-адрес.
     * @return возвращает ip-адрес.
     */
    public static String getIpString(String response) {
        int start = 0;
        int end = 0;
        for(int i = 0; i < response.length(); i++) {
            char ch = response.charAt(i);
            if(ch == '(') {
                start = i;
            }
            if(ch == ')') {
                end = i;
            }
        }
        return response.substring(start + 1, end);
    }

    /**
     * Метод вычисляет порт для подключения к серверу.
     * Например, 192,168,254,253,207,56 означает, что соединение
     * к серверу ожидается на узле с IP-адресом
     * 192.168.254.253 на порту 207 << 8 + 56 = 53048.
     * @param ipString - ip-адрес (например, 192,168,254,253,207,56).
     * @return возвращает порт.
     */
    public static int parsePort(String ipString) {
        String[] nums = ipString.split(",");
        int len = nums.length;
        return (Integer.parseInt(nums[len - 2]) << 8) + Integer.parseInt(nums[len - 1]);
    }

    /**
     * Метод преобразует отвте от сервера к ip-адресу.
     * Например, 192,168,254,253,207,56 будет приобразован к 192.168.254.253
     * @param ipString - ip-адрес (Например, 192,168,254,253,207)
     * @return возвращает строку ip-адрес (192.168.254.253)
     */
    public static String parseIp(String ipString) {
        String[] nums = ipString.split(",");
        StringJoiner ip = new StringJoiner(".");
        for(int i = 0; i < 4; i++) {
            ip.add(nums[i]);
        }
        return ip.toString();
    }

    /**
     * Метод генерирует строку для FTP подключения со стороны сервера.
     * @param host адрес клиента.
     * @param port порт клиента.
     * @return строка в формате 192,168,254,253,207.
     */
    public static String getFtpIp(String host, int port) {
        String ip = host.replaceAll("\\.", ",");
        int d1 = port;
        int d2 = 0;
        while(d1 % 256 != 0) {
            d1--;
            d2++;
        }
        ip += "," + (d1 / 256) + ",";
        ip += d2;
        return ip;
    }

    /**
     * Метод генерирует порт для передачи данных по FTP.
     * Допустимый диапазон от 49152 до 65534.
     * @return порт.
     */
    public static int generateFtpPort() {
        return 49152 + (int)(Math.random() * ((65534 - 49152) + 1));
    }
}
