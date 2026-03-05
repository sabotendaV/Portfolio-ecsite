package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Cart {
	@Autowired
    private CartService cartService;
	
	@GetMapping("/cart")
    public String cart(Model model,HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		// 未ログインはログインページへ
        if (loginUser == null) {
            return "redirect:/login";        }
		 model.addAttribute("loginUser",loginUser);
		 
		// カートアイテム取得
	        List<Cartitem> cartItems = cartService.getCartItems(loginUser.getId());
	        model.addAttribute("cartItems", cartItems);

	        // 合計金額
	        int totalPrice  = cartService.calcTotal(cartItems);
	        int taxAmount   = (int)(totalPrice * 0.1);
	        int shippingFee = 500;
	        int grandTotal  = totalPrice + taxAmount + shippingFee;

	        model.addAttribute("totalPrice",  totalPrice);
	        model.addAttribute("taxAmount",   taxAmount);
	        model.addAttribute("shippingFee", shippingFee);
	        model.addAttribute("grandTotal",  grandTotal);

	        // カート件数更新
	        int cartCount = cartService.getCartCount(loginUser.getId());
	        session.setAttribute("cartCount", cartCount);
	        model.addAttribute("cartCount", cartCount);

	        return "cart";
	}

	// カートに追加
    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam long productId,
            @RequestParam(defaultValue = "1") int quantity,
            HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");

        // 未ログインはログインページへ
        if (loginUser == null) {
            return "redirect:/login";
        }

        cartService.addItem(loginUser.getId(), productId, quantity);

        // セッションのカート件数を更新
        int cartCount = cartService.getCartCount(loginUser.getId());
        session.setAttribute("cartCount", cartCount);

        return "redirect:/cart";
    }
    
 // カートから削除
    @PostMapping("/cart/delete")
    public String deleteFromCart(
            @RequestParam long cartItemId,
            HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        cartService.deleteItem(cartItemId, loginUser.getId());

        // セッションのカート件数を更新
        int cartCount = cartService.getCartCount(loginUser.getId());
        session.setAttribute("cartCount", cartCount);

        return "redirect:/cart";
    }
}
