package epam.pratsaunik.tickets.service;

import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;

import java.util.List;

public interface UserService {

    User create(User user) throws ServiceLevelException;
    List<User> findAllUsers() throws ServiceLevelException;



}


