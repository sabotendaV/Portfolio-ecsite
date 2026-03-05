package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;
@Controller
public class ProductDetail {
	 @Autowired
	    private ProductService productService;

	    @GetMapping("/products/{id}")
	    public String productDetail(
	            @PathVariable long id,
	            Model model,
	            HttpSession session) {

	        // ログインユーザー取得
	        User loginUser = (User) session.getAttribute("loginUser");
	        model.addAttribute("loginUser", loginUser);

	        // カート件数
	        int cartCount = 0;
	        if (loginUser != null) {
	            cartCount = (int) session.getAttribute("cartCount");
	        }
	        model.addAttribute("cartCount", cartCount);

	        // 商品詳細取得
	        Product product = productService.findById(id);
	        model.addAttribute("product", product);

	        return "product_detail";
	    }
}
