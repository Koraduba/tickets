package epam.pratsaunik.tickets.entity;

public class Ticket extends Entity {

    private Long ticketId;
    private TicketCat category;
    private Event event;
    private Long price;

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
