package PaymentServices;

import Enum.PaymentType;
import Main.Payable;
import TransportServices.TransportService;

import java.util.ArrayList;

public abstract class PaymentService {
    protected static ArrayList<PaymentAccount> listOfAccounts = new ArrayList<>();
    protected double MIN_BALANCE = 0;

    public static void createAccount(String name, double balance, PaymentType payment) {
        listOfAccounts.add(new PaymentAccount(name, balance, payment));
    }

    public static PaymentAccount getAccount(String name, PaymentType payment) {
        for (PaymentAccount account : listOfAccounts) {
            if (account.getName().equals(name) && account.getPaymentType() == payment) {
                return account;
            }
        }
        return null;
    }

    public boolean transact(Payable sender, Payable receiver, double amount) {
        if (sender instanceof TransportService)
            ((TransportService) sender).directPayment(-amount);
        else {
            PaymentAccount senderAccount = this.findAccount(sender.getName());
            if (senderAccount != null && senderAccount.getBalance() - amount > MIN_BALANCE) {
                senderAccount.transact(transactionFee(senderAccount, amount));
            } else {
                //System.out.println("Insufficient balance");
                return false;
            }
        }

        if (receiver instanceof TransportService)
            ((TransportService) receiver).directPayment(amount);
        else {
            PaymentAccount receiverAccount = this.findAccount(receiver.getName());
            receiverAccount.receive(amount);
        }

        return true;
    }

    public abstract PaymentAccount findAccount(String name);

    public abstract double transactionFee(PaymentAccount account, double amount);
}
