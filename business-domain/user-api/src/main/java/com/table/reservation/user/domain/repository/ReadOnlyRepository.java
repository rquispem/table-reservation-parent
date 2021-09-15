package com.table.reservation.user.domain.repository;

import java.util.Collection;

public interface ReadOnlyRepository<TE, T> {

    //long Count;

    /**
     * @param id
     */
    boolean contains(T id);

    /**
     * @param id
     */
    TE get(T id);

    /**
     *
     * @return
     */
    Collection<TE> getAll();
}
