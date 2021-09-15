package com.table.reservation.restaurant.domain.service;

import com.table.reservation.restaurant.domain.model.entity.Entity;
import com.table.reservation.restaurant.domain.model.entity.Restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface RestaurantService {

    /**
     * @param restaurant
     */
    void add(Restaurant restaurant) throws Exception;

    /**
     * @param restaurant
     */
    void update(Restaurant restaurant) throws Exception;

    /**
     * @param id
     */
    void delete(String id) throws Exception;

    /**
     * @param restaurantId
     */
    Entity findById(String restaurantId) throws Exception;

    /**
     * @param name
     */
    Collection<Restaurant> findByName(String name) throws Exception;

    /**
     * @param name
     */
    Collection<Restaurant> findByCriteria(Map<String, ArrayList<String>> name)
            throws Exception;

}
