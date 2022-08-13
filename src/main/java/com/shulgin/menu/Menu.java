package com.shulgin.menu;

import com.shulgin.menu.commands.Command;
import com.shulgin.menu.commands.NoCommand;

/**
 * Класс Menu содержит список команд для выполенения.
 */
public class Menu {
    private Command[] commands;

    /**
     * Конструктор инициализирует пустые команды.
     */
    public Menu() {
        commands = new Command[5];
        for(int i = 0; i < 5; i++) {
            commands[i] = new NoCommand();
        }
    }

    /**
     * Метод устанавливает команду на слот.
     * @param slot слот команды.
     * @param command команда.
     */
    public void setCommand(int slot, Command command) {
        commands[slot] = command;
    }

    /**
     * Метод выполняет команду.
     * @param slot слот команды.
     */
    public void doAction(int slot) {
        commands[slot].execute();
    }
}
