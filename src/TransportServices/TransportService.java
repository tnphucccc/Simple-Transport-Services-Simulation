package TransportServices;

import Enum.CStatus;
import Enum.DStatus;
import Main.Booking;
import Main.Customer;
import Main.Driver;
import Main.Payable;
import PaymentServices.PaymentService;

public abstract class TransportService implements Payable {
    public abstract void directPayment(double amount);

    public abstract double calculateCost(Booking booking);

    public abstract void addDriver(Driver driver);

    public abstract Driver assignRandomDriver();

    //public abstract Driver checkForDriver();

    public synchronized Booking book(Customer customer, int distance, PaymentService paymentService) {
        Driver driver = assignRandomDriver();
        Booking booking;

        if (driver != null && customer.getStatus() != CStatus.BANNED) {
            booking = new Booking(distance, customer, driver, this, paymentService);
            driver.assignBooking(booking);
            driver.setStatus(DStatus.PROCESSING);
            customer.changeStatus(CStatus.PROCESSING);
            return booking;
        } else
            return null;
    }

    public void finish(Booking booking) {
        Customer customer = booking.getCustomer();
        PaymentService paymentService = booking.getPaymentService();
        Driver driver = booking.getDriver();
        double fee = calculateCost(booking);

        booking.setFee(fee);

        if (paymentService.transact(customer, this, fee)) {
            customer.changeStatus(CStatus.BOOKING);
            paymentService.transact(this, driver, fee * 8 / 10);
        } else
            customer.changeStatus(CStatus.BANNED);

        driver.setStatus(DStatus.FREE);
    }
}