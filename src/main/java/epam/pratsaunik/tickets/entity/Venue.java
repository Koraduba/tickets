package epam.pratsaunik.tickets.entity;

public class Venue extends Entity {

    private Long venueId;
    private String name;
    private Integer capacity;
    private String layout;

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Venue venue = (Venue) o;

        if (venueId != null ? !venueId.equals(venue.venueId) : venue.venueId != null) return false;
        if (name != null ? !name.equals(venue.name) : venue.name != null) return false;
        if (capacity != null ? !capacity.equals(venue.capacity) : venue.capacity != null) return false;
        return layout != null ? layout.equals(venue.layout) : venue.layout == null;
    }

    @Override
    public int hashCode() {
        int result = venueId != null ? venueId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (layout != null ? layout.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Venue{");
        sb.append("venueId=").append(venueId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", capacity=").append(capacity);
        sb.append(", layout='").append(layout).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
