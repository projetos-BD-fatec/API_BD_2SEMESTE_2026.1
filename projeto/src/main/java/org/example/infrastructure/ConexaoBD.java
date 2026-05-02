package org.example.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final ConfigBD config = new ConfigBD();

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(config.getUrl());
    }
}
