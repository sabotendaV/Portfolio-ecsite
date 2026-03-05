package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {
	@Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    // 購入手続きページ表示
    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("loginUser", loginUser);

        // カートアイテム取得
        List<Cartitem> cartItems = cartService.getCartItems(loginUser.getId());

        // カートが空なら一覧へ
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("cartItems", cartItems);

        // 金額計算
        int totalPrice  = cartService.calcTotal(cartItems);
        int taxAmount   = (int)(totalPrice * 0.1);
        int shippingFee = 500;
        int grandTotal  = totalPrice + taxAmount + shippingFee;

        model.addAttribute("totalPrice",  totalPrice);
        model.addAttribute("taxAmount",   taxAmount);
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("grandTotal",  grandTotal);
        model.addAttribute("cartCount",   session.getAttribute("cartCount"));
        model.addAttribute("order",       new Order());

        return "cheackout";
    }

    // 注文確定処理
    @PostMapping("/order/confirm")
    public String confirmOrder(
            @ModelAttribute Order order,
            HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        // カートアイテム取得
        List<Cartitem> cartItems = cartService.getCartItems(loginUser.getId());
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        // ユーザーIDをセット
        order.setUserId(loginUser.getId());

        // 注文確定（カートも空になる）
        long orderId = orderService.placeOrder(order, cartItems);

        // セッションのカート件数をリセット
        session.setAttribute("cartCount", 0);

        // 注文IDをセッションに保存（完了ページで表示するため）
        session.setAttribute("lastOrderId", orderId);

        return "redirect:/order/complete";
    }

    // 購入完了ページ
    @GetMapping("/order/complete")
    public String orderComplete(Model model, HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        Long orderId = (Long) session.getAttribute("lastOrderId");
        model.addAttribute("orderId",   orderId);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("cartCount", session.getAttribute("cartCount"));

        return "order_complete";
    }
}
