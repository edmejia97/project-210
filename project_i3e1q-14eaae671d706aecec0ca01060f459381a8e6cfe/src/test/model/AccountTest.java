package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests the account class
class AccountTest {
    private AccountList accountList;
    private Account account1;
    private Account account2;

    @BeforeEach
    public void setup() {
        account1 = new Account("Ed");
        account2 = new Account("Jordan");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, account1.getBalance());
        assertEquals("Jordan", account2.getName());
    }

    @Test
    public void testIncreaseBalanceBy() {
        assertEquals(0, account1.getBalance());
        account1.increaseBalanceBy(500);
        assertEquals(500, account1.getBalance());

    }

    @Test
    public void testDecreaseBalanceBy() {
        account2.setBalance(5000);
        account2.decreaseBalanceBy(400);


        assertEquals(4600, account2.getBalance());
        account2.decreaseBalanceBy(5500);
        assertEquals(4600, account2.getBalance());

    }


}

