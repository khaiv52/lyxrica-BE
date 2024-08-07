package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.DTO.ProductDTO;
import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.mapper.ProductMapper;
import com.vanlang.lyxircaspb.model.Product;
import com.vanlang.lyxircaspb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<Page<ProductDTO>> findProductByCategoryHandler(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String parentCategoryName,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) List<String> size,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) throws ProductException {

        Page<Product> res = productService.getALlProducts(category, parentCategoryName, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
        Page<ProductDTO> productDTOs = res.map(ProductMapper::toDTO);
        System.out.println("Number of products: " + res.getTotalElements());
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }


    @GetMapping("/id/{productId}")
    public ResponseEntity<Product> findProductByHandler(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    // Tìm sản phẩm theo tên danh mục
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDTO>> findProductsByCategory(@PathVariable String categoryName) throws ProductException {
        List<Product> products = productService.findProductByCategoryName(categoryName);
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductMapper::toDTO)
                .toList();
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ProductDTO>> getLatestProducts() throws ProductException {
        List<Product> latestProducts = productService.getLastestProduct();
        List<ProductDTO> productDTOs = latestProducts.stream()
                .map(ProductMapper::toDTO)
                .toList();
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> getAllProductsBySearchText(
            @RequestParam("searchText") String searchText,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) throws ProductException {
        Page<Product> products = productService.findProductBySearchText(searchText, pageSize, pageNumber);
        Page<ProductDTO> productDTOs = products.map(ProductMapper::toDTO);
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }
}
