package com.vanlang.lyxrica_springb.controller;

import com.vanlang.lyxrica_springb.DTO.ProductDTO;
import com.vanlang.lyxrica_springb.exception.ProductException;
import com.vanlang.lyxrica_springb.mapper.ProductMapper;
import com.vanlang.lyxrica_springb.model.Product;
import com.vanlang.lyxrica_springb.service.ProductService;
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
//
//    @PostMapping("/products")
//    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) throws ProductException {
//        Product createdProduct = productService.createProduct(req);
//        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
//    }
}
