package com.example.test.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Username không được để trống!")
    private String username;
    @NotBlank(message = "Mật khẩu không được để trống!")
    private String password;
}
