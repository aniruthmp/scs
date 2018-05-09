package com.example.customer.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Profile({"local", "sso"})
@EnableResourceServer
@Slf4j
public class AuthConfig {
    @Value("${ssoScope:pivotal}")
    private String ssoResourceId;

    @Bean
    public GlobalMethodSecurityConfiguration globalMethodSecurityConfiguration() {
        log.info(" ### Ani came inside globalMethodSecurityConfiguration ### ");
        return new GlobalMethodSecurityConfiguration() {
            @Override
            protected MethodSecurityExpressionHandler createExpressionHandler() {
                return new OAuth2MethodSecurityExpressionHandler();
            }
        };
    }

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)
                        SecurityContextHolder.getContext().getAuthentication().getDetails();
                requestTemplate.header("Authorization", "bearer " + details.getTokenValue());
            }
        };
    }

    @Bean
    public ResourceServerConfigurer resourceServerConfigurerAdapter() {
        log.info(" ### Ani came inside resourceServerConfigurerAdapter ### ");
        return new ResourceServerConfigurerAdapter() {
            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                resources.resourceId(ssoResourceId);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http.addFilterAfter(new OncePerRequestFilter() {
                    @Override
                    protected void doFilterInternal(HttpServletRequest request,
                                                    HttpServletResponse response, FilterChain filterChain)
                            throws ServletException, IOException {
                        filterChain.doFilter(request, response);
                    }
                }, AbstractPreAuthenticatedProcessingFilter.class);
                http.csrf().disable();
                http.authorizeRequests().anyRequest().authenticated();
            }
        };
    }
}

