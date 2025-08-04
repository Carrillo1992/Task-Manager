package com.dcarrillo.taskmanager.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO {
    private String accessToken;
    private final String tokenType = "Bearer";
    private Long userId;
    private String email;
    private List<String> roles;
}
