package com.vanlang.lyxrica_springb.repository;

import com.vanlang.lyxrica_springb.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
