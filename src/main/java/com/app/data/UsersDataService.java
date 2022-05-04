package com.app.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.app.model.UserEntity;
import com.app.model.UsersMapper;
import com.app.util.DatabaseException;

/*
 * Alex Vergara
 * 5/1/2022
 */

/*
 * Three stereotypes are typical for Spring beans
 * @Component is a generic stereotype for any Spring-managed component.
 * @Service annotates classes at the Service layer
 * @Repository annotates classes at the persistence layer, which will act as a database repository
 * @Service and @Repository are special cases of @Component
 * They are technically the same, but we use them for different purposes
 * */
@Service
public class UsersDataService implements UsersDataAccessInterface<UserEntity> {
	
	// Repository uses the CRUD repository for data manipulation
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	// non default constructor
	public UsersDataService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	// find by username defined in interface
	@Override
	public UserEntity findByUsername(String username) {
		try {
			System.out.println("In users data service username is: " + username);
			// goes to users repository data class
			return usersRepository.findByUsername(username);
		} catch (Exception e) {
			// Log stack trace & throw custom exception to caller
			e.printStackTrace();
			throw new DatabaseException(e, "Database exception");
		}
	}
	
	/**
	 * get all products
	 * 
	 * @return List<ProductModel>
	 */
	// get all products
	@Override
	public List<UserEntity> getAllUsers() {
		try {
			// query for all properties of all products
			List<UserEntity> products = jdbcTemplate.query("SELECT * FROM users", new UsersMapper());
			return products;
		} catch (Exception e) {
			// Log stack trace & throw custom exception to caller
			e.printStackTrace();
			throw new DatabaseException(e, "Database exception");
		}
	}

}
