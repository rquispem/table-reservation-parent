package com.table.reservation.booking.domain.repository;

import java.util.Collection;

/**
 * @param <Booking>
 * @param <String>
 */
public interface BookingRepository<Booking, String> extends Repository<Booking, String> {

    /**
     * @param name
     */
    boolean containsName(String name);

    /**
     * @param name
     */
    public Collection<Booking> findByName(String name) throws Exception;
}
