package com.vanlang.lyxircaspb.repository;

import com.vanlang.lyxircaspb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);

    Role findRoleById(long value);
}
