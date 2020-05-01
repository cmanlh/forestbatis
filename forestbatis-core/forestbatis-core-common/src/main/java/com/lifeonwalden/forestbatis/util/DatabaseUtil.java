package com.lifeonwalden.forestbatis.util;

import com.lifeonwalden.forestbatis.constant.DatabaseType;

public interface DatabaseUtil {
    static DatabaseType fromProductName(String productName) {
        if ("Oracle".equalsIgnoreCase(productName)) {
            return DatabaseType.ORACLE;
        } else if ("MySQL".equalsIgnoreCase(productName)) {
            return DatabaseType.MYSQL;
        } else if ("Microsoft SQL Server".equalsIgnoreCase(productName)) {
            return DatabaseType.MSSQL;
        } else if ("HSQL Database Engine".equalsIgnoreCase(productName)) {
            return DatabaseType.HSQL;
        } else {
            throw new RuntimeException("Unknown Database Type.");
        }

    }
}
