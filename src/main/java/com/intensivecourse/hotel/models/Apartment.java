package com.intensivecourse.hotel.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Embedded
    private Price price;
    @JsonIgnoreProperties("apartment")
    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Client> clients;
    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;

    public Apartment() {

    }

    public Apartment(long id) {
        this.id = id;
    }

    public Apartment(long id, Price price, ReservationStatus reservationStatus) {
        this.id = id;
        this.price = price;
        this.reservationStatus = reservationStatus;
    }

    public Apartment(long id, Price price, List<Client> clients, ReservationStatus reservationStatus) {
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
