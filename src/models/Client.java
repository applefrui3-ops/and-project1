package models;

import java.util.Objects;

public class Client {
    private long id;
    private String name;
    private Apartment apartment;

    public Client(long id, String name, Apartment apartment){
        this.id = id;
        this.name = name;
        this.apartment = apartment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && Objects.equals(name, client.name) && Objects.equals(apartment, client.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, apartment);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", apartment=" + apartment +
                '}';
    }
}
