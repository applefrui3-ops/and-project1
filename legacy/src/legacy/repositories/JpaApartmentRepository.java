package legacy.repositories;

import com.intensivecourse.hotel.models.Apartment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaApartmentRepository implements ApartmentRepository {

    private final EntityManagerFactory emf;

    public JpaApartmentRepository(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public Optional<Apartment> findById(long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return Optional.ofNullable(em.find(Apartment.class, id));
        }
    }

    @Override
    public List<Apartment> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT a FROM Apartment a", Apartment.class).getResultList();
        }
    }

    @Override
    public void save(Apartment apartment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try (em) {
            tx.begin();
            em.merge(apartment);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to save apartment", e);
        }
    }

    @Override
    public void deleteById(long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try(em){
            tx.begin();
            Apartment apartment = em.find(Apartment.class, id);
            if(apartment != null){
                em.remove(apartment);
            }
            tx.commit();
        }catch (Exception e){
            if(tx.isActive()){
                tx.rollback();
            }
            throw  new RuntimeException("Failed to delete apartment", e);
        }
    }

    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }
}
