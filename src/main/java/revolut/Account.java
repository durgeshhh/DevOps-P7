package revolut;

import java.util.Currency;

public class Account {
    private Currency accountCurrency;
    private PaymentService paymentService;
    private double accountBalance;

    public Account(String accountCurrency, double balance){
        this.accountBalance = balance;

        this.accountCurrency = Currency.getInstance(accountCurrency);

        this.paymentService = new PaymentService("DebitCards");
    }

    public void setAccountBalance(double newBalance) {
        // Set an opening balance
        this.accountBalance = newBalance;
    }

    public double getAccountBalance() {
        return this.accountBalance;
    }

    public boolean verifyPaymentTransaction(double aPayment) {
        // Verify the account has enough
        return (this.accountBalance >= aPayment);
    }

    public void makePayment(double aPayment) {
        if (this.verifyPaymentTransaction(aPayment)) {
            this.accountBalance -= aPayment;
        }
    }

    public double takePayment(double aPayment) {
        this.accountBalance += aPayment;
        return aPayment;
    }

    public boolean addFundsToAccount(double aTopUpAmount, Account sourceAccount) {
        // payment between the two accounts for given amount
        return this.paymentService.processPayment(
                sourceAccount, // aSource
                this, // aTarget
                aTopUpAmount // aAmount
        );
    }

    public boolean addFundsTriangulation(
            double aTopUpAmount, String aCurrency, Account aTopUpAccount) {
        return this.paymentService.processPaymentTriangulation(
                aTopUpAccount, // aSource
                this, // aTarget
                aTopUpAmount, // aAmount
                aCurrency
        );
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public Currency getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String aCurrency) {
        this.accountCurrency = Currency.getInstance(aCurrency);
    }
}
