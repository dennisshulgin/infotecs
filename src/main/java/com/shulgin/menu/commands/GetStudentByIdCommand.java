package com.shulgin.menu.commands;

import com.shulgin.entity.Student;
import com.shulgin.menu.MenuExecutor;
import com.shulgin.services.StudentService;

import java.io.IOException;

public class GetStudentByIdCommand implements Command{
    private MenuExecutor menuExecutor;

    public GetStudentByIdCommand(MenuExecutor menuExecutor) {
        this.menuExecutor = menuExecutor;
    }

    @Override
    public void execute() {
        StudentService studentService = menuExecutor.getStudentService();
        System.out.println("Введите id:");
        int id;
        try{
            id = Integer.parseInt(menuExecutor.getConsole().readLine());
            Student student = studentService.findStudentById(id);
            System.out.println(student);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
