package com.example.shelterapi.data;

import com.example.shelterapi.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class JDBCtemplateConfig {

    private final ApplicationConfig applicationConfig;

    public JDBCtemplateConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public Connection connection() throws SQLException {
        return DriverManager.getConnection(
                applicationConfig.getUrl(),
                applicationConfig.getUsername(),
                applicationConfig.getPassword()
        );
    }

}
