package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Role;
import com.reksoft.holiday.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public Role findRoleByName(String name){
        Role role = roleRepository.findByRolename(name);
        return role;
    }

    @Override
    public boolean saveAndFlush(Role role) {
        Role dbRole = findRoleByName(role.getRolename());
        if (dbRole != null) {
            return false;
        }
        roleRepository.saveAndFlush(role);
        return  true;
    }
}
