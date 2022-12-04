package Enum;

import Main.Booking;
import TransportServices.TransportService;

public enum CStatus {
    BOOKING {
        @Override
        public String toString() {
            return BOOKING.name() + freeStatus;
        }
    },
    PROCESSING,
    BANNED;

    String freeStatus = "";

    public String toString(Booking booking) {
        switch (this) {
            case BANNED -> {
                return "BANNED by " + booking.getPaymentService().getClass().getSimpleName() + ": $" + booking.getFee();
            }
            case BOOKING -> {
                return BOOKING.toString();
            }
            case PROCESSING -> {
                if (booking == null) {
                    System.out.println("Booking is null");
                    return BOOKING.toString();
                }
                return "Being carried by " + booking.getDriver().getName() + " (" + booking.getDriver().getService().getName() + ")";
            }
        }
        return null;
    }

    public void update(TransportService service) {
        freeStatus = "(" + service.getName() + ")";
    }
}
