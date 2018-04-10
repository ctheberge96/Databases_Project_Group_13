package com.group13.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A Menu represents a list of options the user can choose from
 * 
 * @author Conner Theberge
 */
public class Menu {

	private ArrayList<String> options;
	private HashMap<String, Runnable> actionMap;
	private String title;
	
	/**
	 * Creates a menu with the given title and options.
	 * 
	 * <br>Menu will be displayed in this fashion:
	 * <br>
	 * <br>Menu Title
	 * <br>-----------------
	 * <br>1 - Option 1
	 * <br>2 - Option 2
	 * <br>3 - Option 3
  	 * <br>-----------------
	 * 
	 * @param title The title of this menu
	 */
	public Menu(String title) {
		
		this.options = new ArrayList<String>();
		actionMap = new HashMap<>();
		
		this.title = title;
		
	}
	
	/**
	 * Adds an option to this menu.
	 * 
	 * @param option The title of the option
	 * @param action the function that runs when the option is selected
	 */
	public void addOption(String option, Runnable action) {
		
		options.add(option);
		actionMap.put(option, action);
		
	}
	
	/**
	 * Removes an option from this menu
	 * 
	 * @param option The title of the option to remove
	 */
	public void removeOption(String option) {
		
		options.remove(option);
		actionMap.remove(option);
		
	}
	
	/**
	 * Clears all options from this menu
	 */
	public void clearOptions() {
		
		options.clear();
		actionMap.clear();
		
	}
	
	/**
	 * Prints out this menu
	 */
	public void print() {
		
		System.out.println(title);
		System.out.println("-----------------");
		for (int i = 0; i < options.size(); i++) {
			
			System.out.println((i + 1) + " - " + options.get(i));
			
		}
		System.out.println("-----------------");
		
	}
	
	public void getInput(Scanner scanner) {
		
		if (options.isEmpty()) {
			
			return;
			
		}
		
		while (true) {
			
			System.out.print("?> ");
		
			int optionNum = -1;
			
			try {
			
				optionNum = Integer.parseInt(scanner.nextLine());
				
			} catch(Exception e) {
				
				System.out.println("Invalid Choice.");
				
				continue;
				
				//e.printStackTrace();
				
			}
			
			actionMap.get(options.get(optionNum - 1)).run();
			
			break;
			
		}
		
	}
	
}