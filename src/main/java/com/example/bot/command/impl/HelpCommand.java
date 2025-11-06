package com.example.bot.command.impl;

import com.example.bot.command.AbstractCommand;
import com.example.bot.command.Command;
import com.example.bot.command.CommandRegistry;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HelpCommand extends AbstractCommand {
    private final CommandRegistry commandRegistry;

    public HelpCommand(CommandRegistry commandRegistry) {
        super("help", "Помощь по command бота");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public String execute(Message message) {
        String argument = getCommandArgument(message).trim();

        if (!argument.isEmpty()) {
            return getSpecificHelp(argument);
        } else {
            return getAllCommandsHelp();
        }
    }

    private String getAllCommandsHelp() {
        StringBuilder helpText = new StringBuilder();
        helpText.append("*Доступные команды:*\n\n");

        int commandNumber = 1;
        for (Command command : commandRegistry.getAllCommands()) {
            helpText.append(commandNumber++)
                    .append(". /")
                    .append(command.getBotCommand().getCommand())
                    .append(" - ")
                    .append(command.getDescription())
                    .append("\n");
        }

        helpText.append("\nДля подробной информации o command: /help <команда>");
        helpText.append("\nВсего команд: ").append(commandRegistry.getCommandCount());

        return helpText.toString();
    }

    private String getSpecificHelp(String commandName) {
        // Убираем слэш если он есть и приводим к нижнему регистру
        if (commandName.startsWith("/")) {
            commandName = commandName.substring(1);
        }
        commandName = commandName.toLowerCase();

        Command command = commandRegistry.getCommand(commandName);
        if (command != null) {
            return String.format(
                    "*Команда /%s*\n" +
                            "*Описание:* %s\n" +
                            "*Быстрый доступ:* Напишите /%s в чат",
                    commandName,
                    command.getDescription(),
                    commandName
            );
        } else {
            return String.format(
                    "Команда \"/%s\" не найдена.\n" +
                            "Используйте /help для просмотра всех команд.\n" +
                            "Проверьте правильность написания команды.",
                    commandName
            );
        }
    }

    @Override
    public boolean canExecute(Message message) {
        // HelpCommand может обрабатывать как /help, так и /help <команда>
        return super.canExecute(message) ||
                (message.hasText() && message.getText().startsWith("/help "));
    }
}