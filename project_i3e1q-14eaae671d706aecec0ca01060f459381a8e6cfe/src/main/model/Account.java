package model;

import com.sun.corba.se.spi.ior.Writeable;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;

// Account object holds the current balance of a given list.
public class Account implements Writable {
    private String name;
    private int balance;

    // EFFECTS: Creates instantiation of an Account with balance 0
    public Account(String identifier) {
        this.name = identifier;
        this.balance = 0;
    }

    // REQUIRES: amount >= 0
    // Modifies: this
    // EFFECTS: Increase balance by amount deposited
    public void increaseBalanceBy(int amount) {
        balance = balance + amount;
    }

    // REQUIRES: amount >= 0.
    // Modifies: this
    // EFFECTS: If balance >= amount, decrease by amount withdrawn
    public void decreaseBalanceBy(int amount) {
        if (amount > balance) {
            //SatoshiTracker.failTransaction();
            //System.out.println("Not enough funds");
        } else {
            balance = balance - amount;
        }
    }

    public void setBalance(int amount) {
        this.balance = amount;
    }

    public int getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("balance", balance);
        return json;
    }
}
