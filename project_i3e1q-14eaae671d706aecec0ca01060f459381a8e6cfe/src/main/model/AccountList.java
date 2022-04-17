package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// AccountList is an array of Accounts
public class AccountList implements Writable {
    private String name;
    private ArrayList<Account> accountList;

    // EFFECTS: Instantiation of Account List
    public AccountList(String name) {
        this.name = name;
        accountList = new ArrayList<>();
    }

//    public boolean contains(String accName, ArrayList<Account> list) {
//        this.accountList = list;
//
//    }

    // MODIFIES: this
    // EFFECTS: adds account to list of accounts
    public void addAccount(Account account) {
        EventLog.getInstance().logEvent(new Event("Account " + account.getName() + " added"));
        accountList.add(account);

    }

    // EFFECTS: gets account from list of accounts using
    public Account getAccount(int index) {
        return accountList.get(index);
    }

    public void removeAccount(int index) {
        String account = accountList.get(index).getName();
        EventLog.getInstance().logEvent(new Event("Account " + account + " removed"));
        accountList.remove(index);
    }

    public int length() {
        return accountList.size();
    }

    public String getListName() {
        return name;
    }

    public ArrayList<Account> getList() {
        return accountList;
    }

    public boolean checkAccounts(String accountName) {
        for (int i = 0; i < this.accountList.size(); i++) {
            if (this.accountList.get(i).getName().equals(accountName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("accounts", accountToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray accountToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account acc : accountList) {
            jsonArray.put(acc.toJson());
        }

        return jsonArray;
    }
}
