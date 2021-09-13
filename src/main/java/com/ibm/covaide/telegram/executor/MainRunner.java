package com.ibm.covaide.telegram.executor;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.ibm.covaide.telegram.infra.CovAideTelegramBot;
import com.ibm.covaide.telegram.user.UserDataManager;

public class MainRunner {
	
	public static final UserDataManager dataManager = new UserDataManager();

	public static void main(String[] args) {
        ApiContextInitializer.init();
     // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();
     // Register our bot
        try {
            botsApi.registerBot(new CovAideTelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
}

}
