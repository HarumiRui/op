package com.example.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotApplication {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // ЗАМЕНИТЕ на реальные данные вашего бота
            String botUsername = "chatMy_123Bot";
            String botToken = "8249836344:AAFG-7XUuOX2_cYmQTanFl25EY0aUc6ANTc";

            ChatBot bot = new ChatBot(botUsername, botToken);
            botsApi.registerBot(bot);

            System.out.println("Бот успешно запущен!");
            System.out.println("Бот: @" + botUsername);

        } catch (TelegramApiException e) {
            System.err.println("Ошибка при запуске: " + e.getMessage());
            e.printStackTrace();
        }
    }
}