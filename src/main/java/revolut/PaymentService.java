package revolut;

public class PaymentService {
    private String serviceName;
    private double balance;

    public PaymentService(String name){
        this.serviceName = name;
    }

    public String getType() {
        return serviceName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double accountBalance) {
        this.balance = accountBalance;
    }
}
