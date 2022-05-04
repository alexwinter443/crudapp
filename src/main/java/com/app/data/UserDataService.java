package com.app.data;

import java.util.List;

import javax.sql.DataSource;

import com.app.model.UserEntity;
import com.app.model.UserModelMapper;
import com.app.model.UsersMapper;
import com.app.util.DatabaseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDataService {

	@Autowired
	DataSource datasource;
	JdbcTemplate jdbcTemplate;

	// constructor
	public UserDataService(DataSource datasource) {
		this.datasource = datasource;
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	
	public List<UserEntity> searchProducts(String searchTerm) {
		try {
			// SQL query with jdbc for searching product names
			return jdbcTemplate.query("SELECT * FROM users WHERE USERNAME LIKE ?",
					new UsersMapper(),
					new Object[] { "%" + searchTerm + "%" });
		} catch (Exception e) {
			// Log stack trace & throw custom exception to caller
			e.printStackTrace();
			throw new DatabaseException(e, "Database exception");
		}
	}
	

	/**
	 * get a single user by their username and password
	 * 
	 * @param username The username of the user to be retrieved
	 * @param password The password of the user to be retrieved
	 * @return int
	 */
	@SuppressWarnings("deprecation")
	public int getUsersByUsername(String username, String password) {
		try {
			System.out.println("OLD user data service getting user by username");
			// sql query
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users where username = ? and password = ?",
					new Object[] { username, password }, Integer.class);
		} catch (Exception e) {
			System.out.println("something went wrong");
		}
		return 0;
	}

	/**
	 * get a single user by their username
	 * 
	 * @param username The username of the user to be retrieved
	 * @return UserEntity
	 * @throws Exception 
	 */
	// used for login method
	public UserEntity findByUsername(String username) throws Exception {
		try {
			System.out.println("OLD user data service find by username:" + username);
			// query but easier with jdbc
			UserEntity result = jdbcTemplate.queryForObject(
					"SELECT * FROM users WHERE USERNAME = ?",
					new UsersMapper(),
					new Object[] { username });
			// return the user
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception message");
		}
	}

	/**
	 * Add a user to the database
	 * 
	 * @param user UserModel of the user to be added
	 * @return int
	 * @throws Exception 
	 */
	public int addUser(UserEntity user) throws Exception {
		try {
			System.out.println("OLD user data service add user method");
			return jdbcTemplate.update(
					"insert into users (ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, PHONE, ROLE) VALUES(?,?,?,?,?,?,?,?)",
					0,
					user.getUsername(),
					user.getPassword(),
					user.getFirstName(),
					user.getLastName(),
					user.getEmail(),
					user.getPhone(),
					"NORMAL");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception message");
		}
	}

	/**
	 * get all users from the DB
	 * 
	 * @return List<UserModel>
	 * @throws Exception 
	 */
	// NOTE CHANGED FROM USERMODEL TO USERENTITY
	public List<UserEntity> getAllUsers() throws Exception {
		try {
			System.out.println("OLD user data service get all users");
			// query but easier with jdbc
			List<UserEntity> result = jdbcTemplate.query("SELECT * FROM users", new UserModelMapper());
			// return the user
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception message");
		}
	}

	/**
	 * delete a user from the database by their id
	 * 
	 * @param id The id of the user to be deleted
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean deleteOne(Long id) throws Exception {
		try {
			int updateResult = jdbcTemplate.update(
					"DELETE FROM users WHERE ID = ?",
					new Object[] { id });
			System.out.println("user data service: Trying to delete" + id);
			System.out.println("user data service: result: " + updateResult);
			return (updateResult > 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception message");
		}
	}

	/**
	 * update a single user in the database
	 * 
	 * @param idToUpdate The id of the user to be updated
	 * @param updateUser UserModel of the user to be updated
	 * @return UserModel
	 * @throws Exception 
	 */
	// update a user by id with new user details
	public UserEntity updateOne(Long idToUpdate, UserEntity updateUser) throws Exception {
		try {
			// sql query with injection protection
			int result = jdbcTemplate.update(
					"UPDATE users SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, PHONE = ? WHERE ID = ?",
					updateUser.getFirstName(),
					updateUser.getLastName(),
					updateUser.getEmail(),
					updateUser.getPhone(),
					idToUpdate);
			if (result > 0) {
				return updateUser;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception message");
		}
	}
}
