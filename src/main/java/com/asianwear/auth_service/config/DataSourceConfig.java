package com.asianwear.auth_service.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.rds.auth.GetIamAuthTokenRequest;
import com.amazonaws.services.rds.auth.RdsIamAuthTokenGenerator;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
public class DataSourceConfig {
    private static final String RDS_ENDPOINT = "your-rds-endpoint.rds.amazonaws.com";
    private static final String DB_NAME = "yourdbname";
    private static final String DB_USER = "your-db-user";
    private static final int RDS_PORT = 5432;
    @Bean
    public DataSource dataSource() {

            // Generate IAM authentication token
            RdsIamAuthTokenGenerator authTokenGenerator = RdsIamAuthTokenGenerator.builder()
                    .credentials(new DefaultAWSCredentialsProviderChain()) // Uses IAM Role or credentials
                    .build();

            GetIamAuthTokenRequest authTokenRequest = GetIamAuthTokenRequest.builder()
                    .hostname(RDS_ENDPOINT)
                    .port(RDS_PORT)
                    .userName(DB_USER)
                    .build();

            String authToken = authTokenGenerator.getAuthToken(authTokenRequest);

            // Configure HikariCP DataSource
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:postgresql://" + RDS_ENDPOINT + ":" + RDS_PORT + "/" + DB_NAME);
            dataSource.setUsername(DB_USER);
            dataSource.setPassword(authToken);
            dataSource.setMaximumPoolSize(10);
            dataSource.setConnectionTimeout(Duration.ofSeconds(30).toMillis());

            return dataSource;

        }
}
