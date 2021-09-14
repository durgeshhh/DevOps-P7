package revolut;

import java.util.Currency;

public class Account {
    private Currency accCurrency;
    private double balance;

    public Account(Currency currency, double balance){
        this.balance = balance;
        this.accCurrency = currency;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public double getBalance() {
        return this.balance;
    }

    public boolean addFunds(double topUpAmount, PaymentService topUpMethod) {

        if(topUpMethod.getBalance() >= topUpAmount){

            this.balance += topUpAmount;
            return true;
        }

        return false;
    }

    public void receiveTransfer(Double topUpAmount) {
        this.balance += topUpAmount;
    }
}
