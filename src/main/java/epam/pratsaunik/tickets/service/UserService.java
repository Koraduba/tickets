package epam.pratsaunik.tickets.service;

import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;

import java.util.List;

public interface UserService {

    long create(User user) throws ServiceLevelException;

    boolean update (User user) throws ServiceLevelException;

    List<User> findAllUsers() throws ServiceLevelException;

    List<User> findRange(int currentPage, int recordsPerPage) throws ServiceLevelException;

    User createAdmin() throws ServiceLevelException;

    boolean delete(User user) throws ServiceLevelException;

}


