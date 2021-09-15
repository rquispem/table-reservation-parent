package com.table.reservation.booking.common;

public class InvalidBookingException extends Exception {

    private String message;
    private Object[] args;

    public InvalidBookingException(String arg) {
        this.message = String.format("%s is an invalid booking.", arg);
    }

    public InvalidBookingException(Object[] args) {
        this.args = args;
    }

    public InvalidBookingException(String message, Object[] args) {
        this.message = message;
        this.args = args;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the args
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }
}
