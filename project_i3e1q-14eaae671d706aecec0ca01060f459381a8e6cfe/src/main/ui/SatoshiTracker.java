package ui;

import model.Account;

import model.AccountList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Main ui class for the app
// modeled after the teller app given to us on edx
public class SatoshiTracker {
    private static final String JSON_STORE = "./data/SatoshiTracker.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Account acc;
    private AccountList accList;
    private Scanner input;

    public SatoshiTracker() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runSatoshiTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSatoshiTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            newAccount();
        } else if (command.equals("2")) {
            checkBalance();
        } else if (command.equals("3")) {
            doDeposit();
        } else if (command.equals("4")) {
            doWithdrawal();
        } else if (command.equals("5")) {
            doTransfer();
        } else if (command.equals("6")) {
            listAccounts();
        } else if (command.equals("7")) {
            saveAccountList();
        } else if (command.equals("8")) {
            loadAccountList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes account
    private void init() {
        accList = new AccountList("Satoshi Manager");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> add account");
        System.out.println("\t2 -> check balance");
        System.out.println("\t3 -> deposit");
        System.out.println("\t4 -> withdraw");
        System.out.println("\t5 -> transfer");
        System.out.println("\t6 -> list accounts");
        System.out.println("\t7 -> save accounts");
        System.out.println("\t8 -> load accounts");
        System.out.println("\tq -> quit");
    }

    private void checkBalance() {
        System.out.println("Please enter name of account you would like to check balance");
        String accSelect = input.next();
        if (accList.checkAccounts(accSelect)) {
            for (int i = 0; i < accList.length(); i++) {
                if (accSelect.equals(accList.getAccount(i).getName())) {
                    this.acc = accList.getAccount(i);
                    System.out.println("Account " + acc.getName() + " selected");
                    System.out.println("Balance: " + acc.getBalance() + " satoshis");
                }
            }
        } else {
            System.out.println("Account does not found. Please try again.");
        }
    }

    private void doDeposit() {
        acc = null;
        System.out.println("Please enter name of account you would like to deposit to");
        String accSelect = input.next();
        if (accList.checkAccounts(accSelect)) {
            for (int i = 0; i < accList.length(); i++) {
                if (accSelect.equals(accList.getAccount(i).getName())) {
                    this.acc = accList.getAccount(i);
                    System.out.println("Account " + acc.getName() + " selected");
                    System.out.println("Please enter amount to deposit");
                    int amount = Integer.parseInt(input.next());
                    acc.increaseBalanceBy(amount);
                    System.out.println("Balance: " + acc.getBalance() + " satoshis");
                }
            }
        } else {
            System.out.println("Account not found. Please try again");
        }
    }

    private void doWithdrawal() {

        System.out.println("Please enter name of account you would like to withdraw from");
        String accSelect = input.next();
        if (accList.checkAccounts(accSelect)) {
            for (int i = 0; i < accList.length(); i++) {
                if (accSelect.equals(accList.getAccount(i).getName())) {
                    this.acc = accList.getAccount(i);
                    System.out.println("Account " + acc.getName() + " selected");
                    System.out.println("Please enter amount to withdraw");
                    int amount = Integer.parseInt(input.next());
                    acc.decreaseBalanceBy(amount);
                    System.out.println("Balance: " + acc.getBalance() + " satoshis");
                }
            }
        } else {
            System.out.println("Account not found. Please try again");
        }

    }

    private void doTransfer() {
        System.out.println("Please enter name of account you would like to withdraw from");
        String accWithdraw = input.next();
        System.out.println("Please enter name of account you would like to deposit to");
        String accDeposit = input.next();
        if (accList.checkAccounts(accDeposit) && accList.checkAccounts(accWithdraw)) {
            for (int i = 0; i < accList.length(); i++) {
                if (accDeposit.equals(accList.getAccount(i).getName())) {
                    for (i = 0; i < accList.length(); i++) {
                        i = doTransfer(accWithdraw, accDeposit, i);
                    }
                }
            }
        } else if (accList.checkAccounts(accDeposit)) {
            System.out.println("account to withdraw from not found, please try again.");
        } else if (accList.checkAccounts(accWithdraw)) {
            System.out.println("account to deposit from not found, please try again.");
        } else {
            System.out.println("accounts not found, please try again");
        }
    }

    private int doTransfer(String accWithdraw, String accDeposit, int i) {
        if (accWithdraw.equals(accList.getAccount(i).getName())) {
            acc = accList.getAccount(i);
            System.out.println("Account " + accWithdraw + " selected for withdrawal");
            System.out.println("Account " + accDeposit + " selected to deposit");
            System.out.println("Please enter amount to withdraw");
            int amount = Integer.parseInt(input.next());
            if (this.acc.getBalance() >= amount) {
                acc.decreaseBalanceBy(amount);
                for (i = 0; i < accList.length(); i++) {
                    if (accDeposit.equals(accList.getAccount(i).getName())) {
                        acc = accList.getAccount(i);
                        acc.increaseBalanceBy(amount);
                        System.out.println("transaction successful!");
                    }
                }
            } else {
                System.out.println("Insufficient funds in account to withdraw from");
            }
        }
        return i;
    }

    private void listAccounts() {
        int index = 0;
        while (index < accList.length()) {
            System.out.println(toString(accList.getAccount(index)));
            index++;
        }
    }

    private String toString(Account account) {
        acc = account;
        return ("Name: " + this.acc.getName() + ", Balance: " + this.acc.getBalance() + ".");
    }




    private void newAccount() {
        System.out.println("Please enter name of new account");
        String accName = input.next();
        input.nextLine();

        if (!accList.checkAccounts(accName)) {
            acc = new Account(accName);
            System.out.println("Account created for " + accName);
            accList.addAccount(acc);
        } else {
            System.out.println("Account with this name already exists, please try again.");
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveAccountList() {
        try {
            jsonWriter.open();
            jsonWriter.write(accList);
            jsonWriter.close();
            System.out.println("Saved " + accList.getListName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadAccountList() {
        try {
            accList = jsonReader.read();
            System.out.println("Loaded " + accList.getListName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}