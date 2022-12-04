package PaymentServices;

import Enum.PaymentType;

public class EWallet extends PaymentService {
    @Override
    public PaymentAccount findAccount(String name) {
        // TODO Auto-generated method stub
        for (PaymentAccount account : listOfAccounts) {
            if (account.getName().equals(name) && account.getPaymentType() == PaymentType.E_WALLET) {
                return account;
            }
        }
        return null;
    }

    @Override
    public double transactionFee(PaymentAccount account, double amount) {
        // TODO Auto-generated method stub
        return account.getTransactionNo() % 3 == 0 ? amount * 1.005 : amount * 1.008;
    }

    /*public PaymentType getPaymentType() {
        return PaymentType.E_WALLET;
    }*/
}