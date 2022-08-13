package com.shulgin.menu;

import com.shulgin.services.FtpService;
import com.shulgin.services.StudentService;

import java.io.BufferedReader;

/**
 * Класс MenuExecutor предназначен для хранения ресурсов, которые необходимы для команд.
 */
public class MenuExecutor {
    private final FtpService ftpService;
    private final StudentService studentService;
    private final BufferedReader console;

    /**
     * Конструктор принимает на вход сервисы и консоль.
     * @param ftpService Ftp сервис.
     * @param studentService сервис студентов.
     * @param console консоль.
     */
    public MenuExecutor(FtpService ftpService, StudentService studentService, BufferedReader console) {
        this.ftpService = ftpService;
        this.studentService = studentService;
        this.console = console;
    }

    /**
     * Метод возвращает Ftp сервис.
     * @return ftp сервис.
     */
    public FtpService getFtpService() {
        return ftpService;
    }

    /**
     * Метод возвращает Student сервис.
     * @return студент сервис.
     */
    public StudentService getStudentService() {
        return studentService;
    }

    /**
     * Метод возвращает консоль.
     * @return консоль.
     */
    public BufferedReader getConsole() {
        return console;
    }
}
