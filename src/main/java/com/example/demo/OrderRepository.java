package com.example.demo;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;

    // 注文を保存してIDを返す
    public long save(Order order) {
        String sql = "INSERT INTO orders (user_id, total_price, tax_amount, shipping_fee, grand_total, " +
                     "status, last_name, first_name, postal_code, prefecture, address1, address2, phone, payment_method) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, order.getUserId());
            ps.setInt(2, order.getTotalPrice());
            ps.setInt(3, order.getTaxAmount());
            ps.setInt(4, order.getShippingFee());
            ps.setInt(5, order.getGrandTotal());
            ps.setString(6, order.getStatus());
            ps.setString(7, order.getLastName());
            ps.setString(8, order.getFirstName());
            ps.setString(9, order.getPostalCode());
            ps.setString(10, order.getPrefecture());
            ps.setString(11, order.getAddress1());
            ps.setString(12, order.getAddress2());
            ps.setString(13, order.getPhone());
            ps.setString(14, order.getPaymentMethod());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    // 注文明細を保存
    public void saveOrderItems(long orderId, List<Cartitem> cartItems) {
        String sql = "INSERT INTO order_items (order_id, product_id, product_name, price, quantity) VALUES (?, ?, ?, ?, ?)";
        for (Cartitem item : cartItems) {
            jdbcTemplate.update(sql,
                orderId,
                item.getProductId(),
                item.getProductName(),
                item.getPrice(),
                item.getQuantity()
            );
        }
    }
}
