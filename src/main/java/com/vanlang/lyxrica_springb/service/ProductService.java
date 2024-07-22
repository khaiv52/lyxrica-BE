package com.vanlang.lyxrica_springb.service;

import com.vanlang.lyxrica_springb.exception.ProductException;
import com.vanlang.lyxrica_springb.model.Product;
import com.vanlang.lyxrica_springb.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product req) throws ProductException;

    public Product findProductById(Long id) throws ProductException;

    public List<Product> findProductByCategoryId(String category) throws ProductException;

    Page<Product> getALlProducts(String category, String parentCategoryName, List<String> colors, List<String> sizes, Double minPrice, Double maxPrice, Double minDiscount, String sort, String stock, Integer pageNumber, int pageSize) throws ProductException;

    public List<Product> getTopSellingProducts(int limit) throws ProductException;

    public List<Product> findAllProducts();
}
