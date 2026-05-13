package com.intensivecourse.hotel.servlets;

import com.intensivecourse.hotel.models.Client;
import com.intensivecourse.hotel.services.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientServlet extends BaseServlet {
    ClientService clientService;

    @Override
    public void init(){
        this.clientService = (ClientService) getServletContext().getAttribute("clientService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        resp.setHeader("Cache-Control", "public, max-age=3600");
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                sendJson(resp, clientService.findAll(), 200);
            } else {
                long id = parseId(pathInfo);
                sendJson(resp, clientService.findById(id), 200);
            }
        } catch (IllegalArgumentException e) {
            resp.setHeader("Cache-Control", "no-cache");
            sendError(resp, e.getMessage(), 404);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Cache-Control", "no-cache");
        try {
            Client client = mapper.readValue(req.getReader(), Client.class);
            clientService.saveClient(client);
            sendJson(resp, client, 201);
        } catch (Exception e) {
            sendError(resp, e.getMessage(), 400);
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Cache-Control", "no-cache");
        try {
            long id = parseId(req.getPathInfo());
            clientService.deleteById(id);
            sendJson(resp, "Deleted", 204);
        } catch (Exception e) {
            sendError(resp, e.getMessage(), 404);
        }
    }
}
