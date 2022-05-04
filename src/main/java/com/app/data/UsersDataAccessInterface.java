package com.app.data;

import java.util.List;

/*
 * Elijah Olmos and Alex Vergara
 * 5/2/2022
 */

public interface UsersDataAccessInterface<T> {
	public T findByUsername(String username);
	public List<T> getAllUsers();
}
