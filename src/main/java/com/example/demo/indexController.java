package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class indexController {
	@GetMapping("/index")
    public String index(Model model,HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		 model.addAttribute("loginUser",loginUser);
		 
		 Integer cartCount = (Integer) session.getAttribute("cartCount");
		 model.addAttribute("cartCount",cartCount != null ? cartCount : 0);
        return "index";
	}     
}

