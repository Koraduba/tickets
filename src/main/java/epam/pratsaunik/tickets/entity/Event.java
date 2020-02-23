package epam.pratsaunik.tickets.entity;

import javax.naming.ldap.PagedResultsControl;

import java.sql.Time;
import java.util.Date;
import java.util.GregorianCalendar;


public class Event extends Entity {

    private Long eventId;
    private String name;
    private String date;
    private String time;
    private String description;
    private String image;
    private Venue venue;
    private User owner;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Event)) return false;
        Event other = (Event) obj;
        return ((this.eventId == null && other.eventId == null) || (this.eventId != null && this.eventId.equals(other.eventId)))
                && ( (this.date == null && other.date == null)||(this.date!=null&&this.date.equals(other.date)))
                && ( (this.name == null && other.name == null)||(this.name!=null&&this.name.equals(other.name)))
                && ( (this.description == null && other.description == null)||(this.description!=null&&this.description.equals(other.description)))
                && ((this.venue == null && other.venue == null)||(this.venue!=null&&this.venue.equals(other.venue)));
    }

    @Override
    public int hashCode (){
        int hash=7;
        hash=hash*31+(eventId==null?0:eventId.hashCode());
        hash=hash*31+(name==null?0:name.hashCode());
        hash=hash*31+(description==null?0:description.hashCode());
        hash=hash*31+(venue==null?0:venue.hashCode());
        hash=hash*31+(date==null?0:date.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Event{");
        sb.append("eventId=").append(eventId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", date=").append(date);
        sb.append(", description='").append(description).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", venue=").append(venue);
        sb.append('}');
        return sb.toString();
    }
}
