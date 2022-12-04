package PaymentServices;

import Enum.PaymentType;

public class PaymentAccount {
    private final String name;
    private final PaymentType payment;
    private double balance;
    private int transactionNo = 1;

    public PaymentAccount(String name, double balance, PaymentType payment) {
        this.name = name;
        this.balance = balance;
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public PaymentType getPaymentType() {
        return payment;
    }

    public double getBalance() {
        return balance;
    }

    public int getTransactionNo() {
        return transactionNo;
    }

    public void transact(double amount) {
        balance -= amount;
        transactionNo++;
    }

    public void receive(double amount) {
        balance += amount;
    }
}
