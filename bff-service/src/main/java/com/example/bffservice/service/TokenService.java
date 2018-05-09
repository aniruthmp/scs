package com.example.bffservice.service;

import com.example.bffservice.config.OAuth2ClientConfig;
import com.example.bffservice.model.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class TokenService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OAuth2ClientConfig oAuth2ClientConfig;

    /**
     * Get Access Token
     * @return TokenInfo
     */
    public TokenInfo getAccessToken() {
        log.info("Came inside getAccessToken()");
        HttpHeaders headers = getHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("client_id", oAuth2ClientConfig.getClientId());
        map.add("client_secret", oAuth2ClientConfig.getClientSecret());
        map.add("grant_type", oAuth2ClientConfig.getGrantType());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<TokenInfo> tokenInfoResponseEntity = restTemplate.postForEntity(
                oAuth2ClientConfig.getAccessTokenUri(), request, TokenInfo.class);
        if (Objects.nonNull(tokenInfoResponseEntity)) {
            return tokenInfoResponseEntity.getBody();
        } else {
            log.error("No Access Token");
            return null;
        }
    }

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     */
    public HttpHeaders getHeadersWithAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        TokenInfo tokenInfo = getAccessToken();
        headers.add(HttpHeaders.AUTHORIZATION, tokenInfo.getToken_type() + " " + tokenInfo.getAccess_token());
        return headers;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
