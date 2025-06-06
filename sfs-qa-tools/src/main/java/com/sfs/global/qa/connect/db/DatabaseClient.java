package com.sfs.global.qa.connect.db;

import com.sfs.global.qa.connect.db.clients.mybatis.MyBatisDbClient;

public class DatabaseClient {

    public static MyBatisDbClient mybatis() {
        return new MyBatisDbClient();
    }

    public static String getDatabaseDriver(final String databaseType) {
        switch (databaseType.toLowerCase()) {
            case "postgresql":
                return "org.postgresql.Driver";
            case "mysql":
                return "com.mysql.cj.jdbc.Driver";
            case "oracle":
                return "oracle.jdbc.OracleDriver";
            case "neo4j":
                return "org.neo4j.jdbc.Driver";
            case "mssql":
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            default:
                final String message = "databaseType not recognized: " + databaseType;
                throw new IllegalArgumentException(message);
        }
    }

    /*private String getProperty(String propertyName) {
        if (dbProperties.getProperty("credentialsEncoded").equals("true")) {
            return new String(Base64.getDecoder().decode(dbProperties.getProperty(propertyName)));
        } else {
            return dbProperties.getProperty(propertyName);
        }
    }*/

}
