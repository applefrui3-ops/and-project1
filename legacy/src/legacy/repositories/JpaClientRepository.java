package legacy.repositories;


import com.intensivecourse.hotel.models.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaClientRepository implements ClientRepository {

    private final EntityManagerFactory emf;

    public JpaClientRepository(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public Optional<Client> findById(long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return Optional.ofNullable(em.find(Client.class, id));
        }
    }

    @Override
    public List<Client> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT c FROM Client c JOIN FETCH c.apartment", Client.class).getResultList();
        }
    }

    @Override
    public void save(Client client) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try (em) {
            tx.begin();
            em.merge(client);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to save client", e);
        }
    }

    @Override
    public void deleteById(long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try(em){
            tx.begin();
            Client client = em.find(Client.class, id);
            if(client != null){
                em.remove(client);
            }
            tx.commit();
        }catch (Exception e){
            if(tx.isActive()){
                tx.rollback();
            }
            throw  new RuntimeException("Failed to delete client", e);
        }
    }

    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }
}
