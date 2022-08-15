package com.shulgin;

import com.shulgin.entity.Student;
import com.shulgin.entity.User;
import com.shulgin.ftpclient.FTPClient;
import com.shulgin.jsonparser.JsonParser;
import com.shulgin.menu.Menu;
import com.shulgin.menu.MenuExecutor;
import com.shulgin.menu.commands.*;
import com.shulgin.services.FtpService;
import com.shulgin.services.FtpServiceImpl;
import com.shulgin.services.StudentService;
import com.shulgin.services.StudentServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Основной класс запуска приложения.
 */
public class Main {
    private final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    private final StudentService studentService = new StudentServiceImpl();
    private FtpService ftpService;
    private final MenuExecutor menuExecutor = new MenuExecutor(ftpService, studentService, console);
    private FTPClient client;
    private JsonParser json;
    private User user;

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    /**
     * Запуск приложения.
     * @throws Exception исключения при работе приложения.
     */
    public void run() throws Exception{
        loadData();
        Menu menu = loadMenu();
        int action;
        do {
            printMenu();
            action = Integer.parseInt(console.readLine()) - 1;
            menu.doAction(action);
            System.out.println(studentService.findAllStudents());
        }while(action != 4);
        exit();
    }

    /**
     * Метод загрузки данных о пользователе и установление подключения.
     * @throws Exception исключения при загрузке.
     */
    public void loadData() throws Exception{
        user = loadUser();
        client = new FTPClient(user.getRemoteHost(), 21);
        ftpService = new FtpServiceImpl(client);
        ftpService.downloadFile(user, "students.json");
        File file = new File("students.json");
        json = new JsonParser(file);
        List<Student> students = json.parse();
        studentService.addStudents(students);
    }

    /**
     * Метод загрузки данных о пользователе.
     * @return возвращает объет пользователя.
     * @throws Exception исключения загрузки пользователя.
     */
    public User loadUser() throws Exception{
        User user = new User();
        System.out.print("Введите адрес FTP-сервера:");
        user.setRemoteHost(console.readLine());
        System.out.print("Введите логин:");
        user.setUsername(console.readLine());
        System.out.print("Введите пароль:");
        user.setPassword(console.readLine());
        System.out.print("Выберите тип подключения(active, passive):");
        user.setType(console.readLine().toLowerCase());
        if (!(user.getType().equals("active") || user.getType().equals("passive"))) {
            throw new Exception();
        }
        if(user.getType().equals("active")) {
            System.out.print("Введите Ваш адрес для активного подключения:");
            user.setLocalHost(console.readLine());
        }
        return user;
    }

    /**
     * Метод загрузки меню и установления команд на слоты.
     * @return возвращает объект меню.
     */
    public Menu loadMenu() {
        Menu menu = new Menu();
        Command getStudentsByNameCommand = new GetStudentsByNameCommand(menuExecutor);
        Command getStudentByIdCommand = new GetStudentByIdCommand(menuExecutor);
        Command addStudentCommand = new AddStudentCommand(menuExecutor);
        Command deleteStudentByIdCommand = new DeleteStudentByIdCommand(menuExecutor);
        menu.setCommand(0, getStudentsByNameCommand);
        menu.setCommand(1, getStudentByIdCommand);
        menu.setCommand(2, addStudentCommand);
        menu.setCommand(3, deleteStudentByIdCommand);
        return menu;
    }

    /**
     * Метод сохраняет файл и выгружает на сервер.
     * @throws Exception исключение при загрузке файла на сервер.
     */
    public void exit() throws Exception{
        json.saveStudents(studentService.findAllStudents());
        ftpService.uploadFile(user,"students.json");
        console.close();
        client.close();
    }

    /**
     * Метод выводит меню на консоль.
     */
    public void printMenu() {
        System.out.println("1. Получение списка студентов по имени");
        System.out.println("2. Получение информации о студенте по id");
        System.out.println("3. Добавление студента ");
        System.out.println("4. Удаление студента по id");
        System.out.println("5. Завершение работы");
    }
}
