package com.app.business;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.app.data.UserDataService;
import com.app.model.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Alex Vergara
 * 5/1/2022
 */
/**
 * Service for handling all security related interactions
 */
@Service
public class SecurityService implements SecurityServiceInterface{
	
	@Autowired
	UserDataService usersDAO;
	
	/**
	 * check if a user is authenticated 
	 * @param loginModel the user's login credentials
	 * @param username the user's username
	 * @param password the user's password
	 */
	@Override
	public boolean isAuthenticated(UserEntity loginModel, String username, String password) {
	
		// Check to see if the login matches any of the valid logins
		int result = usersDAO.getUsersByUsername(loginModel.getUsername(), loginModel.getPassword());
		
		System.out.println("in the is authenticated method in the security service.");
		
		// successful login ; ie returns true
		if(result > 0) {
			System.out.println("number of people with this username: " + result);
			return true;
		}
		// checks for registered user using cookies
		else if(loginModel.getUsername().equals(username) && loginModel.getPassword().equals(password)) {
			return true;
		}
		// user not found
		else {
			return false;
		}
	}
	
	public List<UserEntity> searchUsers(String searchTerm) {
		return usersDAO.searchProducts(searchTerm);
	}
	

	@Override
	public void test() {
		System.out.println("We are running and using the SecurityService");
		
	}

	/**
	 * add a user to the database, registering them with the application
	 * @param userModel the user to be registered
	 * @param response the HttpServletResponse
	 * @throws Exception 
	 */
	@Override
	public UserEntity registerUser(UserEntity userModel, HttpServletResponse response) throws Exception {
		
		// call add user method
		usersDAO.addUser(userModel);
		
		UserEntity user = usersDAO.findByUsername(userModel.getUsername());
		
		System.out.println("In the security service registering a user with user entity");
		
		// return user model
		// UserModel usr1 = new UserModel(userModel.getUsername(), userModel.getPassword());
		
		// return usr1;
		return user;
	}
	
	/**
	 * retrieve a user from the database by their username
	 * @param userModel the UserModel of the user to be retrieved
	 * @throws Exception 
	 */
	@Override
	public UserEntity getByUsername(UserEntity userModel) throws Exception {
		return usersDAO.findByUsername(userModel.getUsername());
	}

	/**
	 * retrieve all users from the database
	 * @return a List of all users
	 * @throws Exception 
	 */
	@Override
	public List<UserEntity> getAllUsers() throws Exception {
		List<UserEntity> users = usersDAO.getAllUsers();
		return users;
	}
	
	/**
	 * Delete a single user from the db by their ID
	 * @param id the ID of the user to be deleted
	 * @throws Exception 
	 */
	@Override
	public boolean deleteOne(Long id) throws Exception {
		return usersDAO.deleteOne(id);
	}

	/**
	 * Update a single user in the database
	 * @param idToUpdate the ID of the user to be updated
	 * @param updateUser the UserModel of the user to be updated
	 * @throws Exception 
	 */
	@Override
	public UserEntity updateOne(Long idToUpdate, UserEntity updateUser) throws Exception {
		return usersDAO.updateOne(idToUpdate, updateUser);
	}
}
