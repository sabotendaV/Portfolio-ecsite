package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProductService {
	 @Autowired
	    private productRepository productRepository;

	    public List<Product> findAll() {
	        return productRepository.findAll();
	    }

	    public List<Product> findByCategory(String category) {
	        return productRepository.findByCategory(category);
	    }

	    public List<Product> findNewArrivals(int limit) {
	        return productRepository.findNewArrivals(limit);
	    }

	    public Product findById(long id) {
	        return productRepository.findById(id);
	    }
}
