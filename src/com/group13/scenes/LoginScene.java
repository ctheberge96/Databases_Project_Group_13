package com.group13.scenes;

import com.group13.main.MediaCenterApplication;
import com.group13.queries.User;
import com.group13.util.Menu;


public class LoginScene extends AppScene {
	private String inputedUser;
	private String inputedPass;
	
	
	private Menu loginMenu = new Menu("Login Menu");
	private Menu curMenu = loginMenu;
	public LoginScene() {
	
		loginMenu.addOption("Login to your account", () -> {
			/*
			 * While loginState = false
			 * asks for user and pass
			 * 
			 * if user and pass exist in server
			 * checks if user is a creator
			 * if they are they are sent to creator scene
			 * else user scene
			 */
			boolean loginState = false;
			while(!loginState) {
				System.out.print("Input your Username: ");
				inputedUser = MediaCenterApplication.INPUT_SCANNER.nextLine();
				System.out.println();
				System.out.println("Input Password: ");
				inputedPass = MediaCenterApplication.INPUT_SCANNER.nextLine();
				
				User user = new User(inputedUser, inputedPass);
				if(user.isValid()) {
					MediaCenterApplication.currentUser = user; //accepts user as valid for moving on
					if(user.getCreatorStatus() == 'a') {
						MediaCenterApplication.changeScene(MediaCenterApplication.CREATOR_SCENE);
					}
					else {
						MediaCenterApplication.changeScene(MediaCenterApplication.USER_SCENE);
					}
				
				}
				else {
					User.deleteUser(user);
				}
			}
		});
		
		loginMenu.addOption("Register new user", () -> {
			/*
			 * While newUserValid is false loop for username and password
			 * 
			 * User Object is constructed then tested for validity -> 
			 * if valid update newUserState bool
			 * 
			 * else delete the user and loop again.
			 */
			boolean newUserState = false;
			while(!newUserState) {
				System.out.print("Input a new Username: ");
				inputedUser = MediaCenterApplication.INPUT_SCANNER.nextLine();
				System.out.println();
				System.out.print("Input a Password: ");
				inputedPass = MediaCenterApplication.INPUT_SCANNER.nextLine();
				System.out.println("");
			
				User user = new User(inputedUser, inputedPass);
				if(user.isValid()) {
					System.out.println("User already exists!");
					System.out.println();
					User.deleteUser(user);
				}
				else {
					User.registerNewUser(inputedUser, inputedPass);
					newUserState = true;
					System.out.println("You are now able to log in!");
				}
			}
		});		
	}

	private boolean loop = true;
	
	@Override
	public void onStart() {
		
		while (loop) {
			
			curMenu.print();
			curMenu.getInput(MediaCenterApplication.INPUT_SCANNER);
			System.out.println();
			
		}
		
	}

	@Override
	public void onExit() {
		
		loop = false;
		
	}
	
}