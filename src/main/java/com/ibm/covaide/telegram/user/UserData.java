package com.ibm.covaide.telegram.user;

import lombok.Data;

@Data
public class UserData {
	private String chatId;
	private int currentQuestion;
	private String[][] answerMatrix;
	private int currentDay;
	private boolean lastAnswerGiven;

	public UserData() {
		// answers={{"N","N","N"},{"N","N","N"},{"N","N","N"},{"N","N","N"},{"N","N","N"}};
		answerMatrix = new String[3][5];
		resetAnswers();
		currentQuestion = 0;
		currentDay = 0;
		lastAnswerGiven = false;
	}

	private void resetAnswers() {
		for (int i = 0; i < answerMatrix.length; i++) {
			// length returns number of rows
			System.out.print("row " + i + " : ");
			for (int j = 0; j < answerMatrix[i].length; j++) {
				// here length returns # of columns corresponding to current row
				answerMatrix[i][j] = "N";
			}
		}
	}

	public void incrementQuestion() {
		currentQuestion += 1;
		if (currentQuestion >= 3) {
			currentQuestion = 0;
			incrementDay();
		}
	}

	private void incrementDay() {
		currentDay += 1;
		if (currentDay >= 5) {
			currentDay = 0;
			lastAnswerGiven = true;
		}
	}

	public void setAnswer(String answer) {
		updateAnswer(answer, currentQuestion, currentDay);
	}

	private void updateAnswer(String answer, int question, int day) {
		if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
			answerMatrix[question][day] = "Y";
		}
	}

	public String[] getAnswersArray() {
		String[] answers = new String[15];
		int iCounter = 0;
		for (int i = 0; i < answerMatrix.length; i++) {
			// length returns number of rows
			System.out.print("row " + i + " : ");
			for (int j = 0; j < answerMatrix[i].length; j++) {
				// here length returns # of columns corresponding to current row
				answers[iCounter++] = answerMatrix[i][j];
			}
		}
		return answers;
	}

}
