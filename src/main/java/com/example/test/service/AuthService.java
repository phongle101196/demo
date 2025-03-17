package com.example.test.service;

import com.example.test.dto.LoginDTO;
import com.example.test.dto.LoginResponseDTO;
import com.example.test.dto.RegisterDTO;
import com.example.test.dto.UserDTO;
import com.example.test.entity.User;
import com.example.test.exception.UserNotFoundException;
import com.example.test.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO register(RegisterDTO registerDTO) {
        // check username và email xem có chưa
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // create user
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setPhone(registerDTO.getPhone());
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setCreateDate(LocalDateTime.now());
        user.setAvatarUrl("avatar.png");
        user.setRole(User.Role.USER);

        // lưu user
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }
    public LoginResponseDTO login(LoginDTO loginDTO) {
        Optional<User> userOptional = userRepository.findByUsername(loginDTO.getUsername());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User không tồn tại!");
        }
        Authentication authentication  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        User user = userOptional.get();
        return new LoginResponseDTO(user.getId(), user.getUsername());
    }
}
