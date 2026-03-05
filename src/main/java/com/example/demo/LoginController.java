package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	//ログイン
	@GetMapping("/login")
		public String showLogin() {
			return "login";
	}
	//ログアウト用
	@GetMapping("/logout")
		public String Logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@PostMapping("/login")
		public String Login(@ModelAttribute User form, HttpSession session){
			try {
				// メールアドレスでユーザーを検索
				User user = userService.findByEmail(form.getEmail());
				
				//パスワード確認
				if(passwordEncoder.matches(form.getPassword(), user.getPassword())) {
					//一致したらトップページへ
					session.setAttribute("loginUser", user);
					session.setAttribute("cartCount", 0);
					return "redirect:/index";
				}else {
					//パスワードが違う場合、ログインページへ
					return "redirect:/login?error";
				}
			}catch (Exception e) {
		        // ユーザーが見つからない → ログインページに戻る
		        return "redirect:/login?error";
		    }			
			
		}	
	} 
	

