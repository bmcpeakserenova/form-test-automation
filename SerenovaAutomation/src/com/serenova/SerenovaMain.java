/**
 Main class to manage the whole execution of automation script
 @author Shrikant Jaiswal
 * 
 * **/
package com.serenova;

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
			String expectedURL ="";
			String resultURL = "";
			while (testControlScr.hasNext()) {
				try{
					String[] nextTest = testControlScr.nextLine().split(",");
					expectedURL = nextTest[1];
					resultURL   = nextTest[2];
					if (nextTest[2].toLowerCase().startsWith("y")) {
						SerenovaScript.executeScript(nextTest[0], nextTest[1], nextTest[3]);
					}
						Thread.sleep(5000);
				} catch (Exception e) {
					Mailer.emailResultMEssage = Mailer.emailResultMEssage +" ---"+ expectedURL +" --- "+resultURL+" ---  FAIL\n";
					// TODO: handle exception
				}
			} 
			// sendemail
			System.out.println("MAIL SENDING START");
			Mailer.send();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
 		} finally {
 		// terminate browserstack.exe
 		try {
			ProcessStatus.destroyProcess();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}