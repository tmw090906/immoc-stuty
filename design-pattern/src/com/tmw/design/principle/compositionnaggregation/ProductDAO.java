package com.tmw.design.principle.compositionnaggregation;

public class ProductDAO {

    private DBConnection dbConnection;

    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void addProduct() {
        System.out.println("通过" + dbConnection.getConnection() + "添加产品");
    }

}
