package jp.co.pnop.moris.func.sample;

import java.sql.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCpDbSource {
     
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
     
    static {
        config.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=AdventureWorks2016");
        config.setUsername("your username");
        config.setPassword("your passowrd");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(20);
        ds = new HikariDataSource(config);
    }
     
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
     
    private HikariCpDbSource(){}
}