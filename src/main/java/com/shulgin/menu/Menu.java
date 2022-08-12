package com.shulgin.menu;

import com.shulgin.menu.commands.Command;
import com.shulgin.menu.commands.NoCommand;

public class Menu {
    Command[] commands;

    public Menu() {
        commands = new Command[5];
        for(int i = 0; i < 5; i++) {
            commands[i] = new NoCommand();
        }
    }

    public void setCommand(int slot, Command command) {
        commands[slot] = command;
    }

    public void doAction(int slot) {
        commands[slot].execute();
    }
}
