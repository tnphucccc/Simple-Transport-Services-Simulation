package Main;

import PaymentServices.PaymentService;
import TransportServices.TransportService;

public class Booking {
    private final int distance;
    private final Customer customer;
    private final Driver driver;
    private final TransportService transportService;
    private final PaymentService paymentService;
    private double fee;

    public Booking(int distance, Customer customer, Driver driver, TransportService transportService, PaymentService paymentService) {
        this.distance = distance;
        this.customer = customer;
        this.driver = driver;
        this.transportService = transportService;
        this.paymentService = paymentService;
    }

    public int getDistance() {
        return distance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public TransportService getTransportService() {
        return transportService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
