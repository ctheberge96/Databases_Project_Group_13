package com.group13.test;

/* 
 * Conner Theberge
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import com.group13.queries.Admin;
import com.group13.queries.Creator;
import com.group13.queries.Media;
import com.group13.queries.User;

/**
 * This JUnit tests all Query classes
 * @author Conner Theberge
 */
class QueryTest {

	@Test
	void testUser() {
		
		//Working correctly
		
		User.deleteUser(new User("testU","testP"));
		
		assertEquals(1, User.registerNewUser("testU", "testP"), "Not registering new user!");
		User user = new User("testU","testP");
		assertNotEquals(-1, user.getID(), "User is invalid!");
		assertTrue(user.isValid(), "User is invalid, but their ID != -1");
		
		assertEquals("testU", user.getUsername(), "Username is incorrect!");
		assertEquals("testP", user.getPassword(), "Password is incorrect!");
		
		user.changeUsername("newTestU");
		assertEquals("newTestU", user.getUsername(), "New username is incorrect!");
		user.changePassword("newTestP");
		assertEquals("newTestP", user.getPassword(), "New password is incorrect!");
		
		assertEquals(User.CREATOR_STATUS_NONE, user.getCreatorStatus(), "Creator status is not none!");
		assertTrue(user.requestCreatorStatus(),"Unable to request creator status!");
		assertEquals(User.CREATOR_STATUS_WAITING, user.getCreatorStatus(), "Creator status isn't waiting!");
		
		assertTrue(User.deleteUser(user), "Not deleting user!");
		
		//I'M GONNA WRECK IT!
		
		//registering user with same login
		assertEquals(1, User.registerNewUser("testU", "testP"), "Not registering new user!");
		assertEquals(0, User.registerNewUser("testU", "testP"), "Registering new user when it shouldn't!");
		assertTrue(User.deleteUser(new User("testU", "testP")), "Not deleting user!");
		
		//Getting user with non-existing info
		User brokenUser = new User("~","~");
		
		assertEquals(-1, brokenUser.getID(), "User is valid!");
		
		assertFalse(brokenUser.isValid(), "User is valid, but their ID == -1!");
		try {
			brokenUser.getUsername();
			brokenUser.getPassword();
			fail("Getting username or password on broken user!");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "User is not valid! (Doesn't exist!) Check for validity using isValid()!", "Wrong exception!");
		}
		
		//Changing login info to already existing info
		try {
			brokenUser.changeUsername("blah");
			brokenUser.changePassword("blah");
			fail("Changing username or password on broken user!");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "User is not valid! (Doesn't exist!) Check for validity using isValid()!", "Wrong exception!");
		}
		
		assertFalse(User.deleteUser(brokenUser), "Deleting user!");
		
	}
	
	@Test
	void testCreator() {
		
		//boolean registerCreator(User user, String bankRouting, String bankAccNumber)
		
		User.registerNewUser("testU", "testP");
		User user = new User("testU", "testP");
		
		Creator.deleteCreator(new Creator(user));
		
		assertTrue(Creator.registerCreator(user, "123456789", "123456789"), "Unable to register creator!");
		
		assertTrue(Creator.isCreator(user), "User is not creator!");
		
		Creator creator = new Creator(user);
		
		assertEquals(0, creator.getTotalPlays(), "Total plays is not 0!");
		
		assertEquals("123456789",creator.getBankRouting(), "Bank routing is wrong!");
		
		assertEquals("123456789",creator.getBankAccountNumber(), "Bank LinkedList<E>mber is wrong!");
		
		LinkedList<Media> media = creator.getCreatedMedia();
		
		assertEquals(0, media.size(), "More media than I created!");
		
		assertTrue(Creator.deleteCreator(creator), "Creator wasn't deleted!");
		
		User.deleteUser(user);
		
	}
	
	@Test
	void testMedia() {
		
		/*public static boolean addMedia(int creatorID,
								   String mediaTitle,
								   MediaType type,
								   File mediaFile)
		 */
		
		User.registerNewUser("testU", "testP");
		User user = new User("testU", "testP");
		
		Creator.deleteCreator(new Creator(user));
		
		Creator.registerCreator(user, "123456789", "123456789");
		
		Creator creator = new Creator(user);
		
		Media media = new Media("The Most Successful Pirate");
		
		Media.deleteMedia(media);
				
		assertTrue(Media.addMedia(creator.getID(), "The Most Successful Pirate", Media.TYPE_VIDEO, new File("pirate.mp4")), "Media not created!");
		
		media = new Media("The Most Successful Pirate");
		
		File mediaFile = media.getMediaFile();
		
		try {
			Desktop.getDesktop().open(mediaFile);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		assertEquals(Media.TYPE_VIDEO, media.getMediaType());
		
		Calendar calendar = Calendar.getInstance();
		
		assertEquals(new Date(calendar.getTimeInMillis()).toString(), media.getLastOpened().toString());
		
		media.setLastOpened();
		
		assertEquals(new Date(calendar.getTimeInMillis()).toString(), media.getLastOpened().toString());

		assertEquals(new Date(calendar.getTimeInMillis()).toString(), media.getDateCreated().toString());
		
		assertEquals(creator.getID(), media.getCreatorID());
		
		assertEquals(0, media.getMediaViews());
		
		media.addView();
		
		assertEquals(1, media.getMediaViews());
		
	}

	@Test
	void testAdmin() {
		
		User.registerNewUser(Admin.ADMIN_USERNAME, Admin.ADMIN_PASSWORD);
		User adminUser = new User(Admin.ADMIN_USERNAME, Admin.ADMIN_PASSWORD);
		Creator.registerCreator(adminUser, "0", "0");
		
		assertTrue(Admin.isAdmin(adminUser), "Admin user is not admin!");
		
		Admin admin = new Admin(adminUser);
		
		for (Creator creator : admin.getAllCreators()) {
			
			Creator.deleteCreator(creator);
			
		}
		
		User.deleteUser(adminUser);
		
	}
	
}
