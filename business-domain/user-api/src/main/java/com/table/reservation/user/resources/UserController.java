package com.table.reservation.user.resources;

import com.table.reservation.user.common.DuplicateUserException;
import com.table.reservation.user.common.InvalidUserException;
import com.table.reservation.user.common.UserNotFoundException;
import com.table.reservation.user.domain.model.entity.Entity;
import com.table.reservation.user.domain.model.entity.User;
import com.table.reservation.user.domain.service.UserService;
import com.table.reservation.user.domain.valueobject.UserVO;
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
@RequestMapping("/v1/user")
@Slf4j
public class UserController {

    protected UserService userService;

    /**
     * @param userService
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Fetch users with the specified name. A partial case-insensitive match is supported. So
     * <code>http://.../user/rest</code> will find any users with upper or lower case 'rest' in their
     * name.
     *
     * @param name
     * @return A non-null, non-empty collection of users.
     */
    @GetMapping
    public ResponseEntity<Collection<User>> findByName(@RequestParam("name") String name)
            throws Exception {
        log.info(String
                .format("user-service findByName() invoked:{} for {} ", userService.getClass().getName(),
                        name));
        name = name.trim().toLowerCase();
        Collection<User> users;
        try {
            users = userService.findByName(name);
        } catch (UserNotFoundException ex) {
            log.error( "Exception raised findByName REST Call {}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Exception raised findByName REST Call {}", ex);
            throw ex;
        }
        return users.size() > 0 ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Fetch users with the given id. <code>http://.../v1/users/{id}</code> will return user with
     * given id.
     *
     * @param id
     * @return A non-null, non-empty collection of users.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Entity> findById(@PathVariable("id") String id) throws Exception {
        log.info(String
                .format("user-service findById() invoked:{} for {} ", userService.getClass().getName(),
                        id));
        id = id.trim();
        Entity user;
        try {
            user = userService.findById(id);
        } catch (Exception ex) {
            log.error("Exception raised findById REST Call {}", ex);
            throw ex;
        }
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Add user with the specified information.
     *
     * @param userVO
     * @return A non-null user.
     */
    @PostMapping
    public ResponseEntity<User> add(@RequestBody UserVO userVO) throws Exception {
        log.info(String
                .format("user-service add() invoked: %s for %s", userService.getClass().getName(),
                        userVO.getName()));
        System.out.println(userVO);
        User user = User.getDummyUser();
        BeanUtils.copyProperties(userVO, user);
        try {
            userService.add(user);
        } catch (DuplicateUserException | InvalidUserException ex) {
            log.error("Exception raised add Restaurant REST Call {}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Exception raised add Booking REST Call {}", ex);
            throw ex;
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
