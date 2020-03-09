package epam.pratsaunik.tickets.entity;

public class OrderLine extends Entity {

    private Long orderLineId;
    private Ticket ticket;
    private Integer ticketQuantity;
    private Order order;

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }



    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Integer getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(Integer ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OrderLine{");
        sb.append("orderLineId=").append(orderLineId);
        sb.append(", ticket=").append(ticket);
        sb.append(", ticketQuantity=").append(ticketQuantity);
        sb.append(", order=").append(order);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLine orderLine = (OrderLine) o;

        if (orderLineId != null ? !orderLineId.equals(orderLine.orderLineId) : orderLine.orderLineId != null)
            return false;
        if (ticket != null ? !ticket.equals(orderLine.ticket) : orderLine.ticket != null) return false;
        if (ticketQuantity != null ? !ticketQuantity.equals(orderLine.ticketQuantity) : orderLine.ticketQuantity != null)
            return false;
        return order != null ? order.equals(orderLine.order) : orderLine.order == null;
    }

    @Override
    public int hashCode() {
        int result = orderLineId != null ? orderLineId.hashCode() : 0;
        result = 31 * result + (ticket != null ? ticket.hashCode() : 0);
        result = 31 * result + (ticketQuantity != null ? ticketQuantity.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }
}
