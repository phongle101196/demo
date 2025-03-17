package com.example.test.controller;

import com.example.test.dto.*;
import com.example.test.exception.UserNotFoundException;
import com.example.test.service.AuthService;
import com.example.test.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            UserDTO userDTO = authService.register(registerDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success(HttpStatus.CREATED.value(), "Đăng ký thành công!", userDTO));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
            return ResponseEntity
                    .ok(ApiResponse.success(HttpStatus.OK.value(), "Đăng nhập thành công!", loginResponseDTO));
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Sai tài khoản hoặc mật khẩu!"));
        }
    }
}
