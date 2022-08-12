package com.shulgin.menu.commands;

import com.shulgin.entity.Student;
import com.shulgin.menu.MenuExecutor;
import com.shulgin.services.StudentService;

import java.io.IOException;

public class AddStudentCommand implements Command{
    private MenuExecutor menuExecutor;

    public AddStudentCommand(MenuExecutor menuExecutor) {
        this.menuExecutor = menuExecutor;
    }

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
