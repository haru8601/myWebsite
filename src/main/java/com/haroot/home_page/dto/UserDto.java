package com.haroot.home_page.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {
    @NotBlank
    @Size(max = 30)
    final String username;
    @NotBlank
    @Size(max = 100)
    final String password;
}
