package com.udacity.ecommerce.model.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.ecommerce.model.persistence.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	Optional<User> findOptionalByUsername(String username);

}
