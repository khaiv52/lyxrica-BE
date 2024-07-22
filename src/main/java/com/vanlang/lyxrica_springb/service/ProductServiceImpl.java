package com.vanlang.lyxrica_springb.service;

import com.vanlang.lyxrica_springb.exception.ProductException;
import com.vanlang.lyxrica_springb.model.Category;
import com.vanlang.lyxrica_springb.model.Product;
import com.vanlang.lyxrica_springb.model.Rating;
import com.vanlang.lyxrica_springb.model.Review;
import com.vanlang.lyxrica_springb.repository.CategoryRepository;
import com.vanlang.lyxrica_springb.repository.OrderItemRepository;
import com.vanlang.lyxrica_springb.repository.ProductRepository;
import com.vanlang.lyxrica_springb.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final OrderItemRepository orderItemRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository, OrderItemRepository orderItemRepository, OrderItemRepository orderItemRepository1) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.orderItemRepository = orderItemRepository1;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);
            topLevel = categoryRepository.save(topLevelCategory);
        }

        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());

        if (secondLevel == null) {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setLevel(2);
            secondLevelCategory.setParentCategory(topLevel);
            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());

        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setLevel(3);
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSizes());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreateAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        if (!productRepository.existsById(productId)) {
            throw new ProductException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
        return "Product deleted successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);

        product.setQuantity(req.getQuantity() != 0 ? req.getQuantity() : product.getQuantity());
        product.setBrand(req.getBrand());
        product.setImageUrl(req.getImageUrl());
        product.setPrice(req.getPrice());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setCreateAt(LocalDateTime.now());

        // Cập nhật category
        if (req.getCategory() != null) {
            product.setCategory(req.getCategory());
        }

        // Cập nhật sizes
        product.getSizes().clear();
        product.getSizes().addAll(req.getSizes());

        // Cập nhật ratings
        if (req.getRatings() != null) {
            product.getRatings().clear();
            for (Rating rating : req.getRatings()) {
                rating.setProduct(product);
                product.getRatings().add(rating);
            }
        }

        // Cập nhật reviews
        if (req.getReviews() != null) {
            product.getReviews().clear();
            for (Review review : req.getReviews()) {
                review.setProduct(product);
                product.getReviews().add(review);
            }
        }

        product.setNumRatings(req.getNumRatings());

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new ProductException("Product not found with id - " + id);
    }

    @Override
    public List<Product> findProductByCategoryId(String category) throws ProductException {
        return List.of();
    }

    //    Hàm lọc sản phẩm
    @Override
    public Page<Product> getALlProducts(String category, String parentCategoryName, List<String> colors, List<String> sizes, Double minPrice, Double maxPrice, Double minDiscount, String sort, String stock, Integer pageNumber, int pageSize) throws ProductException {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10; // giá trị mặc định nếu pageSize không hợp lệ
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Lấy tất cả các sản phẩm theo tiêu chí lọc
        List<Product> products = productRepository.filterProducts(category, parentCategoryName, minPrice, maxPrice, minDiscount, sort);

        // Lọc sản phẩm theo màu sắc
        if (colors != null && !colors.isEmpty()) {
            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }

        // Lọc sản phẩm theo kích thước
        if (sizes != null && !sizes.isEmpty()) {
            products = products.stream().filter(p -> sizes.stream().anyMatch(s -> p.getSizes().stream().anyMatch(size -> size.getName().equalsIgnoreCase(s)))).collect(Collectors.toList());
        }

        // Lọc sản phẩm theo trạng thái tồn kho
        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        // Kiểm tra và điều chỉnh startIndex và endIndex để tránh lỗi IllegalArgumentException
        if (startIndex > endIndex) {
            startIndex = endIndex;
        }

        List<Product> pageContent = products.subList(startIndex, endIndex);

        return new PageImpl<>(pageContent, pageable, products.size());
    }


    // Hàm tìm số lượng sản phẩm bán chạy nhất
    @Override
    public List<Product> getTopSellingProducts(int limit) throws ProductException {
        List<Object[]> results = orderItemRepository.findTopSellingProducts();
        return results.stream().limit(limit).map(result -> (Product) result[0]).collect(Collectors.toList());
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> listProducts = productRepository.findAll();
        return listProducts;
    }
}
