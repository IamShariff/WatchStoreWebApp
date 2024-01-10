package com.watchhub.watchstore.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.watchhub.watchstore.entity.User;

/**
 * Repository interface for managing user data.
 * Provides CRUD operations for the User entity.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
