package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class productRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;

    // 全商品取得
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product p = new Product();
            p.setId(rs.getLong("id"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setPrice(rs.getInt("price"));
            p.setOriginalPrice(rs.getInt("original_price"));
            p.setSalePrice(rs.getInt("sale_price"));
            p.setImageUrl(rs.getString("image_url"));
            p.setCategory(rs.getString("category"));
            p.setStock(rs.getInt("stock"));
            p.setNew(rs.getBoolean("is_new"));
            p.setOnSale(rs.getBoolean("on_sale"));
            return p;
        });
    }

    // カテゴリで絞り込み
    public List<Product> findByCategory(String category) {
        String sql = "SELECT * FROM products WHERE category = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product p = new Product();
            p.setId(rs.getLong("id"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setPrice(rs.getInt("price"));
            p.setOriginalPrice(rs.getInt("original_price"));
            p.setSalePrice(rs.getInt("sale_price"));
            p.setImageUrl(rs.getString("image_url"));
            p.setCategory(rs.getString("category"));
            p.setStock(rs.getInt("stock"));
            p.setNew(rs.getBoolean("is_new"));
            p.setOnSale(rs.getBoolean("on_sale"));
            return p;
        }, category);
    }

    // 新着商品取得（件数指定）
    public List<Product> findNewArrivals(int limit) {
        String sql = "SELECT * FROM products WHERE is_new = true LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product p = new Product();
            p.setId(rs.getLong("id"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setPrice(rs.getInt("price"));
            p.setOriginalPrice(rs.getInt("original_price"));
            p.setSalePrice(rs.getInt("sale_price"));
            p.setImageUrl(rs.getString("image_url"));
            p.setCategory(rs.getString("category"));
            p.setStock(rs.getInt("stock"));
            p.setNew(rs.getBoolean("is_new"));
            p.setOnSale(rs.getBoolean("on_sale"));
            return p;
        }, limit);
    }

    // IDで1件取得
    public Product findById(long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Product p = new Product();
            p.setId(rs.getLong("id"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setPrice(rs.getInt("price"));
            p.setOriginalPrice(rs.getInt("original_price"));
            p.setSalePrice(rs.getInt("sale_price"));
            p.setImageUrl(rs.getString("image_url"));
            p.setCategory(rs.getString("category"));
            p.setStock(rs.getInt("stock"));
            p.setNew(rs.getBoolean("is_new"));
            p.setOnSale(rs.getBoolean("on_sale"));
            return p;
        }, id);
    }
}
