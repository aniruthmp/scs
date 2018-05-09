package com.example.bffservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {
    private String access_token;
    private String token_type;
    private String scope;
    private String jti;
    private long expires_in;
}
