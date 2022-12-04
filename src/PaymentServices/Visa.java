package PaymentServices;

import Enum.PaymentType;

public class Visa extends PaymentService {

    public Visa() {
        MIN_BALANCE = -10;
    }

    @Override
    public PaymentAccount findAccount(String name) {
        // TODO Auto-generated method stub
        for (PaymentAccount account : listOfAccounts) {
            if (account.getName().equals(name) && account.getPaymentType() == PaymentType.VISA) {
                return account;
            }
        }
        return null;
    }

    @Override
    public double transactionFee(PaymentAccount account, double amount) {
        // TODO Auto-generated method stub
        return amount * 1.01;
    }

    /*public PaymentType getPaymentType() {
        return PaymentType.VISA;
    }*/
}