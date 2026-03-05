package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {
	
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    // カート追加
    public void addItem(long userId, long productId, int quantity) {
        // すでに同じ商品がカートにあれば数量を更新
        String checkSql = "SELECT COUNT(*) FROM cart_items WHERE user_id = ? AND product_id = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, userId, productId);

        if (count > 0) {
            String updateSql = "UPDATE cart_items SET quantity = quantity + ? WHERE user_id = ? AND product_id = ?";
            jdbcTemplate.update(updateSql, quantity, userId, productId);
        } else {
            String insertSql = "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)";
            jdbcTemplate.update(insertSql, userId, productId, quantity);
        }
    }
    
 // ユーザーのカート一覧取得（商品情報も一緒に取得）
    public List<Cartitem> findByUserId(long userId) {
        String sql = "SELECT c.id, c.user_id, c.product_id, c.quantity, " +
                     "p.name AS product_name, p.image_url, " +
                     "CASE WHEN p.on_sale = true THEN p.sale_price ELSE p.price END AS price " +
                     "FROM cart_items c " +
                     "JOIN products p ON c.product_id = p.id " +
                     "WHERE c.user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Cartitem item = new Cartitem();
            item.setId(rs.getLong("id"));
            item.setUserId(rs.getLong("user_id"));
            item.setProductId(rs.getLong("product_id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setProductName(rs.getString("product_name"));
            item.setImageUrl(rs.getString("image_url"));
            item.setPrice(rs.getInt("price"));
            return item;
        }, userId);
    }
    
 // カートアイテム削除
    public void deleteItem(long cartItemId, long userId) {
        String sql = "DELETE FROM cart_items WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, cartItemId, userId);
    }

    // カート件数取得
    public int countByUserId(long userId) {
        String sql = "SELECT COALESCE(SUM(quantity), 0) FROM cart_items WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    // ユーザーのカートを全削除（注文完了後）
    public void deleteAllByUserId(long userId) {
        String sql = "DELETE FROM cart_items WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
}
