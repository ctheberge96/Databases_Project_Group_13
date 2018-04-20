package com.group13.scenes;

/* 
 * Tyler Crosby
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */ 

/*
 * NOTE!!!
 * 
 * This is a static import. It helps so you don't have to type
 * "MediaCenterApplication" every time you need something from the class.
 * Just type what you need (such as changeScene, or INPUT_SCANNER)
 */
import static com.group13.main.MediaCenterApplication.*;

import com.group13.main.MediaCenterApplication; //lies, i am using you...
import com.group13.queries.Admin;
import com.group13.queries.Creator;
import com.group13.queries.User;
import com.group13.scenes.AppScene;
import com.group13.util.Menu;

/**
 * 
 * MENU OPTION 1)
 * 		LOGIN TO EXISTING ACCOUNT. 
 * 		1) TAKES USERNAME AND PASSWORD
 * 		2) CREATES A USER OBJECT
 * 		3) IF USER IS VALID --> CHECKS PRIVILEDGES OF THE USER TO CHOOSE THE CORRECT VIEW TO GIVE
 * 		4) IF USER ISN'T VALID --> BREAK
 * 
 * @author Tyler Crosby
 */
@SuppressWarnings("unused")
public class LoginScene extends AppScene {

	private String inputedUser;
	private String inputedPass;
	
	private Menu menu = new Menu("Login");
	private Menu curMenu = menu;
	
	public LoginScene() {
		
		menu.addOption("Login to existing account", () -> {
			/*
			 * CONNER
			 * 
			 * I reworked this the best i can without being able to test it, i added the kill loop to all of the options before it switchs to the next scene
			 * hopefully that fixes the infinite loop issue.
			 */
			boolean loginState = true;
			while(loginState) {
				System.out.print("Input your Username: ");
				inputedUser = INPUT_SCANNER.nextLine();
				System.out.println();
				System.out.println("Input Password: ");
				inputedPass = INPUT_SCANNER.nextLine();
				
				User user = new User(inputedUser, inputedPass);
				if(user.isValid()) {
					currentUser = user; //accepts user as valid for moving on
					/*if(Creator.isCreator(user)) {
						loginState = false; //kill the loop
						changeScene(CREATOR_SCENE);
					}
					else if(Admin.isAdmin(user)) {
						loginState = false; //kill the loop
						changeScene(ADMIN_SCENE);
					}
					else {
						loginState = false; //kill the loop
						changeScene(USER_SCENE);
					}*/
					loginState = false;
					changeScene(USER_SCENE);
				}
				else {
					System.out.println("User not found!");
				}
			}
			
		});
		
		menu.addOption("Register new account", () -> {
			
			boolean newUserState = true;
			while(newUserState) {
				System.out.print("Input a new Username: "); //GET USERNAME
				inputedUser = INPUT_SCANNER.nextLine();
				System.out.println();
				System.out.print("Input a Password: "); //GET PASSWORD
				inputedPass = INPUT_SCANNER.nextLine();
				System.out.println("");
				
				//NO cancelling, cancelling is for wussies
				
				User user = new User(inputedUser, inputedPass);
				if(user.isValid()) { //IF THE USER ALREADY IS VALID DOESN'T WORK FBM
					System.out.println("User already exists!");
					System.out.println();
				}
				else {
					User.registerNewUser(inputedUser, inputedPass);
					newUserState = false;
					System.out.println("You are now able to log in!");
				}
			}
			
		});
		
		menu.addOption("Exit application", () -> {
			
			//TODO
			//How should the application be exited?
			//Should we tell the user?
			
			System.out.print("Exit Application? Y/N: " );
			String exit = INPUT_SCANNER.nextLine();
			System.out.println();
			if(exit.charAt(0) == 'y') {
				System.out.println("System shutting down...");
				System.exit(0);
			} //NO ELSE, MEANING IF ITS MISTYPED NO KILL DA PROGRAM
			
		});
		
	}
	
	private boolean loop = true;
	
	@Override
	public void onStart() {
		
		while (loop) {
			
			curMenu.print();
			curMenu.getInput(INPUT_SCANNER);
			System.out.println();
			
		}
		
	}

	@Override
	public void onExit() {
		
		//TODO
		//What happens when we exit the login scene?
		
	}

}