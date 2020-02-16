package epam.pratsaunik.tickets.entity;

import java.math.BigDecimal;

public class Ticket extends Entity {

    private Long ticketId;
    private TicketCat category;
    private Event event;
    private BigDecimal price;

    public TicketCat getCategory() {
        return category;
    }

    public void setCategory(TicketCat category) {
        this.category = category;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
