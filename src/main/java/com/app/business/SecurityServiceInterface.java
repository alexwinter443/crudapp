package com.app.business;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.app.model.UserEntity;

/*
 * Elijah Olmos and Alex vergara
 * Milestone
 * 02/02/2022
 */

 /**
  * Interface for the SecurityService
  */
public interface SecurityServiceInterface {

	public void test();
	public UserEntity registerUser(UserEntity usermasodel, HttpServletResponse response) throws Exception;
	boolean isAuthenticated(UserEntity loginModel, String username, String password);
	public UserEntity getByUsername(UserEntity userModel) throws Exception;
	public List<UserEntity> getAllUsers() throws Exception;
	boolean deleteOne(Long id) throws Exception;
	UserEntity updateOne(Long idToUpdate, UserEntity updateUser) throws Exception;
	public List<UserEntity> searchUsers(String searchTerm);
}
