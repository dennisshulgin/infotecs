package com.shulgin.ftpclient.commands;

public class NoCommand implements Command{
    @Override
    public String execute() throws Exception {
        return "No command";
    }
}
