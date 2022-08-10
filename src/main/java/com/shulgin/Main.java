package com.shulgin;

import com.shulgin.ftpclient.FTPClient;
import com.shulgin.services.FtpService;
import com.shulgin.services.FtpServiceImpl;

import java.util.Scanner;

public class Main {

    private String host;
    private String localhost;
    private String type;
    private String username;
    private String password;

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    public void run() throws Exception{
        readFtpServerInfo();
        //FtpService ftpService = new FtpService(host, type, username, password);
        FTPClient client = new FTPClient(host, 21);
        FtpService ftpService = new FtpServiceImpl(client);
        ftpService.auth(username, password);
        ftpService.uploadFile("");
        //ftpService.downloadFile("students.json");
        client.close();
    }

    public void readFtpServerInfo() throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите host:");
            host = scanner.nextLine();
            System.out.print("Введите логин:");
            username = scanner.nextLine();
            System.out.print("Введите пароль:");
            password = scanner.nextLine();
            System.out.print("Выберите тип подключения(active, passive):");
            type = scanner.nextLine().toLowerCase();
            if (!(type.equals("active") || type.equals("passive"))) {
                throw new Exception();
            }
            if(type.equals("active")) {
                System.out.print("Введите Ваш ip-адрес для активного подключения:");
                localhost = scanner.nextLine();
            }
        }
    }

    public void printMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1. Получение списка студентов по имени");
        System.out.println("2. Получение информации о студенте по id");
        System.out.println("3. Добавление студента ");
        System.out.println("4. Удаление студента по id");
        System.out.println("5. Завершение работы");
    }
}
