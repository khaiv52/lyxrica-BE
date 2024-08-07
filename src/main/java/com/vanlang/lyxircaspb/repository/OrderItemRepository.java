package com.vanlang.lyxircaspb.repository;

import com.vanlang.lyxircaspb.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi.product, SUM(oi.quantity) as totalQuantity " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product " +
            "ORDER BY totalQuantity DESC"
    )
    List<Object[]> findTopSellingProducts();
}
