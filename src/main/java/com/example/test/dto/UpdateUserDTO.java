package com.example.test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    @Pattern(regexp = "(\\+84|0)[0-9]{9}", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Size(max = 50, message = "Họ không được vượt quá 50 ký tự")
    private String firstName;

    @Size(max = 50, message = "Tên không được vượt quá 50 ký tự")
    private String lastName;
    private String avatarUrl;
}
