package legacy.servlets;

import com.intensivecourse.hotel.models.Apartment;
import com.intensivecourse.hotel.services.ApartmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ApartmentServlet extends BaseServlet {

    private ApartmentService apartmentService;

    @Override
    public void init() {
        this.apartmentService = (ApartmentService) getServletContext().getAttribute("apartmentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        resp.setHeader("Cache-Control", "no-cache");
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                sendJson(resp, this.apartmentService.findAll(), 200);
            } else {
                long id = parseId(pathInfo);
                sendJson(resp, this.apartmentService.findById(id), 200);
            }
        } catch (IllegalArgumentException e) {
            sendError(resp, e.getMessage(), 404);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Cache-Control", "no-cache");
        try {
            Apartment apartment = mapper.readValue(req.getReader(), Apartment.class);
            this.apartmentService.saveApartment(apartment);
            sendJson(resp, apartment, 201);
        } catch (Exception e) {
            sendError(resp, e.getMessage(), 400);
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Cache-Control", "no-cache");
        try {
            long id = parseId(req.getPathInfo());
            this.apartmentService.deleteById(id);
            sendJson(resp, "Deleted", 204);
        } catch (Exception e) {
            sendError(resp, e.getMessage(), 404);
        }
    }
}
