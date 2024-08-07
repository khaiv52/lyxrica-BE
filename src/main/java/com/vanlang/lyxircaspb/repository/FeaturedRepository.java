package com.vanlang.lyxircaspb.repository;

import com.vanlang.lyxircaspb.model.Featured;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedRepository extends JpaRepository<Featured, Long> {
}
