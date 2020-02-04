package epam.pratsaunik.tickets.entity;

import java.util.Date;

public class Order extends Entity {

    private Long orderId;
    private User user;
    private Date date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
