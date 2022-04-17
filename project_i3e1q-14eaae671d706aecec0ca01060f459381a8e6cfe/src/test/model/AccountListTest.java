package model;

import model.AccountList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountListTest {
    private AccountList accountList;
    private Account account1;
    private Account account2;

    @BeforeEach
    void setup() {
        accountList = new AccountList("Account Tracker");
        account1 = new Account("Ed");
        account2 = new Account("Jordan");
    }

    @Test
    void testAccountList() {
        assertEquals(0,accountList.length());
    }

    @Test
    void testAddAccount() {
        accountList.addAccount(account1);
        assertEquals(1, accountList.length());
        accountList.addAccount(account2);
        assertEquals(2, accountList.length());
    }

    @Test
    void testGetAccount() {
        accountList.addAccount(account1);
        accountList.addAccount(account2);

        assertEquals(account1, accountList.getAccount(0));
        assertEquals(account2, accountList.getAccount(1));
    }

    @Test
    void testRemoveAccount() {
        accountList.addAccount(account1);
        accountList.addAccount(account2);

        accountList.removeAccount(0);

        assertEquals(1, accountList.length());
        assertEquals(account2, accountList.getAccount(0));
    }

    @Test
    void testGetListName() {
        accountList.addAccount(account1);
        accountList.addAccount(account2);

        assertEquals("Account Tracker", accountList.getListName());
    }

    @Test
    void testGetList() {
        accountList.addAccount(account1);
        assertEquals(accountList.getList(),accountList.getList());
    }

    @Test
    void testCheckAccount() {
        accountList.addAccount(account1);
        accountList.addAccount(account2);

        assertTrue(accountList.checkAccounts("Ed"));
        assertTrue(accountList.checkAccounts("Jordan"));
        assertFalse(accountList.checkAccounts("test"));
    }
}

