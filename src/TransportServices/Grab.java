package TransportServices;

import Enum.DStatus;
import Main.App;
import Main.Booking;
import Main.Driver;

import java.util.ArrayList;

public class Grab extends TransportService {

    private static final ArrayList<Driver> listOfDrivers = new ArrayList<>();

    @Override
    public void directPayment(double amount) {
        // TODO Auto-generated method stub
    }

    @Override
    public double calculateCost(Booking booking) {
        // TODO Auto-generated method stub
        int distance = booking.getDistance();
        if (distance <= 2) return distance * 0.5;
        else return distance - 1;
    }

    @Override
    public void addDriver(Driver driver) {
        // TODO Auto-generated method stub
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
    public synchronized Driver checkForDriver() {
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
        // TODO Auto-generated method stub
        return "Grab";
    }
}