package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    // 注文確定処理
    public long placeOrder(Order order, List<Cartitem> cartItems) {
        // 合計金額計算
        int totalPrice = cartService.calcTotal(cartItems);
        int taxAmount  = (int)(totalPrice * 0.1);
        int shippingFee = 500;
        int grandTotal = totalPrice + taxAmount + shippingFee;

        order.setTotalPrice(totalPrice);
        order.setTaxAmount(taxAmount);
        order.setShippingFee(shippingFee);
        order.setGrandTotal(grandTotal);
        order.setStatus("PENDING");

        // 注文を保存してIDを取得
        long orderId = orderRepository.save(order);

        // 注文明細を保存
        orderRepository.saveOrderItems(orderId, cartItems);

        // カートを空にする
        cartService.clearCart(order.getUserId());

        return orderId;
    }
}
