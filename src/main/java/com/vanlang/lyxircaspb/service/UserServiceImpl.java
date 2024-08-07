package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.UserRole;
import com.vanlang.lyxircaspb.config.JwtProvider;
import com.vanlang.lyxircaspb.exception.UserException;
import com.vanlang.lyxircaspb.model.Role;
import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.repository.RoleRepository;
import com.vanlang.lyxircaspb.repository.UserRepository;
import com.vanlang.lyxircaspb.request.UpdatePasswordRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        for (UserRole userRole : UserRole.values()) {
            if (roleRepository.findById(userRole.value).isEmpty()) {
                Role role = new Role();
                role.setId(userRole.value);
                role.setName(userRole.name());
                role.setDescription("Description for " + userRole.name());
                roleRepository.save(role);
            }
        }
    }

    public void assignDefaultRole(User user) {
        Role defaultRole = roleRepository.findRoleById(UserRole.USER.value);
        if (defaultRole == null) {
            throw new IllegalStateException("Default role not found");
        }
        user.getRoles().add(defaultRole);
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long userId, User updatedUser) throws UserException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with ID: " + userId));

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            if (!existingUser.getEmail().equals(updatedUser.getEmail()) &&
                    userRepository.findByEmail(updatedUser.getEmail()) != null) {
                throw new UserException("User with email: " + updatedUser.getEmail() + " already exists");
            }
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }

        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }

        if (updatedUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }

        userRepository.save(existingUser);
    }

    @Override
    public User resetUserPassword(UpdatePasswordRequest req) throws UserException {
        User user = userRepository.findByEmail(req.getEmail());
        if (user == null) {
            throw new UserException("User not found with email: " + req.getEmail());
        } else {
            // Set the new password and encode it
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            // Save the updated user
            return userRepository.save(user);
        }
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserException("User not found with id " + userId);
        }
        return user;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email " + email);
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user;
    }
}
