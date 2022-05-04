package com.app.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.app.model.UserEntity;

/*
 * Alex Vergara
 * 5/1/2022
 */

 /**
  * CRUD repository handles most of the operations
  * Interface for generic CRUD operations on a repository for a specific type.
  * <model,id type of model)
  */
@Component
public interface UsersRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);
}
