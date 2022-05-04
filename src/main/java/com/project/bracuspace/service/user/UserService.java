package com.project.bracuspace.service.user;

import com.project.bracuspace.dto.UserDTO;
import com.project.bracuspace.dto.UserUpdateDTO;
import com.project.bracuspace.model.Role;
import com.project.bracuspace.model.User;
import com.project.bracuspace.repository.UserRepository;
import com.project.bracuspace.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.Collections.emptyList;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Cacheable(value = "cache.allUsers")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Cacheable(value = "cache.allUsersPageable")
    public Page<User> findAllPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Cacheable(value = "cache.userByEmail", key = "#email", unless = "#result == null")
    public User findByEmail(String email) {
        return userRepository.findByEmailEagerly(email);
    }

    @Cacheable(value = "cache.userById", key = "#id", unless = "#result == null")
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Page<User> findByIdPageable(Long id, Pageable pageRequest) {
        Optional<User> user = userRepository.findById(id);
        List<User> users = user.map(Collections::singletonList).orElse(emptyList());
        return new PageImpl<>(users, pageRequest, users.size());
    }

    public User findByEmailAndIdNot(String email, Long id) {
        return userRepository.findByEmailAndIdNot(email, id);
    }

    public User findByUsernameAndIdNot(String username, Long id) {
        return userRepository.findByUsernameAndIdNot(username, id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByIdEagerly(Long id) {
        return userRepository.findByIdEagerly(id);
    }

    @Cacheable(value = "cache.allUsersEagerly")
    public List<User> findAllEagerly() {
        return userRepository.findAllEagerly();
    }

    @Cacheable(value = "cache.byNameContaining")
    public Page<User> findByNameContaining(String name, Pageable pageable) {
        return userRepository.findByNameContainingOrderByIdAsc(name, pageable);
    }

    @Cacheable(value = "cache.bySurnameContaining")
    public Page<User> findBySurnameContaining(String surname, Pageable pageable) {
        return userRepository.findBySurnameContainingOrderByIdAsc(surname, pageable);
    }

    @Cacheable(value = "cache.byUsernameContaining")
    public Page<User> findByUsernameContaining(String username, Pageable pageable) {
        return userRepository.findByUsernameContainingOrderByIdAsc(username, pageable);
    }

    @Cacheable(value = "cache.byEmailContaining")
    public Page<User> findByEmailContaining(String email, Pageable pageable) {
        return userRepository.findByEmailContainingOrderByIdAsc(email, pageable);
    }

    @Transactional
    @CacheEvict(value = {"cache.allUsersPageable", "cache.allUsers", "cache.userByEmail", "cache.userById", "cache.allUsersEagerly", "cache.byNameContaining", "cache.bySurnameContaining", "cache.byUsernameContaining", "cache.byEmailContaining"}, allEntries = true)
    public void save(User user) {
        userRepository.save(user);
    }

    @CacheEvict(value = {"cache.allUsersPageable", "cache.allUsers", "cache.userByEmail", "cache.userById", "cache.allUsersEagerly", "cache.byNameContaining", "cache.bySurnameContaining", "cache.byUsernameContaining", "cache.byEmailContaining"}, allEntries = true)
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User createNewAccount(UserDTO UserDTO) {
        User user = new User();
        user.setName(UserDTO.getName());
        user.setSurname(UserDTO.getSurname());
        user.setUsername(UserDTO.getUsername());
        user.setEmail(UserDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(UserDTO.getPassword()));
        user.setRoles(Collections.singletonList(roleService.findByName("ROLE_USER")));
        return user;
    }

    public User getUpdatedUser(User persistedUser, UserUpdateDTO userUpdateDTO) {
        persistedUser.setName(userUpdateDTO.getName());
        persistedUser.setSurname(userUpdateDTO.getSurname());
        persistedUser.setUsername(userUpdateDTO.getUsername());
        persistedUser.setEmail(userUpdateDTO.getEmail());
        persistedUser.setRoles(getAssignedRolesList(userUpdateDTO));
        persistedUser.setEnabled(userUpdateDTO.isEnabled());
        return persistedUser;
    }

    public List<Role> getAssignedRolesList(UserUpdateDTO userUpdateDto) {
        Map<Long, Role> assignedRoleMap = new HashMap<>();
        List<Role> roles = userUpdateDto.getRoles();
        for (Role role : roles) assignedRoleMap.put(role.getId(), role);

        List<Role> userRoles = new ArrayList<>();
        List<Role> allRoles = roleService.findAll();

        for (Role role : allRoles) {
            if (assignedRoleMap.containsKey(role.getId())) userRoles.add(role);
            else userRoles.add(null);
        }
        return userRoles;
    }
}
