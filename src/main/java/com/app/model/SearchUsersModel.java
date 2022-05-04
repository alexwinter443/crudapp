package com.app.model;

/*
 * Alex vergara
 * 5/2/2022
 */
/**
 * RowMapper for mapping Search Product Rows
 */
public class SearchUsersModel {
	// property for easier searching
	private String searchTerm;
	
	// getters and setters
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
}
