package epam.pratsaunik.tickets.service;

import epam.pratsaunik.tickets.exception.ServiceLevelException;

public interface Service {
    Integer getNumberOfRecords() throws ServiceLevelException;
}
