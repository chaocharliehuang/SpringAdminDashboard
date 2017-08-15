package com.chaocharliehuang.admindashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chaocharliehuang.admindashboard.models.Role;
import com.chaocharliehuang.admindashboard.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	List<User> findAllByRolesContaining(Role role);
}