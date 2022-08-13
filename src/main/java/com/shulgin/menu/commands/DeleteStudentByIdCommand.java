package com.shulgin.menu.commands;

import com.shulgin.menu.MenuExecutor;
import com.shulgin.services.StudentService;

import java.io.IOException;

/**
 * Класс команда для удаления студентов из списка.
 */
public class DeleteStudentByIdCommand implements Command {
    private final MenuExecutor menuExecutor;

    /**
     * Конструктор принимает на вход MenuExecutor.
     * @param menuExecutor ресурсы для команды.
     */
    public DeleteStudentByIdCommand(MenuExecutor menuExecutor) {
        this.menuExecutor = menuExecutor;
    }

    /**
     * Метод выполняет команду.
     */
    @Override
    public void execute() {
        StudentService studentService = menuExecutor.getStudentService();
        System.out.println("Введите id:");
        int id;
        try{
            id = Integer.parseInt(menuExecutor.getConsole().readLine());
            studentService.deleteStudentById(id);;
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
