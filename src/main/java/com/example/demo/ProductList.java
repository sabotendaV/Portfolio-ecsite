package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
@Controller
public class ProductList {
	@Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String productList(
            @RequestParam(required = false) String category,
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

        // 商品取得（カテゴリ指定があれば絞り込み）
        List<Product> products;
        if (category != null && !category.isEmpty()) {
            products = productService.findByCategory(category);
            model.addAttribute("currentCategory", category);
        } else {
            products = productService.findAll();
            model.addAttribute("currentCategory", "all");
        }

        model.addAttribute("products", products);
        model.addAttribute("productCount", products.size());

        return "product_list";
    }
}
