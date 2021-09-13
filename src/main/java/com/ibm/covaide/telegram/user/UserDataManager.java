package com.ibm.covaide.telegram.user;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class UserDataManager {
	String[] questionsList;
	private Map<Long, UserData> dataMap;

	public UserDataManager() {
		dataMap = new HashMap<Long, UserData>();
		questionsList = new String[3];
		questionsList[0] = "Do you have feve?";
		questionsList[1] = "Do you have cough?";
		questionsList[2] = "Do you have cold?";
	}

	public UserData getUserData(long clientId) {
		return dataMap.get(clientId);
	}

	public UserData createUserData(long clientId) {
		UserData data = new UserData();
		dataMap.put(clientId, data);
		return data;
	}

	public String getQuestion(long clientId) {
		String result = "Welcome to CovAide";
		UserData data = null;
		if (dataMap.containsKey(clientId)) {
			data = dataMap.get(clientId);
		} else {
			data = createUserData(clientId);
		}
		result = questionsList[data.getCurrentQuestion()];
		data.incrementQuestion();
		return result;
	}

	public void setAnswer(long clientId, String response) {
		if (dataMap.containsKey(clientId)) {
			UserData data = dataMap.get(clientId);
			data.setAnswer(response);
		}
	}

}
