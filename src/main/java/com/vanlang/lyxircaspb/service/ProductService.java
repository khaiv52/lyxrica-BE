package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.model.Product;
import com.vanlang.lyxircaspb.request.CreateProductRequest;
import com.vanlang.lyxircaspb.request.UpdateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest req);

    String deleteProduct(Long productId) throws ProductException;

    Product updateProduct(Long productId, UpdateProductRequest req) throws ProductException;

    Product findProductById(Long id) throws ProductException;

    List<Product> findProductByCategoryName(String category) throws ProductException;

    Page<Product> getALlProducts(String category, String parentCategoryName, List<String> colors, List<String> sizes, Double minPrice, Double maxPrice, Double minDiscount, String sort, String stock, Integer pageNumber, int pageSize) throws ProductException;

    List<Product> getTopSellingProducts(int limit) throws ProductException;

    List<Product> findAllProducts();

    List<Product> getLastestProduct() throws ProductException;

    Page<Product> findProductBySearchText(String searchText, int pageSize, int pageNumber) throws ProductException;
}
