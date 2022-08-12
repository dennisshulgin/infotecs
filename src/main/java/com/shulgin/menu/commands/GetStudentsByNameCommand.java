package com.shulgin.menu.commands;

import com.shulgin.entity.Student;
import com.shulgin.menu.MenuExecutor;
import com.shulgin.services.StudentService;

import java.io.IOException;
import java.util.List;

public class GetStudentsByNameCommand implements Command{
    private final MenuExecutor menuExecutor;

    public GetStudentsByNameCommand(MenuExecutor menuExecutor) {
        this.menuExecutor = menuExecutor;
    }

    @Override
    public void execute() {
        StudentService studentService = menuExecutor.getStudentService();
        System.out.println("Введите имя:");
        String name;
        try{
            name = menuExecutor.getConsole().readLine();
            List<Student> students = studentService.findStudentByName(name);
            System.out.println(students);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
