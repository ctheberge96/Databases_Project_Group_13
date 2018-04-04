package com.group13.util;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A Menu represents a list of options the user can choose from
 * 
 * @author Conner Theberge
 */
public class Menu {

	private ArrayList<String> options;
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
	 * @param options All options for the user to choose from
	 */
	public Menu(String title, String...options) {
		
		this.options = new ArrayList<String>();
		for (String option : options) {
			addOption(option);
		}
		
		this.title = title;
		
	}
	
	/**
	 * Adds an option to this menu.
	 */
	public void addOption(String option) {
		
		options.add(option);
		
	}
	
	/**
	 * Removes an option from this menu
	 */
	public void removeOption(String option) {
		
		options.remove(option);
		
	}
	
	/**
	 * Clears all options from this menu
	 */
	public void clearOptions() {
		
		options.clear();
		
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
	
	/**
	 * Gets the input from the user.
	 * <br>Will continue to accept input until valid input
	 * has been recieved.
	 * <br>Returns null if there are no available options
	 * 
	 * @param scanner The scanner to use for user input
	 * @return The selected option's title
	 */
	public String getInput(Scanner scanner) {
		
		if (options.isEmpty()) {
			
			return null;
			
		}
		
		while (true) {
			
			System.out.print("?> ");
		
			try {
			
				int optionNum = Integer.parseInt(scanner.nextLine());
				
				return options.get(optionNum - 1);
				
			} catch(Exception e) {
				
				System.out.println("Invalid Choice.");
				
			}
			
		}
		
	}
	
}