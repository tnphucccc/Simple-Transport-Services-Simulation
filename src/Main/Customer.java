package Main;

import Enum.CStatus;
import Enum.PaymentType;
import PaymentServices.*;
import TransportServices.TransportService;

import java.util.ArrayList;

public class Customer implements Payable {
    private static final ArrayList<Customer> customers = new ArrayList<>();
    private static int ID = 1;
    private final String name;
    private Booking myOrder;
    private CStatus status = CStatus.BOOKING;

    public Customer(double visa, double e_wallet, double i_banking) {
        this.name = "C00" + ID++;
        if (visa > 0) PaymentService.createAccount(name, visa, PaymentType.VISA);
        if (e_wallet > 0) PaymentService.createAccount(name, e_wallet, PaymentType.E_WALLET);
        if (i_banking > 0) PaymentService.createAccount(name, i_banking, PaymentType.I_BANKING);
        customers.add(this);
    }

    public static void startRandom() {
        for (Customer customer : customers) {
            customer.randomBook();
        }
    }

    public static boolean checkAllBanned() {
        boolean check = true;
        for (Customer customer : customers)
            if (customer.getStatus() != CStatus.BANNED) {
                check = false;
                break;
            }

        return check;
    }

    public static void printCustomersInitial() {
        System.out.println("[Customer]");
        System.out.printf("%10s | %10s | %10s | %10s%n", "ID", "Visa", "E-Wallet", "IBanking");
        for (Customer customer : customers) {
            PaymentAccount visa = Visa.getAccount(customer.name, PaymentType.VISA);
            PaymentAccount eWallet = EWallet.getAccount(customer.name, PaymentType.E_WALLET);
            PaymentAccount iBanking = IBanking.getAccount(customer.name, PaymentType.I_BANKING);

            System.out.printf("%10s | %10.5s | %10.5s | %10.5s%n", customer.name,
                    visa != null ? visa.getBalance() : "___",
                    eWallet != null ? eWallet.getBalance() : "___",
                    iBanking != null ? iBanking.getBalance() : "___");
        }
    }

    public static void printCustomers() {
        System.out.printf("%-10s | %-30s | %-50s |%n", "Customer", "Status", "Balance");
        System.out.println(new String(new char[98]).replace("\0", "-"));
        for (Customer customer : customers)
            System.out.println(customer);
        System.out.println(new String(new char[98]).replace("\0", "-"));
    }

    public void randomBook() {
        Thread thread = new Thread(() -> {
            try {
                while (status != CStatus.BANNED) {
                    PaymentService forBooking;
                    do {
                        forBooking = App.payments[App.generateRandomDI()];
                    } while (forBooking.findAccount(getName()) == null);
                    TransportService service = App.transports[App.generateRandomDI()];
                    this.updateStatus(service);

                    if (!Book(service, App.generateRandom(5, 9), forBooking)) {
                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    public CStatus getStatus() {
        return status;
    }

    public void updateStatus(TransportService service) {
        status.update(service);
    }

    public void changeStatus(CStatus status) {
        if (this.status != CStatus.BANNED) this.status = status;
    }

    public synchronized boolean Book(TransportService service, int distance, PaymentService payment) throws InterruptedException {
        myOrder = service.book(this, distance, payment);
        if (myOrder == null) return false;
        else return myOrder.getDriver().ride();
    }

    @Override
    public String toString() {
        PaymentAccount visa = PaymentService.getAccount(name, PaymentType.VISA);
        PaymentAccount e_wallet = PaymentService.getAccount(name, PaymentType.E_WALLET);
        PaymentAccount i_banking = PaymentService.getAccount(name, PaymentType.I_BANKING);

        double visaBalance = visa != null ? visa.getBalance() : 0;
        double eWalletBalance = e_wallet != null ? e_wallet.getBalance() : 0;
        double iBankingBalance = i_banking != null ? i_banking.getBalance() : 0;
        double sum = visaBalance + eWalletBalance + iBankingBalance;

        return String.format("%-10s | %-30s | %-5.5s (Visa: %-5.5s E-Wallet: %-5.5s IBanking %-5.5s) |",
                name,
                status.toString(myOrder),
                sum,
                visaBalance,
                eWalletBalance,
                iBankingBalance);
    }
}
