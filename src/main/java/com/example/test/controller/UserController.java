package com.example.test.controller;

import com.example.test.dto.ApiResponse;
import com.example.test.dto.UpdateUserDTO;
import com.example.test.dto.UserDTO;
import com.example.test.exception.NoDataFoundException;
import com.example.test.exception.UserNotFoundException;
import com.example.test.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<UserDTO> userDTOPage = userService.getAllUsers(page, size);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Success", userDTOPage));
        } catch (NoDataFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(Authentication auth) {
        try {
            String currentUsername = auth.getName();
            UserDTO userDTO = userService.getCurrentUser(currentUsername);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Success", userDTO));
        }catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Có lỗi xảy ra!"));
        }
    }

    @PutMapping("/update/me")
    public ResponseEntity<ApiResponse<UserDTO>> updateCurrentUser(@Valid @RequestBody UpdateUserDTO updateUserDTO,
                                                                  Authentication auth) {
        try {
            String currentUsername = auth.getName();
            UserDTO updatedUser = userService.updateCurrentUser(updateUserDTO, currentUsername);
            return ResponseEntity.ok(
                    ApiResponse.success(HttpStatus.OK.value(), "Cập nhật thành công!", updatedUser));
        }catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Có lỗi xảy ra"));
        }
    }

    @DeleteMapping("/delete/me")
    public ResponseEntity<ApiResponse<String>> deleteUser(Authentication auth) {
        try {
            String currentUsername = auth.getName();
            userService.deleteCurrentUser(currentUsername);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Xóa tài khoản thành công!", null));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Có lỗi xảy ra!"));
        }
    }
}
