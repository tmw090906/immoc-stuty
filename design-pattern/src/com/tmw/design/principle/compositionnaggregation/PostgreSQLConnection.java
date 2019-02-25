package com.tmw.design.principle.compositionnaggregation;

public class PostgreSQLConnection implements DBConnection {
    @Override
    public String getConnection() {
        return "PostgreSQL数据库Connection";
    }
}
