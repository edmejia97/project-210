package persistence;

import model.Account;
import model.AccountList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AccountList al = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccountList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySatoshiTracker.json");
        try {
            AccountList al = reader.read();
            assertEquals("SatoshiTracker", al.getListName());
            assertEquals(0, al.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSatoshiTracker.json");
        try {
            AccountList al = reader.read();
            assertEquals("SatoshiTracker", al.getListName());
            List<Account> accounts = al.getList();
            assertEquals(2, accounts.size());
            checkAccount("one", accounts.get(0));
            checkAccount("two", accounts.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}