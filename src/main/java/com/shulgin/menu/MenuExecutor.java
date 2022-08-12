package com.shulgin.menu;

import com.shulgin.services.FtpService;
import com.shulgin.services.StudentService;

import java.io.BufferedReader;

public class MenuExecutor {
    private final FtpService ftpService;
    private final StudentService studentService;
    private final BufferedReader console;

    public MenuExecutor(FtpService ftpService, StudentService studentService, BufferedReader console) {
        this.ftpService = ftpService;
        this.studentService = studentService;
        this.console = console;
    }

    public FtpService getFtpService() {
        return ftpService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public BufferedReader getConsole() {
        return console;
    }
}
