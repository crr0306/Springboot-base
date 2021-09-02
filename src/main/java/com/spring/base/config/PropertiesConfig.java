package com.spring.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rsa")
@Data
public class PropertiesConfig {
    private String e;
    private String d;
    private String n;


}