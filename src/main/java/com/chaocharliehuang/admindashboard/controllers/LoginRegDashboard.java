package com.chaocharliehuang.admindashboard.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chaocharliehuang.admindashboard.models.*;
import com.chaocharliehuang.admindashboard.services.*;
import com.chaocharliehuang.admindashboard.validator.UserValidator;

@Controller
public class LoginRegDashboard {

	private UserService userService;
	private UserValidator userValidator;
	
	public LoginRegDashboard(UserService userService, UserValidator userValidator) {
		this.userService = userService;
		this.userValidator = userValidator;
	}
	
	@GetMapping("/")
	public String index(
			@Valid @ModelAttribute("user") User user,
			@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout,
			Model model) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid credentials; please try again.");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "Logout successful!");
		}
		return "loginregistration.jsp";
	}
	
	@PostMapping("/registration")
	public String registration(
			@Valid @ModelAttribute("user") User user,
			BindingResult result,
			Model model) {
		userValidator.validate(user, result);
	    if (result.hasErrors()) {
	        return "loginregistration.jsp";
	    } else {
	    		Role adminRole = userService.getRoleById(Long.valueOf(2));
	    		List<User> admins = userService.getAllUsersByRole(adminRole);
	    		if (admins.isEmpty()) {
	    			userService.saveUserWithAdminRole(user);
	    		} else {
	    			userService.saveWithUserRole(user);
	    		}
    			return "redirect:/";
	    }
	}
	
	@GetMapping("/dashboard")
	public String home(Principal principal, Model model) {
		String username = principal.getName();
		model.addAttribute("currentUser", userService.findByUsername(username));
		model.addAttribute("lastLogin", new Date());
		model.addAttribute("adminRole", userService.getRoleById(Long.valueOf(2)));
		return "dashboard.jsp";
	}
	
	@GetMapping("/admin")
	public String adminPage(Principal principal, Model model) {
		String username = principal.getName();
		model.addAttribute("currentUser", userService.findByUsername(username));
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("adminRole", userService.getRoleById(Long.valueOf(2)));
		return "adminPage.jsp";
	}
	
	@GetMapping("/admin/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/admin/makeadmin/{id}")
	public String makeAdmin(@PathVariable("id") Long id) {
		User user = userService.getUserById(id);
		List<Role> roles = user.getRoles();
		Role adminRole = userService.getRoleById(Long.valueOf(2));
		roles.add(adminRole);
		user.setRoles(roles);
		userService.updateUser(user);
		return "redirect:/admin";
	}
}
