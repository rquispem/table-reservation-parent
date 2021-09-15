package com.table.reservation.user.domain.service;

import com.table.reservation.user.common.DuplicateUserException;
import com.table.reservation.user.common.InvalidUserException;
import com.table.reservation.user.domain.model.entity.Entity;
import com.table.reservation.user.domain.model.entity.User;
import com.table.reservation.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BaseService<User, String>
        implements UserService {

    private UserRepository<User, String> userRepository;

    /**
     * @param userRepository
     */
    @Autowired
    public UserServiceImpl(UserRepository<User, String> userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public void add(User user) throws Exception {
        if (userRepository.containsName(user.getName())) {
            Object[] args = {user.getName()};
            throw new DuplicateUserException("duplicateUser", args);
        }
        if (user.getName() == null || "".equals(user.getName())) {
            Object[] args = {"User with null or empty name"};
            throw new InvalidUserException("invalidUser", args);
        }
        super.add(user);
    }

    /**
     * @param name
     */
    @Override
    public Collection<User> findByName(String name) throws Exception {
        return userRepository.findByName(name);
    }

    /**
     * @param user
     */
    @Override
    public void update(User user) throws Exception {
        userRepository.update(user);
    }

    /**
     * @param id
     */
    @Override
    public void delete(String id) throws Exception {
        userRepository.remove(id);
    }

    /**
     * @param id
     */
    @Override
    public Entity findById(String id) throws Exception {
        return userRepository.get(id);
    }

    /**
     * @param name
     */
    @Override
    public Collection<User> findByCriteria(Map<String, ArrayList<String>> name) throws Exception {
        throw new UnsupportedOperationException(
                "Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
