package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepositories) {
        this.roleRepository = roleRepositories;
    }

    @Transactional
    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
    @Transactional(readOnly = true)
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
