package com.shulgin.menu.commands;

import com.shulgin.entity.Student;
import com.shulgin.menu.MenuExecutor;
import com.shulgin.services.StudentService;

import java.io.IOException;

/**
 * Класс команда для добавления студентов в список.
 */
public class AddStudentCommand implements Command{
    private final MenuExecutor menuExecutor;

    /**
     * Конструктор принимает на вход MenuExecutor.
     * @param menuExecutor ресурсы для команды.
     */
    public AddStudentCommand(MenuExecutor menuExecutor) {
        this.menuExecutor = menuExecutor;
    }

    /**
     * Метод выполняет команду.
     */
    @Override
    public void execute() {
        StudentService studentService = menuExecutor.getStudentService();
        System.out.println("Введите имя:");
        String name;
        try{
            name = menuExecutor.getConsole().readLine();
            Student student = new Student(name);
            studentService.addStudent(student);
            System.out.println(student);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
