package com.nextplugins.libs.npcranking.example;

import java.text.DecimalFormat;

public class Account {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.###");

    private final String name;
    private final double balance;

    Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getBalanceFormatted() {
        return DECIMAL_FORMAT.format(getBalance());
    }

}
