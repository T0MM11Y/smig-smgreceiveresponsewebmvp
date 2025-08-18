package com.smig.smg.receiver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * Database configuration and connectivity verification
 * 
 * @author SMIG Development Team
 */
@Configuration
@Slf4j
public class DatabaseConfig {

    private final DataSource dataSource;

    public DatabaseConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void verifyDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            log.info("Database connection verified successfully:");
            log.info("  Database: {} {}", metaData.getDatabaseProductName(), metaData.getDatabaseProductVersion());
            log.info("  Driver: {} {}", metaData.getDriverName(), metaData.getDriverVersion());
            log.info("  URL: {}", metaData.getURL());
            log.info("  Username: {}", metaData.getUserName());
        } catch (Exception e) {
            log.error("Failed to verify database connection: {}", e.getMessage(), e);
        }
    }
}
