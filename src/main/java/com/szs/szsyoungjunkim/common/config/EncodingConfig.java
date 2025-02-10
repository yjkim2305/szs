package com.szs.szsyoungjunkim.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class EncodingConfig {

    private static final String SECRET_KEY = "szs_secure_key";
    private static final String SALT = "1234567890123456";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public TextEncryptor customRegNoEncryptor() {
        return Encryptors.text(SECRET_KEY, SALT);
    }
}
