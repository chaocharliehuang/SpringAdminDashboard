package com.chaocharliehuang.admindashboard.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chaocharliehuang.admindashboard.models.*;
import com.chaocharliehuang.admindashboard.repositories.*;

@Service
public class UserService {
	
	private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(
    		UserRepository userRepository,
    		RoleRepository roleRepository,
    		BCryptPasswordEncoder bCryptPasswordEncoder) {
    		
    		this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
    
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }    
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public List<User> getAllUsers() {
    		return (List<User>) userRepository.findAll();
    }
    
    public List<User> getAllUsersByRole(Role role) {
    		return userRepository.findAllByRolesContaining(role);
    }
    
    public User getUserById(Long id) {
    		return userRepository.findOne(id);
    }
    
    public void deleteUser(Long id) {
    		userRepository.delete(id);
    }
    
    public void updateUser(User user) {
    		userRepository.save(user);
    }
    
    public Role getRoleById(Long id) {
    		return roleRepository.findOne(id);
    }
}