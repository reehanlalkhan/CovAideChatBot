package com.ibm.covaide.watson.studio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AIConnector {
	public static void main(String[] args) throws IOException {
		String inputFields = getInputFields();
		String inputValues = getInputValues();
		execute(inputFields, inputValues);
	}

	public static String execute(String[] inputValues) throws IOException {
		String inputFields = getInputFields();
		String inputValuesString = getInputValues(inputValues);
		return execute(inputFields, inputValuesString);

	}

	public static String execute(String inputFields, String inputValues) throws IOException {

		// NOTE: you must manually set API_KEY below using information retrieved from
		// your IBM Cloud account.

		String API_KEY = "M0RRdRTwqYlQniZdM_UP-FC3OMP9Zn7yeUyqSDjXVrrK";

		HttpURLConnection tokenConnection = null;
		HttpURLConnection scoringConnection = null;
		BufferedReader tokenBuffer = null;
		BufferedReader scoringBuffer = null;
		StringBuffer jsonStringScoring = new StringBuffer();
		try {
			// Getting IAM token
			URL tokenUrl = new URL(
					"https://iam.cloud.ibm.com/identity/token?grant_type=urn:ibm:params:oauth:grant-type:apikey&apikey="
							+ API_KEY);
			tokenConnection = (HttpURLConnection) tokenUrl.openConnection();
			tokenConnection.setDoInput(true);
			tokenConnection.setDoOutput(true);
			tokenConnection.setRequestMethod("POST");
			tokenConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			tokenConnection.setRequestProperty("Accept", "application/json");
			tokenBuffer = new BufferedReader(new InputStreamReader(tokenConnection.getInputStream()));
			StringBuffer jsonString = new StringBuffer();
			String line;
			while ((line = tokenBuffer.readLine()) != null) {
				jsonString.append(line);
			}
			// Scoring request
			URL scoringUrl = new URL(
					"https://us-south.ml.cloud.ibm.com/ml/v4/deployments/12c387ee-efa0-4308-8290-55d1f1202632/predictions?version=2021-09-08");
			String iam_token = "Bearer " + jsonString.toString().split(":")[1].split("\"")[1];
			scoringConnection = (HttpURLConnection) scoringUrl.openConnection();
			scoringConnection.setDoInput(true);
			scoringConnection.setDoOutput(true);
			scoringConnection.setRequestMethod("POST");
			scoringConnection.setRequestProperty("Accept", "application/json");
			scoringConnection.setRequestProperty("Authorization", iam_token);
			scoringConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(scoringConnection.getOutputStream(), "UTF-8");

			// NOTE: manually define and pass the array(s) of values to be scored in the
			// next line
			String payload = "{\"input_data\": [{\"fields\":[" + inputFields + "], \"values\": [[" + inputValues
					+ "]]}]}";
			writer.write(payload);
			writer.close();

			scoringBuffer = new BufferedReader(new InputStreamReader(scoringConnection.getInputStream()));
			String lineScoring;
			while ((lineScoring = scoringBuffer.readLine()) != null) {
				jsonStringScoring.append(lineScoring);
			}
			System.out.println(jsonStringScoring);
		} catch (IOException e) {
			System.out.println("There was an exception." + e.getMessage());
			System.out.println(e.getMessage());
		} finally {
			if (tokenConnection != null) {
				tokenConnection.disconnect();
			}
			if (tokenBuffer != null) {
				tokenBuffer.close();
			}
			if (scoringConnection != null) {
				scoringConnection.disconnect();
			}
			if (scoringBuffer != null) {
				scoringBuffer.close();
			}
		}
		return jsonStringScoring.toString();
	}

	public static String getInputFields() {
		String[] fields = { "D1Fe", "D1Cough", "D1Cold", "D2Fe", "D2Cough", "D2Cold", "D3Fe", "D3Cough", "D3Cold",
				"D4Fe", "D4Cough", "D4Cold", "D5Fe", "D5Cough", "D5Cold" };
		StringBuffer symptom = new StringBuffer();
		for (String field : fields) {
			symptom.append("\"");
			symptom.append(field);
			symptom.append("\",");
		}
		symptom.deleteCharAt(symptom.length() - 1);
		return symptom.toString();
	}

	private static String getInputValues() {
		String[] values = { "N", "Y", "N", "N", "Y", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N" };
		return getInputValues(values);
	}

	private static String getInputValues(String[] values) {
		StringBuffer symptom_value = new StringBuffer();
		for (String field : values) {
			symptom_value.append("\"");
			symptom_value.append(field);
			symptom_value.append("\",");
		}
		symptom_value.deleteCharAt(symptom_value.length() - 1);
		return symptom_value.toString();
	}

}
