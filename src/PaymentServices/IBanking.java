package PaymentServices;

import Enum.PaymentType;

public class IBanking extends PaymentService {
    @Override
    public PaymentAccount findAccount(String name) {
        // TODO Auto-generated method stub
        for (PaymentAccount account : listOfAccounts) {
            if (account.getName().equals(name) && account.getPaymentType() == PaymentType.I_BANKING) {
                return account;
            }
        }
        return null;
    }

    @Override
    public double transactionFee(PaymentAccount account, double amount) {
        // TODO Auto-generated method stub
        return amount * 1.005;
    }

    /*public PaymentType getPaymentType() {
        return PaymentType.I_BANKING;
    }*/
}