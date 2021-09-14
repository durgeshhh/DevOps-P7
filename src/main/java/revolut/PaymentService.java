package revolut;

import java.util.Currency;

public class PaymentService {
    private String serviceName;

    // for easy testing exchange rate added like that
    public static final double EUR_TO_GBP = 0.8;
    public static final double EUR_TO_USD = 1.2;

    public PaymentService(String name){
        this.serviceName = name;
    }

    public String getType() {
        return serviceName;
    }

    public boolean processPayment(
            Account sourceAccount,
            Account targetAccount,
            Double amount) {
        if (sourceAccount.verifyPaymentTransaction(amount)) {

            double exchangeAmount = exchangeCurrency(amount,
                    sourceAccount.getAccountCurrency(), targetAccount.getAccountCurrency());

            System.out.println("From account money: " + amount);
            System.out.println("To account money: " + exchangeAmount);

            sourceAccount.makePayment(amount);
            targetAccount.takePayment(exchangeAmount);

            return true;
        }

        //It is nicer if we throw exception here
        return false;
    }

    public boolean processPaymentTriangulation(
            Account sourceAccount,
            Account targetAccount,
            Double aAmount,
            String aCurrency) {

        double exAmount = PaymentService.exchangeCurrency(aAmount,
                Currency.getInstance(aCurrency),
                sourceAccount.getAccountCurrency());

        return processPayment(sourceAccount, targetAccount, exAmount);
    }

    public static double exchangeCurrency(Double amount, Currency sourceCurrency, Currency targetCurrency) {
        return amount * getRate(sourceCurrency, targetCurrency);
    }

    public static double getRate(Currency accountFrom, Currency accountTo) {

        // "EUR"
        double rate;
        switch (accountFrom.getCurrencyCode()) {
            case "GBP":
                rate = 1 / EUR_TO_GBP;
                break;
            case "USD":
                rate = 1 / EUR_TO_USD;
                break;
            default:
                rate = 1;
                break;
        }

        // Converting currency for target
        switch (accountTo.getCurrencyCode()) {
            case "GBP":
                return rate * EUR_TO_GBP;
            case "USD":
                return rate * EUR_TO_USD;
            default:
                return rate;
        }
    }
}
