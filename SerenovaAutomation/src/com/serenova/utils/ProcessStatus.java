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
		System.out.println("IS PROCESS ALIVE");
		String cmd = "tasklist";
	
		if(System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0){
			cmd = "ps -aef";
		}
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (((line = input.readLine()) != null)) {
			//System.out.println(line);
			if (line.contains("BrowserStack")) {
				return true;
			}
		}
		return false;
	}

	public static void destroyProcess() throws IOException {
		String cmd = "taskkill /F /IM BrowserStackLocal.exe";
		if(System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0){
			cmd = "pkill -9 BrowserStack";
		}
		//Runtime.getRuntime().exec("taskkill /F /IM BrowserStackLocal.exe");
		Runtime.getRuntime().exec(cmd);
	}

	public static void main(String args[]) throws IOException, InterruptedException {
		//isProcessIsAlive();
		String cmd = "./BrowserStackLocal" + " --key " + "6sFopSbKXj8fJGPLznCz"
				+ " --local-proxy-host localhost --local-proxy-port " + "52554" + " --force-local --force-proxy";
		System.out.println(cmd);
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (((line = input.readLine()) != null)) {
			System.out.println(line);
		}
	}
	public static void fileWrite(){
		try{   
		    FileWriter windowsFw=new FileWriter("stop.bat");  
	        windowsFw.write(" taskkill /pid "+getProcessId()+" /f ");    
	        windowsFw.close(); 
	        System.out.println("success");
	      
	        FileWriter macFw=new FileWriter("stop.sh");  
	        macFw.write("kill  "+getProcessId());    
	        macFw.close(); 
		
		
		}
		catch(Exception e){
			e.printStackTrace();
		}    
	   }
	
	public static String getProcessId() throws IOException{
		String current_process_info = ManagementFactory.getRuntimeMXBean().getName();
		 String process_id = current_process_info.split("@")[0];
		return process_id;
	}
}
