package legacy.repositories;

import legacy.db.ConnectionManager;
import com.intensivecourse.hotel.models.Apartment;
import com.intensivecourse.hotel.models.Currency;
import com.intensivecourse.hotel.models.Price;
import com.intensivecourse.hotel.models.ReservationStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcApartmentRepository implements ApartmentRepository {
    @Override
    public Optional<Apartment> findById(long id) {
        String sql = """
                SELECT * FROM apartments a WHERE id = ?
                LEFT JOIN clients c ON a.id = c.apartment_id
                """;
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapApartment(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find apartment by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Apartment> findAll() {
        List<Apartment> apartments = new ArrayList<>();
        String sql = """
                SELECT * FROM apartments a
                LEFT JOIN clients c ON a.id = c.apartment_id
                """;
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                apartments.add(mapApartment(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all apartments", e);
        }
        return apartments;
    }

    @Override
    public void save(Apartment apartment) {
        String sql = """
            INSERT INTO apartments (id, price_value, currency, reservation_status)
            VALUES (?, ?, ?, ?)
            ON CONFLICT (id) DO UPDATE SET
                price_value = EXCLUDED.price_value,
                currency = EXCLUDED.currency,
                reservation_status = EXCLUDED.reservation_status
            """;
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, apartment.getId());
            stmt.setInt(2, apartment.getPrice().getValue());
            stmt.setString(3, apartment.getPrice().getCurrency().name());
            stmt.setString(4, apartment.getReservationStatus().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save apartment: " + apartment.getId(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM apartments WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete apartment: " + id, e);
        }
    }

    @Override
    public boolean existsById(long id) {
        String sql = "SELECT 1 FROM apartments WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check apartment existence: " + id, e);
        }
    }

    private Apartment mapApartment(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        int priceValue = rs.getInt("price_value");
        Currency currency = Currency.valueOf(rs.getString("currency"));
        ReservationStatus status = ReservationStatus.valueOf(rs.getString("reservation_status"));

        Price price = new Price(priceValue, currency);
        return new Apartment(id, price, null, status);
    }
}
