package com.table.reservation.restaurant.resources;

import com.table.reservation.restaurant.common.DuplicateRestaurantException;
import com.table.reservation.restaurant.common.InvalidRestaurantException;
import com.table.reservation.restaurant.common.RestaurantNotFoundException;
import com.table.reservation.restaurant.domain.model.entity.Entity;
import com.table.reservation.restaurant.domain.model.entity.Restaurant;
import com.table.reservation.restaurant.domain.service.RestaurantService;
import com.table.reservation.restaurant.domain.valueobject.RestaurantVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/v1/restaurants")
@Slf4j
public class RestaurantController {

    /**
     *
     */
    protected RestaurantService restaurantService;

    /**
     * @param restaurantService
     */
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    /**
     * Fetch restaurants with the specified name. A partial case-insensitive match is supported. So
     * <code>http://.../restaurants/rest</code> will find any restaurants with upper or lower case
     * 'rest' in their name.
     *
     * @param name
     * @return A non-null, non-empty collection of restaurants.
     */
    @GetMapping
    public ResponseEntity<Collection<Restaurant>> findByName(@RequestParam("name") String name)
            throws Exception {
        log.info(String.format("restaurant-service findByName() invoked:{} for {} ",
                restaurantService.getClass().getName(), name));
        name = name.trim().toLowerCase();
        Collection<Restaurant> restaurants;
        try {
            restaurants = restaurantService.findByName(name);
        } catch (RestaurantNotFoundException ex) {
            log.warn("Exception raised findByName REST Call {}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Exception raised findByName REST Call {}", ex);
            throw  ex;
        }
        return restaurants.size() > 0 ? new ResponseEntity<>(restaurants, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Fetch restaurants with the given id.
     * <code>http://.../v1/restaurants/{restaurant_id}</code> will return
     * restaurant with given id.
     *
     * @param id
     * @return A non-null, non-empty collection of restaurants.
     */
    @GetMapping(value = "/{restaurant_id}")
    public ResponseEntity<Entity> findById(@PathVariable("restaurant_id") String id) throws Exception {
        log.info(String.format("restaurant-service findById() invoked:{} for {} ",
                restaurantService.getClass().getName(), id));
        id = id.trim();
        Entity restaurant;
        try {
            restaurant = restaurantService.findById(id);
        } catch (Exception ex) {
            log.error("Exception raised findById REST Call {}", ex);
            throw ex;
        }
        return restaurant != null ? new ResponseEntity<>(restaurant, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Add restaurant with the specified information.
     *
     * @param restaurantVO
     * @return A non-null restaurant.
     */
    @PostMapping
    public ResponseEntity<Restaurant> add(@RequestBody RestaurantVO restaurantVO) throws Exception {
        log.info(String.format("restaurant-service add() invoked: %s for %s",
                restaurantService.getClass().getName(), restaurantVO.getName()));
        System.out.println(restaurantVO);
        Restaurant restaurant = Restaurant.getDummyRestaurant();
        BeanUtils.copyProperties(restaurantVO, restaurant);
        try {
            restaurantService.add(restaurant);
        } catch (DuplicateRestaurantException | InvalidRestaurantException ex) {
            log.error("Exception raised add Restaurant REST Call {}");
            throw ex;
        } catch (Exception ex) {
            log.error("Exception raised add Restaurant REST Call {}", ex);
            throw ex;
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
