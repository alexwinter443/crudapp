package com.app.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/*
 * Alex Vergara
 * 5/1/2022
 */
/**
 * RowMapper for mapping Product Rows
 */
public class UserModelMapper implements RowMapper<UserEntity>{

	// USER ENTITY model mapper
	@Override
	public UserEntity mapRow(ResultSet resultset, int rowNum) throws SQLException {
		// create new user entity which will be used to set roles
		UserEntity user = new UserEntity(
				resultset.getLong("ID"),
				resultset.getString("USERNAME"),
				resultset.getString("PASSWORD"),
				resultset.getString("FIRST_NAME"),
				resultset.getString("LAST_NAME"),
				resultset.getString("EMAIL"),
				resultset.getString("PHONE"),
				resultset.getString("ROLE")
				);
		
		return user;
	}

}
