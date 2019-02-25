package com.tmw.design.principle.compositionnaggregation;

public class Test {

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        productDAO.setDbConnection(new PostgreSQLConnection());
        productDAO.addProduct();
    }

}
