package com.token.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.token.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	@Query(value = "select B.* from user_role_mapping A "
			+ "inner join role_test B on A.role_id = B.id "
			+ "where A.user_id=:userId ", nativeQuery = true)
	public List<Role> findRoleByUserId(@Param("userId") long userId);

}
