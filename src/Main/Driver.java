package Main;

import Enum.DStatus;
import Enum.PaymentType;
import PaymentServices.PaymentAccount;
import PaymentServices.PaymentService;
import TransportServices.TransportService;

import java.util.ArrayList;

public class Driver implements Payable {
    private static final ArrayList<Driver> drivers = new ArrayList<>();
    private static int ID = 1;
    private final String name;
    private final TransportService service;
    private Booking currentOrder;
    private DStatus status = DStatus.FREE;

    public Driver(TransportService service) {
        this.name = "D00" + ID++;
        this.service = service;
        service.addDriver(this);

        PaymentService.createAccount(name, 0, PaymentType.VISA);
        PaymentService.createAccount(name, 0, PaymentType.E_WALLET);
        PaymentService.createAccount(name, 0, PaymentType.I_BANKING);
        drivers.add(this);
    }

    public static void printDriversInitial() {
        System.out.println("[Driver]");
        System.out.printf("%10s | %10s%n", "ID", "Service");
        for (Driver driver : drivers)
            System.out.printf("%10s | %10s%n", driver.getName(), driver.getService().getName());
    }

    public static void printDrivers() {
        System.out.printf("%-10s | %-10s | %-17s | %-50s |%n", "Driver", "Service", "Status", "Balance");
        System.out.println(new String(new char[98]).replace("\0", "-"));
        for (Driver driver : drivers)
            System.out.println(driver);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    public DStatus getStatus() {
        return status;
    }

    public void setStatus(DStatus status) {
        this.status = status;
    }

    public TransportService getService() {
        return service;
    }

    public void assignBooking(Booking booking) {
        this.currentOrder = booking;
    }

    public synchronized boolean ride() throws InterruptedException {
        if (currentOrder != null) {
            for (double i = 1; i <= currentOrder.getDistance(); i += 1) {
                Thread.sleep(250);
            }
            service.finish(currentOrder);
        } else
            return false;
        currentOrder = null;
        return true;
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

        return String.format("%-10s | %-10s | %-17s | %-5.5s (Visa: %-5.5s E-Wallet: %-5.5s IBanking %-5.5s) |",
                name,
                getService().getName(),
                status.toString(currentOrder),
                sum,
                visaBalance,
                eWalletBalance,
                iBankingBalance);
    }
}
