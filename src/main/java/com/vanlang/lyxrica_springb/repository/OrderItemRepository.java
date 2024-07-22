package com.vanlang.lyxrica_springb.repository;

import com.vanlang.lyxrica_springb.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi.product, SUM(oi.quantity) as totalQuantity " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product " +
            "ORDER BY totalQuantity DESC"
    )
    List<Object[]> findTopSellingProducts();
}
