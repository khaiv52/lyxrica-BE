package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.model.*;
import com.vanlang.lyxircaspb.repository.*;
import com.vanlang.lyxircaspb.request.CreateProductRequest;
import com.vanlang.lyxircaspb.request.UpdateProductRequest;
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
    private final SectionRepository sectionRepository;
    private final CategoryItemRepository categoryItemRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository, OrderItemRepository orderItemRepository, SectionRepository sectionRepository, CategoryItemRepository categoryItemRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.orderItemRepository = orderItemRepository;
        this.sectionRepository = sectionRepository;
        this.categoryItemRepository = categoryItemRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {

        // Kiểm tra và tạo danh mục cấp 1 nếu không tồn tại
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        if (topLevel == null) {
            Category newTopLevel = new Category();
            newTopLevel.setName(req.getTopLevelCategory());
            topLevel = categoryRepository.save(newTopLevel);
        }

        // Kiểm tra và tạo danh mục cấp 2 nếu không tồn tại
        Section secondLevel = sectionRepository.findByNameAndParentCategoryName(req.getSecondLevelCategory(), topLevel.getName());

        if (secondLevel == null) {
            Section newSecondLevel = new Section();
            newSecondLevel.setName(req.getSecondLevelCategory());
            newSecondLevel.setParentCategory(topLevel);
            secondLevel = sectionRepository.save(newSecondLevel);
        }

        CategoryItem thirdLevel = categoryItemRepository.findByNameAndParentSectionName(req.getThirdLevelCategory(), secondLevel.getName());

        if (thirdLevel == null) {
            CategoryItem newThirdLevel = new CategoryItem();
            newThirdLevel.setName(req.getThirdLevelCategory());
            newThirdLevel.setParentSection(secondLevel);
            thirdLevel = categoryItemRepository.save(newThirdLevel);
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

        // Log các giá trị để kiểm tra
        System.out.println("Finding top-level category: " + req.getTopLevelCategory());
        System.out.println("Top Level Category found: " + topLevel);

        System.out.println("Finding second-level section: " + req.getSecondLevelCategory() + " with parent category: " + topLevel.getName());
        System.out.println("Second Level Section found: " + secondLevel);

        System.out.println("Finding third-level item: " + req.getThirdLevelCategory() + " with parent section: " + secondLevel.getName());
        System.out.println("Third Level Item found: " + thirdLevel);

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
    public Product updateProduct(Long productId, UpdateProductRequest req) throws ProductException {
        // Tìm sản phẩm hiện tại
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

        // Cập nhật các cấp danh mục
        if (req.getToplevelCategory() != null) {
            Category topLevel = categoryRepository.findByName(req.getToplevelCategory());
            if (topLevel == null) {
                topLevel = new Category();
                topLevel.setName(req.getToplevelCategory());
                topLevel = categoryRepository.save(topLevel);
            }

            Section secondLevel = sectionRepository.findByNameAndParentCategoryName(req.getSecondLevelCategory(), topLevel.getName());
            if (secondLevel == null) {
                secondLevel = new Section();
                secondLevel.setName(req.getSecondLevelCategory());
                secondLevel.setParentCategory(topLevel);
                secondLevel = sectionRepository.save(secondLevel);
            }

            CategoryItem thirdLevel = categoryItemRepository.findByNameAndParentSectionName(req.getThirdLevelCategory(), secondLevel.getName());
            if (thirdLevel == null) {
                thirdLevel = new CategoryItem();
                thirdLevel.setName(req.getThirdLevelCategory());
                thirdLevel.setParentSection(secondLevel);
                thirdLevel = categoryItemRepository.save(thirdLevel);
            }

            product.setCategory(thirdLevel);
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
    public List<Product> findProductByCategoryName(String categoryName) throws ProductException {
        try {
            return productRepository.findProductsByCategoryName(categoryName);
        } catch (Exception e) {
            throw new ProductException("Error occurred while fetching products by category: " + e.getMessage(), e);
        }
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

    @Override
    public List<Product> getLastestProduct() throws ProductException {
        return productRepository.findLatestProducts();
    }

    @Override
    public Page<Product> findProductBySearchText(String searchText, int pageSize, int pageNumber) throws ProductException {
        try {
            // Tạo đối tượng Pageable để quản lý phân trang
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            // Lấy tất cả các sản phẩm theo tiêu chí lọc
            Page<Product> products = productRepository.findProductBySearchText(searchText, pageable);
            return products;
        } catch (Exception e) {
            throw new ProductException("Error occurred while fetching products by searchText");
        }
    }


}
