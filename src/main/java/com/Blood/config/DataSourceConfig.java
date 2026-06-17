package com.Blood.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String rawUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password:}")
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() {
        String jdbcUrl = rawUrl;
        String dbUser = username;
        String dbPassword = password;

        if (rawUrl != null && !rawUrl.isEmpty()) {
            if (rawUrl.startsWith("mysql://")) {
                try {
                    // Extract URI components
                    // mysql://username:password@host:port/database
                    URI uri = new URI(rawUrl);
                    String userInfo = uri.getUserInfo();
                    if (userInfo != null) {
                        if (userInfo.contains(":")) {
                            String[] parts = userInfo.split(":", 2);
                            dbUser = parts[0];
                            dbPassword = parts[1];
                        } else {
                            dbUser = userInfo;
                        }
                    }
                    String host = uri.getHost();
                    int port = uri.getPort();
                    String path = uri.getPath(); // starts with "/"
                    String query = uri.getQuery();

                    // Construct JDBC url
                    String portStr = (port != -1) ? ":" + port : "";
                    String queryStr = (query != null && !query.isEmpty()) ? "?" + query : "";
                    
                    // Add robust default query params if not present
                    if (queryStr.isEmpty()) {
                        queryStr = "?allowPublicKeyRetrieval=true&useSSL=false";
                    } else {
                        if (!queryStr.contains("allowPublicKeyRetrieval")) {
                            queryStr += "&allowPublicKeyRetrieval=true";
                        }
                        if (!queryStr.contains("useSSL")) {
                            queryStr += "&useSSL=false";
                        }
                    }

                    jdbcUrl = "jdbc:mysql://" + host + portStr + path + queryStr;
                    System.out.println("[DataSourceConfig] Parsed mysql:// URL into JDBC URL: " + jdbcUrl);
                } catch (Exception e) {
                    System.err.println("[DataSourceConfig] Failed to parse mysql:// URL: " + e.getMessage());
                }
            } else if (rawUrl.startsWith("jdbc:mysql://")) {
                // Ensure allowPublicKeyRetrieval and useSSL are configured to prevent common connection failures
                if (!rawUrl.contains("allowPublicKeyRetrieval")) {
                    jdbcUrl += (jdbcUrl.contains("?") ? "&" : "?") + "allowPublicKeyRetrieval=true";
                }
                if (!rawUrl.contains("useSSL")) {
                    jdbcUrl += (jdbcUrl.contains("?") ? "&" : "?") + "useSSL=false";
                }
                System.out.println("[DataSourceConfig] Using JDBC URL: " + jdbcUrl);
            }
        }

        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(dbUser)
                .password(dbPassword)
                .build();
    }
}
