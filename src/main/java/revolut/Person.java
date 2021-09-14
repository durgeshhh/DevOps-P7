package revolut;

import java.util.Currency;
import java.util.HashMap;

public class Person {

    private String name;
    static final String EURO_ACCOUNT = "EUR";
    static final int splitBy = 2;

    //Accounts currency & balance
    // EUR 30
    // USD 50
    // STG 30
    private HashMap<String, Account> userAccounts = new HashMap<String, Account>();

    public Person(String name){
        this.name = name;
        //Create a default euro account and add it the our userAccounts container
        Currency accCurrency = Currency.getInstance(EURO_ACCOUNT);
        Account euroAccount = new Account(accCurrency, 0);
        userAccounts.put(EURO_ACCOUNT, euroAccount);
    }

    public void setAccountBalance(double startingBalance) {
        userAccounts.get(EURO_ACCOUNT).setBalance(startingBalance);
    }

    public double getAccountBalance(String eur) {
        return userAccounts.get(EURO_ACCOUNT).getBalance();
    }

    public Account getAccount(String account) {
        return userAccounts.get(account);
    }

    public void send(Person person, Double sendMoney) {

        if(this.getAccount(EURO_ACCOUNT).getBalance() >= sendMoney){

            person.getAccount(EURO_ACCOUNT).receiveTransfer(sendMoney);
            this.getAccount(EURO_ACCOUNT).setBalance(this.getAccount(EURO_ACCOUNT).getBalance() - sendMoney );

        }
    }

    public void splitingBill(Person person, Double bill) {
        //Divide between two
        bill = bill / 2.0;

        if((this.getAccount(EURO_ACCOUNT).getBalance() > bill && person.getAccount(EURO_ACCOUNT).getBalance() > bill) ) {

            this.setAccountBalance(this.getAccount(EURO_ACCOUNT).getBalance() - bill);
            person.setAccountBalance(person.getAccount(EURO_ACCOUNT).getBalance() - bill);
        }

    }
}
