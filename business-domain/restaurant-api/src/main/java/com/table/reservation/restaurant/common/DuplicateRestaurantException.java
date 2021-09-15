package com.table.reservation.restaurant.common;

public class DuplicateRestaurantException extends Exception {

    private String message;
    private Object[] args;

    public DuplicateRestaurantException(String name) {
        this.message = String.format("There is already a restaurant with the name - %s", name);
    }

    public DuplicateRestaurantException(Object[] args) {
        this.args = args;
    }

    public DuplicateRestaurantException(String message, Object[] args) {
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