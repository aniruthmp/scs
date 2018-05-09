package com.example.bffservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties(prefix = "security.oauth2.client")
@Profile({"local", "sso"})
@Data
public class OAuth2ClientConfig {

    private String clientId;
    private String clientSecret;
    private String grantType;
    private String accessTokenUri;

}

