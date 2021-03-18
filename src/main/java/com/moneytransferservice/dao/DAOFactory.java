package com.moneytransferservice.dao;

public abstract class DAOFactory {
    public static final int H2 = 1;

    public abstract UserDAO getUserDAO();

    public abstract AccountDAO getAccountDAO();

    public abstract void populateTestData();

    public static DAOFactory getDAOFactory(int factoryCode) {

        if (factoryCode == H2) {
            return new H2DAOFactory();
        }// by default using H2 in memory database
        return new H2DAOFactory();
    }
}
