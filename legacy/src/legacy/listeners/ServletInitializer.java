package legacy.listeners;

import legacy.config.AppConfig;
import legacy.db.ConnectionManager;
import com.intensivecourse.hotel.models.Currency;
import com.intensivecourse.hotel.models.ReservationStatus;
import com.intensivecourse.hotel.repositories.*;
import com.intensivecourse.hotel.services.ApartmentService;
import com.intensivecourse.hotel.services.ClientService;
import legacy.util.DataGenerator;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.util.HashMap;
import java.util.Map;

public class ServletInitializer implements ServletContextListener {

    private final String SERIALIZED_APARTMENTS_PATH;
    private final String SERIALIZED_CLIENTS_PATH;
    private final int APARTMENT_BASE_PRICE;
    private final Currency APARTMENT_BASE_CURRENCY;
    private final ReservationStatus APARTMENT_BASE_STATUS;
    private final boolean CHANGE_APARTMENT_STATUS_ENABLED;

    private final DataGenerator dataGenerator;
    private final EntityManagerFactory emf;

    private final ClientService clientService;
    private final ApartmentService apartmentService;


    public ServletInitializer(){
        AppConfig appConfig = new AppConfig();

        SERIALIZED_APARTMENTS_PATH = appConfig.getProperty("serialized.apartment.path");
        SERIALIZED_CLIENTS_PATH = appConfig.getProperty("serialized.client.path");
        APARTMENT_BASE_PRICE = appConfig.getIntProperty("apartment.base.price");
        APARTMENT_BASE_CURRENCY = appConfig.getEnumProperty("apartment.base.currency", Currency.class);
        APARTMENT_BASE_STATUS = appConfig.getEnumProperty("apartment.base.status", ReservationStatus.class);
        CHANGE_APARTMENT_STATUS_ENABLED = appConfig.getBoolProperty("apartment.status.change");

        dataGenerator = new DataGenerator(APARTMENT_BASE_PRICE, APARTMENT_BASE_CURRENCY, APARTMENT_BASE_STATUS);

        Map<String, String> persistenceProps = new HashMap<>();
        persistenceProps.put("jakarta.persistence.jdbc.url", appConfig.getProperty("db.url"));
        persistenceProps.put("jakarta.persistence.jdbc.user", appConfig.getProperty("db.username"));
        persistenceProps.put("jakarta.persistence.jdbc.password", appConfig.getProperty("db.password"));
        persistenceProps.put("jakarta.persistence.jdbc.driver", appConfig.getProperty("db.driver.name"));
        emf = Persistence.createEntityManagerFactory("hotelapp-pu", persistenceProps);

        ClientRepository clientRepository = new JdbcClientRepository();
        clientService = new ClientService(clientRepository);

        ApartmentRepository apartmentRepository = new JdbcApartmentRepository();
        apartmentService = new ApartmentService(apartmentRepository);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce){
            ServletContext ctx = sce.getServletContext();

            AppConfig config = new AppConfig();
            ConnectionManager.init(config);

            ctx.setAttribute("clientService", clientService);
            ctx.setAttribute("apartmentService", apartmentService);
            ctx.setAttribute("dataGenerator", dataGenerator);

            System.out.println("Servlet context is initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        System.out.println("Servlet context is destroyed");
    }
}
