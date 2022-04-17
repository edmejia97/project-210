package persistence;

import model.Account;
import model.AccountList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// This test is based off the sample Work Room file from edx
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            AccountList al = new AccountList("Satoshi Tracker");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccountList() {
        try {
            AccountList al = new AccountList("SatoshiTracker");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySatoshiTracker.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySatoshiTracker.json");
            al = reader.read();
            assertEquals("SatoshiTracker", al.getListName());
            assertEquals(0, al.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccountList() {
        try {
            AccountList al = new AccountList("SatoshiTracker");
            al.addAccount(new Account("one"));
            al.addAccount(new Account("two"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSatoshiTracker.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSatoshiTracker.json");
            al = reader.read();
            assertEquals("SatoshiTracker", al.getListName());
            ArrayList<Account> accounts = al.getList();
            assertEquals(2, accounts.size());
            checkAccount("one", accounts.get(0));
            checkAccount("two", accounts.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}