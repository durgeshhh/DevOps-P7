package features;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.junit.Assert;
import revolut.Account;
import revolut.PaymentService;
import revolut.Person;

public class StepDefinitions {

    private static final double openingBalance = 250;

    private Account topUpAccount;
    private double topUpAmount;

    Person danny;

    @Before
    public void setUp() {
        this.danny = new Person("Danny");
        this.topUpAccount = new Account("EUR", openingBalance);

        System.out.println("New Person: " + this.danny.getName());
        System.out.printf("New Top Up Account balance: %s%s%n",
                this.topUpAccount.getAccountCurrency().getSymbol(),
                this.topUpAccount.getBalance());
    }

    @Given("Danny has {double} euro in his euro Revolut account")
    public void dannyHasEuroInHisEuroRevolutAccount(double startingBalance) {
        danny.setAccountBalance(startingBalance);

        System.out.printf("Danny's %s Revolut account balance: %s%s%n",
                danny.getAccount().getAccountCurrency().getDisplayName(),
                danny.getAccount().getAccountCurrency().getSymbol(),
                danny.getAccount().getBalance());
    }

    @Given("Danny selects {double} euro as the topUp amount")
    public void danny_selects_euro_as_the_top_up_amount(double topUpAmount) {
        this.topUpAmount = topUpAmount;

        System.out.println("Top Up Amount : " + this.topUpAmount);
    }

    @Given("Danny selects his {paymentService} as his topUp method")
    public void danny_selects_his_debit_card_as_his_top_up_method(PaymentService aTopUpSource) {
        topUpAccount.setPaymentService(aTopUpSource);

        System.out.printf("Top Up Account Payment Service set to: %s%n",
                topUpAccount.getPaymentService().getType());
    }

    @Given("Danny has a starting balance of {double}")
    public void dannyHasAStartingBalanceOfStartBalance(double startBalance) {
        // Defaults to EUR Account
        danny.setAccountBalance(startBalance);

        System.out.printf("Initial Balance: %s%s%n",
                danny.getAccount().getAccountCurrency().getSymbol(),
                danny.getAccountBalance());
    }

    @Given("Danny has a starting balance of {double} in their {word} account")
    public void dannyHasAStartingBalanceOfStartBalanceInTheirCurrencyAccount(
            double startBalance, String account
    ) {
        danny.setSelectedAccountCode(account); // Will create new account if needed
        danny.addAccount(account);
        danny.setAccountBalance(startBalance); // then apply the opening balance

        System.out.printf("Initial Balance: %s%s%n",
                danny.getAccount().getAccountCurrency().getSymbol(),
                danny.getAccountBalance());
    }

    @And("Danny selects his {word} {paymentService} as his topUp method")
    public void dannySelectsHisEuroDebitCardAsHisTopUpMethod(
            String aCurrency,
            PaymentService aTopUpSource
    ) {
        topUpAccount.setAccountCurrency(aCurrency);
        topUpAccount.setPaymentService(aTopUpSource);

        System.out.printf("Top Up Account Currency set to: %s%n",
                topUpAccount.getAccountCurrency());
        System.out.printf("Top Up Account Payment Service set to: %s%n",
                topUpAccount.getPaymentService().getType());
    }

    // WHEN - ACT

    @When("Danny tops up")
    public void danny_tops_up() {
        danny.getAccount()
                .addFundsToAccount(topUpAmount, topUpAccount);

        System.out.printf("Danny's %s Revolut Account balance: %s%s%n",
                danny.getAccount().getAccountCurrency().getDisplayName(),
                danny.getAccount().getAccountCurrency().getSymbol(),
                danny.getAccount().getBalance());
    }

    @When("Danny now tops up by {double}")
    public void dannyNowTopsUpByTopUpAmount(double topUpAmount) {
        // Defaults to EUR Account
        System.out.println("Topping-Up By: " + topUpAmount);

        danny.getAccount().addFundsToAccount(topUpAmount, topUpAccount);

        System.out.println("Topped-Up Balance: " +
                danny.getAccountBalance());
    }

    @When("Danny now tops up by {double} {word}")
    public void dannyNowTopsUpByTopUpAmountMovementCurrency(
            Double aAmount,
            String aCurrency
    ) {
        danny.getAccount()
                .addFundsTriangulation(aAmount, aCurrency, topUpAccount);
    }

    @When("Danny transfers {int} from their {word} account to their {word} account")
    public void dannyTransfersFromTheirEURAccountToTheirGBPAccount(
            int aAmount, String sourceAccount, String targetAccount) {

        danny.getAccount(targetAccount)
                .addFundsToAccount(aAmount,
                        danny.getAccount(sourceAccount));
    }

    @Then("The new balance of his euro account should now be {double}")
    public void the_new_balance_of_his_euro_account_should_now_be(double newBalance) {
        double actualResult = danny.getAccountBalance();
        Assert.assertEquals(newBalance, actualResult,0.009);
        System.out.println("The new balance is: " + actualResult);
    }

    @Then("The balance in his euro account should be {double}")
    public void theBalanceInHisEuroAccountShouldBeNewBalance(double newBalance) {
        Assert.assertEquals(
                newBalance,
                danny.getAccountBalance(),
                0.009);
    }

    @Then("The balance in their {word} account should be {double}")
    public void theBalanceInTheirEURAccountShouldBe(
            String aAccount, double aBalance) {

        Account tmpAccount = danny.getAccount(aAccount);

        System.out.println("Account: " + aAccount + ", balance: " + tmpAccount.getBalance() +
                ", exp: " + aBalance);

        Assert.assertEquals(
                aBalance,
                tmpAccount.getBalance(),
                0.009); // To the final tenth of a percent

    }
}
