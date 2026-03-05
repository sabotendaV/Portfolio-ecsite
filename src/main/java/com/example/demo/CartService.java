package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
	@Autowired
    private CartRepository cartRepository;

    // カートに追加
    public void addItem(long userId, long productId, int quantity) {
        cartRepository.addItem(userId, productId, quantity);
    }

    // カート一覧取得
    public List<Cartitem> getCartItems(long userId) {
        return cartRepository.findByUserId(userId);
    }

    // カートアイテム削除
    public void deleteItem(long cartItemId, long userId) {
        cartRepository.deleteItem(cartItemId, userId);
    }

    // カート件数取得
    public int getCartCount(long userId) {
        return cartRepository.countByUserId(userId);
    }

    // 合計金額計算
    public int calcTotal(List<Cartitem> cartItems) {
        return cartItems.stream()
            .mapToInt(item -> item.getPrice() * item.getQuantity())
            .sum();
    }

    // カート全削除（注文完了後）
    public void clearCart(long userId) {
        cartRepository.deleteAllByUserId(userId);
    }
}
