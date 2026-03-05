package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/register")
	public String showRegisterr() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute User form){
		form.setRole("USER");
		userService.register(form);
		return "redirect:/login";
	}
	

}
