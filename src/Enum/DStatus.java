package Enum;

import Main.Booking;

public enum DStatus {
    FREE,
    PROCESSING {
        @Override
        public String toString(Booking booking) {
            return "Carrying " + booking.getCustomer().getName();
        }
    };

    public String toString(Booking booking) {
        if (this == FREE) return FREE.name();
        else return PROCESSING.toString(booking);
    }
}
