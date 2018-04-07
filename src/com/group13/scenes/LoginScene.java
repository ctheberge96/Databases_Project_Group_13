package com.group13.scenes;

import java.util.Scanner;

import com.group13.main.MediaCenterApplication;


public class LoginScene extends AppScene {

	private boolean validUser = false;
	private boolean validPass = false;
	
	private String inputedUser;
	private String inputedPass;
	public LoginScene() {
	
		
		
		
		
	}

	
	private boolean loop = true;
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		while(!validUser) {
			System.out.println("Enter your username: ");
			inputedUser = MediaCenterApplication.INPUT_SCANNER.next();
			
			if(database.contains(inputedUser)) { //if inputedUser is in DB
				
				validUser = true;
				
			}
			else {
				System.out.println("Non existant user!");
				System.out.println();
				System.out.println();
				System.out.println();
			}
		}
		while(!validPass) {
			
			System.out.println("Enter your password: ");
			inputedPass = MediaCenterApplication.INPUT_SCANNER.next();
			
			if(inputedUser.contains(inputedPass)) {
				
				validPass = true;
				
			}
			else {
				System.out.println("Incorrect password!");
				System.out.println();
				System.out.println();
				System.out.println();
			}
			
		}
	}

	@Override
	public void onExit() {
		
		loop = false;
		
	}
	
}