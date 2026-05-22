package legacy.repositories;

import legacy.db.ConnectionManager;
import com.intensivecourse.hotel.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcClientRepository implements ClientRepository {
    @Override
    public Optional<Client> findById(long id) {
        String sql = """
                SELECT * FROM clients c WHERE id = ?
                LEFT JOIN apartments a ON c.apartment_id = a.id
                """;
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapClient(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find client by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = """
                SELECT * FROM clients c
                LEFT JOIN apartments a ON c.apartment_id = a.id
                """;
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(mapClient(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all clients", e);
        }
        return clients;
    }

    @Override
    public void save(Client client) {
        String sql;
        if (client.getId() == 0) {
            sql = """
                    INSERT INTO clients (name, apartment_id)
                    VALUES (?, ?)
                    """;
        } else {
            sql = """
                    INSERT INTO clients (id, name, apartment_id)
                    VALUES (?, ?, ?)
                    ON CONFLICT (id) DO UPDATE SET
                        name = EXCLUDED.name,
                        apartment_id = EXCLUDED.apartment_id
                    """;
        }

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if(client.getId() == 0){
                stmt.setString(1, client.getName());
                if (client.getApartment() != null) {
                    stmt.setLong(2, client.getApartment().getId());
                } else {
                    stmt.setNull(2, Types.BIGINT);
                }
            }else {
                stmt.setLong(1, client.getId());
                stmt.setString(2, client.getName());
                if (client.getApartment() != null) {
                    stmt.setLong(3, client.getApartment().getId());
                } else {
                    stmt.setNull(3, Types.BIGINT);
                }
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save client: " + client.getId(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM clients WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete client: " + id, e);
        }
    }

    @Override
    public boolean existsById(long id) {
        String sql = "SELECT 1 FROM clients WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check client existence: " + id, e);
        }
    }

    private Client mapClient(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        long apartmentId = rs.getLong("apartment_id");

        Client client = new Client(id, name);
        if (apartmentId != 0) {
            int value = rs.getInt("price_value");
            Currency currency = Currency.valueOf(rs.getString("currency"));
            ReservationStatus status = ReservationStatus.valueOf(rs.getString("reservation_status"));
            client.setApartment(new Apartment(apartmentId, new Price(value, currency), status));
        }
        return client;
    }
}
