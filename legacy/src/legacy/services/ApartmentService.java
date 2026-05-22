package legacy.services;



import com.intensivecourse.hotel.models.Apartment;
import com.intensivecourse.hotel.repositories.ApartmentRepository;

import java.util.List;
import java.util.Optional;

public class ApartmentService {
    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository){
        this.apartmentRepository = apartmentRepository;
    }

    public List<Apartment> findAll(){
        return apartmentRepository.findAll();
    }

    public Apartment findById(long id) throws IllegalArgumentException{
        Optional<Apartment> optional = apartmentRepository.findById(id);
        Apartment apartment = optional.orElseThrow(
                () -> new IllegalArgumentException("The apartment with number: " + id + " not found")
        );
        return apartment;
    }

    public void saveApartment(Apartment apartment){
        apartmentRepository.save(apartment);
    }

    public void deleteById(long id){
        apartmentRepository.deleteById(id);
    }

    public boolean existsById(long id){
        return apartmentRepository.existsById(id);
    }


    public String getApartmentStatus(long id) throws IllegalArgumentException{
        return findById(id).getReservationStatus().name();
    }
}
