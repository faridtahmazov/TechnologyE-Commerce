package com.company.service;

import com.company.model.Role;
import com.company.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    private void save(Role role){
       roleRepository.save(role);
    }

    public Optional<Role> findById(Integer id){
        return roleRepository.findById(id);
    }
}
