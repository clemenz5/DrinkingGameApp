package com.example.drinkinggame.Models;

import com.example.drinkinggame.Activitys.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class FileTransfer {

	public static List<String> getAllPackageNames() {
		File file = new File(MainActivity.PACKAGES_DIR_PATH);
		List<String> fileNameList = new LinkedList<>();
		for (File currentFile : file.listFiles()) {
			fileNameList.add(currentFile.getName());
		}
		return fileNameList;
	}

	public static Package getPackage(String name) {
		if (!getAllPackageNames().contains(name)) {
			return null;
		} else {
			final List<Card> cardList = new LinkedList<>();
			File packageFile = new File(MainActivity.PACKAGES_DIR_PATH + "/" + name);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(packageFile));

				reader.lines().forEach(cardString -> {
					String[] cardStringArray = cardString.split(";");
					cardList.add(new Card(cardStringArray[0], Integer.valueOf(cardStringArray[1]), CardType.valueOf(cardStringArray[2])));
				});
				return new Package(name, cardList);
			} catch (IOException e) {
				return null;
			}
		}
	}

	public static boolean savePackage(Package savePackage) {
		File packageFile = new File(MainActivity.PACKAGES_DIR_PATH + "/" + savePackage.getName());
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(packageFile));
			writer.write("");
			for (Card card : savePackage.getCards()) {
				writer.append(card.toString());
				writer.newLine();
			}
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean deletePackage(String name) {
		File packageFile = new File(MainActivity.PACKAGES_DIR_PATH + "/" + name);
		return packageFile.delete();
	}

	public static boolean saveCardToPackage(String packageName, Card card) {
		File packageFile = new File(MainActivity.PACKAGES_DIR_PATH + "/" + packageName);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(packageFile, true);
			//BufferedWriter writer = new BufferedWriter(new FileWriter(packageFile), true);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			bufferedWriter.write(card.toString());
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
