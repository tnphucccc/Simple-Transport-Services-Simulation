package Main;

import PaymentServices.EWallet;
import PaymentServices.IBanking;
import PaymentServices.PaymentService;
import PaymentServices.Visa;
import TransportServices.Be;
import TransportServices.Gojek;
import TransportServices.Grab;
import TransportServices.TransportService;

import java.util.Random;

public class App {

    public static Grab grabDI = new Grab();
    public static Gojek gojekDI = new Gojek();
    public static Be beDI = new Be();
    public static TransportService[] transports = {grabDI, gojekDI, beDI};

    public static Visa visaDI = new Visa();
    public static EWallet eWalletDI = new EWallet();
    public static IBanking iBankingDI = new IBanking();
    public static PaymentService[] payments = {visaDI, eWalletDI, iBankingDI};

    public static void main(String[] args) {
        createCustomers(5);
        createDrivers(5);
        // Thread to print System Status
        startStatusThread();
        // Start random customer
        Customer.startRandom();
    }

    public static int generateRandomDI() {
        Random rand = new Random();
        return rand.nextInt(3);
    }

    public static int generateRandom(int min, int max) {
        Random rand = new Random();
        return min + rand.nextInt(max - min + 1);
    }

    public static int generateRandomBalance() {
        if (generateRandom(0, 4) == 0)
            return 0;
        else
            return generateRandom(10, 20);
    }

    public static void generateRandomDriver() {
        new Driver(transports[generateRandomDI()]);
    }

    public static void createDrivers(int expected) {
        new Driver(grabDI);
        new Driver(gojekDI);
        new Driver(beDI);
        for (int i = 0; i < generateRandom(expected - 3, expected + 1); i++)
            generateRandomDriver();
    }

    public static void generateRandomCustomer() {
        new Customer(generateRandomBalance(), generateRandomBalance(), generateRandomBalance());
    }

    public static void createCustomers(int expected) {
        for (int i = 0; i < generateRandom(expected, expected + 2); i++)
            generateRandomCustomer();
    }

    public static void startStatusThread() {
        Thread thread = new Thread(() -> {
            printInitial();
            do {
                printAll();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!Customer.checkAllBanned());
            printAll();
        });
        thread.start();
    }

    public static void printInitial() {
        System.out.println("\n" + new String(new char[42]).replace("\0", "*") + "Initial State" + new String(new char[43]).replace("\0", "*"));
        Customer.printCustomersInitial();
        Driver.printDriversInitial();
        System.out.println(new String(new char[98]).replace("\0", "*") + "\n");
    }

    public static void printAll() {
        System.out.println("\n" + new String(new char[42]).replace("\0", "=") + "System Status" + new String(new char[43]).replace("\0", "="));
        Customer.printCustomers();
        Driver.printDrivers();
        System.out.println(new String(new char[98]).replace("\0", "=") + "\n");
    }
}
