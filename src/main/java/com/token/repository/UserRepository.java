package com.token.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.token.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query(value = "select * from user_test where user_name=:userName and passwd =:password ", nativeQuery = true)
	public Optional<User> findUserByUsernameAndPassword(@Param("userName") String userName, @Param("password") String password);
	public User findByUserId(String userId);
}
