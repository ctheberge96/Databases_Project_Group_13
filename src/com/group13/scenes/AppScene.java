package com.group13.scenes;

/* 
 * Conner Theberge
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */ 

/**
 * An AppScene is a scene of which is changed to
 * and conducts certain actions upon starting, such
 * as showing the user information.
 * 
 * @author Conner Theberge
 */
public abstract class AppScene {

	/**
	 * Called when this scene is changed to (when this
	 * scene is shown)
	 */
	public abstract void onStart();
	
	/**
	 * Called when this scene is left to go to a different scene
	 */
	public abstract void onExit();
	
}