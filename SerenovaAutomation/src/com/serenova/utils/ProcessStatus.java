/**
 * Class for managing the execution of system related operations which we have to perform to execute browserstack.exe.
 *   
 * @author Shrikant Jaiswal
 **/

package com.serenova.utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

public class ProcessStatus {
	public static boolean isProcessIsAlive() throws IOException {
		Process p = Runtime.getRuntime().exec("tasklist");
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (((line = input.readLine()) != null)) {
			if (line.contains("BrowserStackLocal.exe")) {
				return true;
			}
		}
		return false;
	}

	public static void destroyProcess() throws IOException {
		Runtime.getRuntime().exec("taskkill /F /IM BrowserStackLocal.exe");
	}

	public static void main(String args[]) throws IOException, InterruptedException {
		isProcessIsAlive();
	}
	public static void fileWrite(){
		try{   
		    FileWriter windowsFw=new FileWriter("stop.bat");  
	        System.out.println(windowsFw.toString());
	        windowsFw.write(" taskkill /pid "+getProcessId()+" /f ");    
	        windowsFw.close(); 
	        System.out.println("success");
	      }
		catch(Exception e){
	        System.out.println(e);
	       }    
	   }
	
	public static String getProcessId() throws IOException{
		String current_process_info = ManagementFactory.getRuntimeMXBean().getName();
		 String process_id = current_process_info.split("@")[0];
		return process_id;
	}
}
