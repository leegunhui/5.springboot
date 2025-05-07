package com.korea.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korea.user.model.UserEntity;

@Repository

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	UserEntity findByEmail(String email);

	Optional<UserEntity> findById(int id);

	void deleteById(int id);
}
