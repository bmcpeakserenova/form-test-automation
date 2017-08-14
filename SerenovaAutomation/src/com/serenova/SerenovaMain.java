/**
 Main class to manage the whole execution of automation script
 @author Shrikant Jaiswal
 * 
 * **/
package com.serenova;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.serenova.script.SerenovaScript;
import com.serenova.utils.Mailer;
import com.serenova.utils.ProcessStatus;
import com.serenova.utils.Utils;

public class SerenovaMain {

	public static void main(String[] args) {
		try {
			ProcessStatus.fileWrite();
			Scanner testControlScr = Utils.getScanner("Testcases.csv");
		
			if (ProcessStatus.isProcessIsAlive()) {
				System.out.println("killing existing Browser stack process ");
				ProcessStatus.destroyProcess();
				Thread.sleep(10000);
			}
		
			// Starting reading test cases from file and execute it one by one
			while (testControlScr.hasNext()) {

				String[] nextTest = testControlScr.nextLine().split(",");
				if (nextTest[2].toLowerCase().startsWith("y")) {
					if (nextTest[0].equalsIgnoreCase("G2-Contact-csm")) {
						SerenovaScript.executeG2Script(nextTest[0], nextTest[1], nextTest[3]);
					} else {
						SerenovaScript.executeScript(nextTest[0], nextTest[1], nextTest[3]);
					}
					Thread.sleep(5000);
				}
			} 
			// sendemail
			System.out.println("MAIL SENDING START");
			Mailer.send();
			// terminate browserstack.exe
			ProcessStatus.destroyProcess();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}