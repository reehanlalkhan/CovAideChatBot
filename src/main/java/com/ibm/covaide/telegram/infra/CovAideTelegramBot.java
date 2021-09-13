package com.ibm.covaide.telegram.infra;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.ibm.covaide.telegram.executor.MainRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CovAideTelegramBot extends TelegramLongPollingBot {

	/**
	 * Method for receiving messages.
	 * 
	 * @param update Contains a message from the user.
	 */
	// @Override
	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {
			// Set variables
			String userResponse = update.getMessage().getText();
			long chat_id = update.getMessage().getChatId();
			MainRunner.dataManager.setAnswer(chat_id, userResponse);
			String message_text = MainRunner.dataManager.getQuestion(chat_id);

			log.debug("Message text: {}, by chat id: {}", message_text, chat_id);

			// Create a message object object
			SendMessage message = addButtonsToMessages(new SendMessage().setChatId(chat_id).setText(message_text));
			try {
				// Sending our message object to user
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized SendMessage addButtonsToMessages(SendMessage sendMessage) {
		MessageButtons buttons = new MessageButtons();

		return buttons.setYesNoButtons(sendMessage);
	}

	/**
	 * This method returns the bot's name, which was specified during registration.
	 * 
	 * @return bot name
	 */
	// @Override
	public String getBotUsername() {
		return "CovAide";
	}

	/**
	 * This method returns the bot's token for communicating with the Telegram
	 * server
	 * 
	 * @return the bot's token
	 */
	// @Override
	public String getBotToken() {
		return "<Bot ID Here>";
	}
}