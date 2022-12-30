package com.nextplugins.libs.npcranking.example;

public class Account {

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

}
