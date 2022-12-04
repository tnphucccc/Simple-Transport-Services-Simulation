package TransportServices;

import Enum.DStatus;
import Main.App;
import Main.Booking;
import Main.Driver;

import java.util.ArrayList;

public class Gojek extends TransportService {

    private static final ArrayList<Driver> listOfDrivers = new ArrayList<>();

    @Override
    public void directPayment(double amount) {
    }

    @Override
    public double calculateCost(Booking booking) {
        int distance = booking.getDistance();
        if (distance <= 5) return distance * 0.7;
        else return (distance - 5) * 1.2 + 3.5;
    }

    @Override
    public void addDriver(Driver driver) {
        listOfDrivers.add(driver);
    }

    @Override
    public Driver assignRandomDriver() {
        ArrayList<Driver> freeDriverList = new ArrayList<>();
        for (Driver driver : listOfDrivers) {
            if (driver.getStatus() == DStatus.FREE) {
                freeDriverList.add(driver);
            }
        }
        if (freeDriverList.size() == 0) {
            return null;
        }
        return freeDriverList.get(App.generateRandom(0, freeDriverList.size() - 1));
    }

    /*@Override
    public Driver checkForDriver() {
        for (Driver driver : listOfDrivers) {
            if (driver.getStatus() == DStatus.FREE) {
                driver.setStatus(DStatus.PROCESSING);
                return driver;
            }
        }
        return null;
    }*/

    @Override
    public String getName() {
        return "Gojek";
    }
}