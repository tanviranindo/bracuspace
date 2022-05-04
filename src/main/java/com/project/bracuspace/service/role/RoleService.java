package com.project.bracuspace.service.role;

import com.project.bracuspace.model.Role;
import com.project.bracuspace.repository.RoleRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Cacheable("cache.allRoles")
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Cacheable(value = "cache.roleByName", key = "#name", unless = "#result == null")
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Cacheable(value = "cache.roleById", key = "#id", unless = "#result == null")
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @CacheEvict(value = {"cache.allRoles", "cache.roleByName", "cache.roleById"}, allEntries = true)
    public void save(Role role) {
        roleRepository.save(role);
    }

    public boolean checkIfRoleNameIsTaken(List<Role> allRoles, Role role, Role persistedRole) {
        boolean roleNameAlreadyExists = false;
        for (Role r : allRoles) {
            if (!role.getName().equals(persistedRole.getName()) && role.getName().equals(r.getName())) {
                roleNameAlreadyExists = true;
                break;
            }
        }
        return roleNameAlreadyExists;
    }
}
