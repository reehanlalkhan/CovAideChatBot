package com.ibm.covaide.telegram.infra;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

public class MessageButtons {

	public SendMessage setButtons(SendMessage sendMessage) {

		// Create a keyboard
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		
		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(true);

		// Create a list of keyboard rows
		List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
		// First keyboard row
		KeyboardRow keyboardFirstRow = new KeyboardRow();

		// Add buttons to the first keyboard row
		keyboardFirstRow.add(new KeyboardButton("Yes"));

		// Second keyboard row
		KeyboardRow keyboardSecondRow = new KeyboardRow();
		// Add the buttons to the second keyboard row
		keyboardSecondRow.add(new KeyboardButton("No"));

		// Add all of the keyboard rows to the list
		keyboard.add(keyboardFirstRow);
		keyboard.add(keyboardSecondRow);
		// and assign this list to our keyboard
		replyKeyboardMarkup.setKeyboard(keyboard);
		
		sendMessage.setReplyMarkup(replyKeyboardMarkup);
		
		return sendMessage;
	}
	
	public SendMessage setYesNoButtons(SendMessage sendMessage) {
        List<List<InlineKeyboardButton>> buttonsList = new ArrayList<List<InlineKeyboardButton>>();
        List<InlineKeyboardButton> buttonsSet = new ArrayList<InlineKeyboardButton>();
        buttonsSet.add(new InlineKeyboardButton().setText("Yes").setCallbackData("Y"));
        buttonsSet.add(new InlineKeyboardButton().setText("No").setCallbackData("N"));
        buttonsList.add(buttonsSet);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttonsList);
        sendMessage.setReplyMarkup(markupKeyboard);
        
        return sendMessage;
    }

}
