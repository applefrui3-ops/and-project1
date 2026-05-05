package models;

import java.util.List;
import java.util.Objects;

public class Apartment {
    private long id;
    private Price price;
    private List<Client> clients;
    private ReservationStatus reservationStatus;

    public Apartment(){

    }

    public Apartment(long id, Price price, List<Client> clients, ReservationStatus reservationStatus){
        this.id = id;
        this.price = price;
        this.clients = clients;
        this.reservationStatus = reservationStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return id == apartment.id && Objects.equals(price, apartment.price) && Objects.equals(clients, apartment.clients) && reservationStatus == apartment.reservationStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, clients, reservationStatus);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", price=" + price +
                ", clients=" + clients +
                ", reservationStatus=" + reservationStatus +
                '}';
    }
}
