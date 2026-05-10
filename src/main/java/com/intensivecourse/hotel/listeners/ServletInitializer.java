package com.intensivecourse.hotel.listeners;

import com.intensivecourse.hotel.config.AppConfig;
import com.intensivecourse.hotel.models.Currency;
import com.intensivecourse.hotel.models.ReservationStatus;
import com.intensivecourse.hotel.repositories.ApartmentRepository;
import com.intensivecourse.hotel.repositories.ClientRepository;
import com.intensivecourse.hotel.repositories.JsonApartmentRepository;
import com.intensivecourse.hotel.repositories.JsonClientRepository;
import com.intensivecourse.hotel.services.ApartmentService;
import com.intensivecourse.hotel.services.ClientService;
import com.intensivecourse.hotel.util.DataGenerator;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.File;

public class ServletInitializer implements ServletContextListener {

    private final String SERIALIZED_APARTMENTS_PATH;
    private final String SERIALIZED_CLIENTS_PATH;
    private final int APARTMENT_BASE_PRICE;
    private final Currency APARTMENT_BASE_CURRENCY;
    private final ReservationStatus APARTMENT_BASE_STATUS;
    private final boolean CHANGE_APARTMENT_STATUS_ENABLED;

    DataGenerator dataGenerator;
    ClientService clientService;
    ApartmentService apartmentService;


    public ServletInitializer(){
        AppConfig appConfig = new AppConfig();

        SERIALIZED_APARTMENTS_PATH = appConfig.getProperty("serialized.apartment.path");
        SERIALIZED_CLIENTS_PATH = appConfig.getProperty("serialized.client.path");
        APARTMENT_BASE_PRICE = appConfig.getIntProperty("apartment.base.price");
        APARTMENT_BASE_CURRENCY = appConfig.getEnumProperty("apartment.base.currency", Currency.class);
        APARTMENT_BASE_STATUS = appConfig.getEnumProperty("apartment.base.status", ReservationStatus.class);
        CHANGE_APARTMENT_STATUS_ENABLED = appConfig.getBoolProperty("apartment.status.change");

        dataGenerator = new DataGenerator(APARTMENT_BASE_PRICE, APARTMENT_BASE_CURRENCY, APARTMENT_BASE_STATUS);

        ClientRepository clientRepository = new JsonClientRepository(SERIALIZED_CLIENTS_PATH);
        clientService = new ClientService(clientRepository);

        ApartmentRepository apartmentRepository = new JsonApartmentRepository(SERIALIZED_APARTMENTS_PATH);
        apartmentService = new ApartmentService(apartmentRepository);

        // Что видит Java как "текущую директорию"
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Working dir: " + new File(".").getAbsolutePath());

// Где реально лежат классы
        System.out.println("Classes: " + getClass().getResource("/"));
        System.out.println("Classes: " + getClass().getResource("/json/"));
    }

    @Override
    public void contextInitialized(ServletContextEvent sce){
        ServletContext ctx = sce.getServletContext();

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
