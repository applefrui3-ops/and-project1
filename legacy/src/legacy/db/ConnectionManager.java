package legacy.db;

import legacy.config.AppConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static HikariDataSource dataSource;

    public static void init(AppConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getProperty("db.url"));
        hikariConfig.setUsername(config.getProperty("db.username"));
        hikariConfig.setPassword(config.getProperty("db.password"));
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setMaximumPoolSize(config.getIntProperty("db.pool.size"));

        dataSource = new HikariDataSource(hikariConfig);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
