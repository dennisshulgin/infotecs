package com.shulgin;

import com.shulgin.entity.Student;
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

public class Main {
    private final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    private final StudentService studentService = new StudentServiceImpl();
    private FtpService ftpService;
    private final MenuExecutor menuExecutor = new MenuExecutor(ftpService, studentService, console);

    private JsonParser json;
    private User user;

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    public void run() throws Exception{
        loadData();
        Menu menu = loadMenu();
        int action;
        do {
            printMenu();
            action = Integer.parseInt(console.readLine()) - 1;
            menu.doAction(action);
        }while(action != 4);

        exit();
    }

    public void loadData() throws Exception{
        user = loadUser();
        FTPClient client = new FTPClient(user.getRemoteHost(), 21);
        ftpService = new FtpServiceImpl(client);
        ftpService.downloadFile(user, "students.json");
        File file = new File("students.json");
        json = new JsonParser(file);
        List<Student> students = json.parse();
        studentService.addStudents(students);
    }

    public User loadUser() throws Exception{
        User user = new User();
        System.out.print("Введите host:");
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
            System.out.print("Введите Ваш ip-адрес для активного подключения:");
            user.setLocalHost(console.readLine());
        }
        return user;
    }

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

    public void exit() throws Exception{
        json.saveStudents(studentService.findAllStudents());
        ftpService.uploadFile(user,"students.json");
    }

    public void printMenu() {
        System.out.println("1. Получение списка студентов по имени");
        System.out.println("2. Получение информации о студенте по id");
        System.out.println("3. Добавление студента ");
        System.out.println("4. Удаление студента по id");
        System.out.println("5. Завершение работы");
    }
}
