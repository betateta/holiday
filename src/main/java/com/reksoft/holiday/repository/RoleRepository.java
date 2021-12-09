package com.reksoft.holiday.repository;

import com.reksoft.holiday.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Integer>{
    Role findByRolename (String roleName);
}
