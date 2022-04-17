package ui;

import model.Account;
import model.AccountList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;

import static java.lang.Integer.parseInt;

// GUI for Satoshi Tracker!
public class GUI extends JFrame
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private static final String createString = "Create";
    private static final String deleteString = "Delete";
    private static final String depositString = "Deposit";
    private static final String withdrawString = "Withdraw";
    private static final String transferString = "Transfer";
    private static final String quitString = "Quit";
    private static final String saveString = "Save";
    private static final String loadString = "Load";
    private static final String JSON_STORE = "./data/SatoshiTracker.json";
    private static final Font font = new Font("Calibri",Font.BOLD,20);

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JButton createButton;
    private JButton deleteButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JButton quitButton;
    private JButton saveButton;
    private JButton loadButton;
    private JTextField accountName;
    private AccountList accountList = new AccountList("Satoshi Tracker");
    private JOptionPane optionPane;
    private ImageIcon bitcoinLogo;

    //GUI constructor
    public GUI() {
        setSize(1500, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createOptionPane();

        listModel = new DefaultListModel();

        //Create the list and put it in a scroll pane.
        JScrollPane listScrollPane = createJPane();

        CreateListener createListener = buttonCreate();

        buttonRemove();
        buttonDeposit();
        buttonWithdraw();
        buttonTransfer();

        buttonsJson();
        textField(createListener);
        JPanel buttonPane = setLayout();

        setBorders(listScrollPane, buttonPane);
        setJson();
    }

    // EFFECTS: sets borders for GUI
    private void setBorders(JScrollPane listScrollPane, JPanel buttonPane) {
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // EFFECTS: creates jsonWriter and Reader objects
    private void setJson() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: JPanel
    // EFFECTS: sets the JPanel layout
    private JPanel setLayout() {
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(deleteButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(accountName);
        accountName.setFont(font);
        buttonPane.add(createButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        buttonPane.add(depositButton);
        buttonPane.add(withdrawButton);
        buttonPane.add(transferButton);
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);
        buttonPane.add(quitButton);
        return buttonPane;
    }

    // EFFECTS: Listener for text field that creates account
    private void textField(CreateListener createListener) {
        accountName = new JTextField(10);
        accountName.addActionListener(createListener);
        accountName.getDocument().addDocumentListener(createListener);
    }

    // EFFECTS: Controls the JSON buttons
    private void buttonsJson() {
        saveButton = new JButton(saveString);
        saveButton.setFont(font);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton(loadString);
        loadButton.setFont(font);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());

        quitButton = new JButton(quitString);
        quitButton.setFont(font);
        quitButton.setActionCommand(quitString);
        quitButton.addActionListener(new QuitListener());
    }

    // EFFECTS: controls the transfer button
    private void buttonTransfer() {
        transferButton = new JButton(transferString);
        transferButton.setFont(font);
        transferButton.setActionCommand(transferString);
        transferButton.addActionListener(new TransferListener());
    }

    // EFFECTS: controls the withdraw button
    private void buttonWithdraw() {
        withdrawButton = new JButton(withdrawString);
        withdrawButton.setFont(font);
        withdrawButton.setActionCommand(withdrawString);
        withdrawButton.addActionListener(new WithdrawListener());
    }

    // EFFECTS: controls the deposit button
    private void buttonDeposit() {
        depositButton = new JButton(depositString);
        depositButton.setFont(font);
        depositButton.setActionCommand(depositString);
        depositButton.addActionListener(new DepositListener());
    }

    // EFFECTS: controls the remove button
    private void buttonRemove() {
        deleteButton = new JButton(deleteString);
        deleteButton.setFont(font);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteListener());
    }

    // EFFECTS: controls the create button and listener
    private CreateListener buttonCreate() {
        createButton = new JButton(createString);
        createButton.setFont(font);
        CreateListener createListener = new CreateListener(createButton);
        createButton.setActionCommand(createString);
        createButton.addActionListener(createListener);
        createButton.setEnabled(false);
        return createListener;
    }

    //MODIFIES: JPanel
    //EFFECTS: adds a scroll bar to the ListModel JPane
    private JScrollPane createJPane() {
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        list.setFont(font);
        JScrollPane listScrollPane = new JScrollPane(list,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar bar = listScrollPane.getVerticalScrollBar();
        bar.setPreferredSize(new Dimension(20, 50));
        return listScrollPane;
    }

    //EFFECTS: makes the popup with the logo!
    private void createOptionPane() {
        bitcoinLogo = new ImageIcon("src/images/Bitcoin png.png");
        optionPane = new JOptionPane("popup");
        UIManager.put("OptionPane.informationIcon", bitcoinLogo);
        optionPane.setSize(200,200);
        optionPane.setVisible(false);
    }

    // MODIFIES: accountList, JList
    // EFFECTS: removes account from account list and JList
    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);
            accountList.removeAccount(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable delete
                deleteButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // MODIFIES: account, JList
    // EFFECTS:increases balance of account by x amount and updates String in JList
    class DepositListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();


            String n = JOptionPane.showInputDialog("Enter amount to deposit");
            Account account = accountList.getAccount(index);
            account.increaseBalanceBy(parseInt(n));

            listModel.setElementAt("Account: " + account.getName()
                    + "   [Balance: " + account.getBalance() + " satoshis]",index);
            JOptionPane.showMessageDialog(optionPane, "Transaction Successful!");
        }
    }

    // MODIFIES: account, JList
    // EFFECTS:decreases balance of account by x amount and updates String in JList
    class WithdrawListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            int index = list.getSelectedIndex();


            String n = JOptionPane.showInputDialog("Enter amount to Withdraw");
            Account account = accountList.getAccount(index);
            int balance = account.getBalance();
            if (parseInt(n) > account.getBalance()) {
                JOptionPane.showMessageDialog(optionPane, "Not Enough Funds!");
            }
            account.decreaseBalanceBy(parseInt(n));

            listModel.setElementAt("Account: " + account.getName()
                    + "   [Balance: " + account.getBalance() + " satoshis]",index);
            if (parseInt(n) <= balance) {
                JOptionPane.showMessageDialog(optionPane, "Transaction Successful!");
            }
        }
    }

    // MODIFIES: account, JList
    // EFFECTS: if there is enough funds, decreases balance of account by x amount, and increases the balance of
    //          called for account, then displays updated JList Strings. Otherwise does nothing and not enough
    //          funds popup appears
    class TransferListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            int index = list.getSelectedIndex();
            int depositIndex = 0;
            Account withdrawAccount = accountList.getAccount(index);
            Account depositAccount;

            String depositTo = JOptionPane.showInputDialog("Enter name of receiving account: ");

            doDeposit(index, withdrawAccount, depositTo);
        }
    }

    // MODIFIES: account, JList
    // EFFECTS: if there is enough funds, decreases balance of account by x amount, and increases the balance of
    //          called for account, then displays updated JList Strings. Otherwise does nothing and not enough
    //          funds popup appears
    private void doDeposit(int index, Account withdrawAccount, String depositTo) {
        int depositIndex;
        Account depositAccount;
        if (accountList.checkAccounts(depositTo)) {
            for (int i = 0; i < accountList.length(); i++) {
                if (depositTo.equals(accountList.getAccount(i).getName())) {
                    depositAccount = accountList.getAccount(i);
                    String amount = JOptionPane.showInputDialog("Enter amount to Transfer");
                    moveSats(index, withdrawAccount, depositAccount, i, amount);
                }
            }
        } else {
            JOptionPane.showMessageDialog(optionPane, "Not a valid account!");
        }
    }

    // MODIFIES: account, JList
    // EFFECTS: if there is enough funds, decreases balance of account by x amount, and increases the balance of
    //          called for account, then displays updated JList Strings. Otherwise does nothing and not enough
    //          funds popup appears
    private void moveSats(int index, Account withdrawAccount, Account depositAccount, int i, String amount) {
        int depositIndex;
        if (parseInt(amount) <= withdrawAccount.getBalance()) {
            withdrawAccount.decreaseBalanceBy(parseInt(amount));
            accountList.getAccount(i).increaseBalanceBy(parseInt(amount));
            depositIndex = i;
            list.setSelectedIndex(depositIndex);
            listModel.setElementAt("Account: " + depositAccount.getName()
                    + "   [Balance: " + depositAccount.getBalance() + " satoshis]", i);
            list.setSelectedIndex(index);
            listModel.setElementAt("Account: " + withdrawAccount.getName()
                    + "   [Balance: " + withdrawAccount.getBalance() + " satoshis]", index);
            JOptionPane.showMessageDialog(optionPane, "Transaction Successful!");
        } else {
            JOptionPane.showMessageDialog(optionPane, "Not Enough Funds!");
        }
    }

    // EFFECTS: on button click, calls saveAccountList()
    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveAccountList();
        }
    }

    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadAccountList();
        }
    }

    //This listener is shared by the text field and the create button.
    class CreateListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public CreateListener(JButton button) {
            this.button = button;
        }


        public void actionPerformed(ActionEvent e) {
            Account account = new Account(accountName.getText());
            account.setBalance(0);
            accountList.addAccount(account);


            if (account.getName().equals("") || alreadyInList(account.getName())) {
                Toolkit.getDefaultToolkit().beep();
                accountName.requestFocusInWindow();
                accountName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.addElement("Account: "
                    + account.getName() + "   [Balance: " + account.getBalance() + " satoshis]");

            //Reset the text field.
            accountName.requestFocusInWindow();
            accountName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);

            JOptionPane.showMessageDialog(optionPane, "Account added!");
        }

        // EFFECTS: checks for string equality
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        // EFFECTS: Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // EFFECTS: Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // EFFECTS: Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        //MODIFIES: JButton
        //EFFECTS: Enables JButton
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        //EFFECTS: When text field is empty
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // EFFECTS: This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                deleteButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                deleteButton.setEnabled(true);
            }
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveAccountList() {
        try {
            jsonWriter.open();
            jsonWriter.write(accountList);
            jsonWriter.close();
            System.out.println("Saved " + accountList.getListName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: AccountList and JList
    // EFFECTS: loads workroom from file
    private void loadAccountList() {
        try {
            listModel.removeAllElements();
            accountList = jsonReader.read();

            for (Account account : accountList.getList()) {
                listModel.addElement("Account: "
                        + account.getName() + "   [Balance: " + account.getBalance() + " satoshis]");
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // EFFECTS: displays EventLog and quits application
    class QuitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            this.printLog(EventLog.getInstance());
            System.exit(0);
        }

        public void printLog(EventLog el) {
            for (Event next : el) {
                System.out.println(next.toString() + "\n");
            }
        }
    }
}
