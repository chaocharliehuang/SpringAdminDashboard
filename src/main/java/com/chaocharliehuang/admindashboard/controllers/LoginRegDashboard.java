package com.chaocharliehuang.admindashboard.controllers;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		    userService.saveWithUserRole(user);
		    return "redirect:/";
	    }
	}
	
	@GetMapping("/dashboard")
	public String home(Principal principal, Model model) {
		String email = principal.getName();
		model.addAttribute("currentUser", userService.findByEmail(email));
		model.addAttribute("lastLogin", new Date());
		return "dashboard.jsp";
	}
}
