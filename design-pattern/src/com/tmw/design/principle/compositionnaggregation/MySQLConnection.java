package com.tmw.design.principle.compositionnaggregation;

public class MySQLConnection implements DBConnection {
    @Override
    public String getConnection() {
        return "MySQL数据库Connection";
    }
}
