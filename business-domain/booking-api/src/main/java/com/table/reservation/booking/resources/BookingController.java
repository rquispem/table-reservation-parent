package com.table.reservation.booking.resources;

import com.table.reservation.booking.common.BookingNotFoundException;
import com.table.reservation.booking.common.DuplicateBookingException;
import com.table.reservation.booking.common.InvalidBookingException;
import com.table.reservation.booking.domain.model.entity.Booking;
import com.table.reservation.booking.domain.model.entity.Entity;
import com.table.reservation.booking.domain.service.BookingService;
import com.table.reservation.booking.domain.valueobject.BookingVO;
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
@RequestMapping("/v1/booking")
@Slf4j
public class BookingController {

    /**
     *
     */
    protected BookingService bookingService;

    /**
     * @param bookingService
     */
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Fetch bookings with the specified name. A partial case-insensitive match is supported. So
     * <code>http://.../booking/rest</code> will find any bookings with upper or lower case 'rest' in
     * their name.
     *
     * @param name
     * @return A non-null, non-empty collection of bookings.
     */
    @GetMapping
    public ResponseEntity<Collection<Booking>> findByName(@RequestParam("name") String name)
            throws Exception {
        log.info(String.format("booking-service findByName() invoked:{} for {} ",
                bookingService.getClass().getName(), name));
        name = name.trim().toLowerCase();
        Collection<Booking> bookings;
        try {
            bookings = bookingService.findByName(name);
        } catch (BookingNotFoundException ex) {
            log.error("Exception raised findByName REST Call {}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Exception raised findByName REST Call {}", ex);
            throw ex;
        }
        return bookings.size() > 0 ? new ResponseEntity<>(bookings, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Fetch bookings with the given id.
     * <code>http://.../v1/bookings/{id}</code> will return booking with given
     * id.
     *
     * @param id
     * @return A non-null, non-empty collection of bookings.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Entity> findById(@PathVariable("id") String id) throws Exception {
        log.info(String.format("booking-service findById() invoked:{} for {} ",
                bookingService.getClass().getName(), id));
        id = id.trim();
        Entity booking;
        try {
            booking = bookingService.findById(id);
        } catch (Exception ex) {
            log.error("Exception raised findById REST Call {}", ex);
            throw ex;
        }
        return booking != null ? new ResponseEntity<>(booking, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Add booking with the specified information.
     *
     * @param bookingVO
     * @return A non-null booking.
     */
    @PostMapping
    public ResponseEntity<Booking> add(@RequestBody BookingVO bookingVO) throws Exception {
        log.info(String
                .format("booking-service add() invoked: %s for %s", bookingService.getClass().getName(),
                        bookingVO.getName()));
        System.out.println(bookingVO);
        Booking booking = Booking.getDummyBooking();
        BeanUtils.copyProperties(bookingVO, booking);
        try {
            bookingService.add(booking);
        } catch (DuplicateBookingException | InvalidBookingException ex) {
            log.error("Exception raised add Restaurant REST Call {}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Exception raised add Booking REST Call {}", ex);
            throw ex;
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
