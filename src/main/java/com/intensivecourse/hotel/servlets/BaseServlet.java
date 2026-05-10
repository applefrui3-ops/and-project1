package com.intensivecourse.hotel.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class BaseServlet extends HttpServlet {

    protected final ObjectMapper mapper = new ObjectMapper();

    protected void sendJson(HttpServletResponse resp, Object data, int status) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(status);
        PrintWriter writer = resp.getWriter();
        mapper.writeValue(writer, data);
    }

    protected void sendError(HttpServletResponse resp, String message, int status) throws IOException{
        resp.setContentType("application/json");
        resp.setStatus(status);
        PrintWriter writer = resp.getWriter();
        mapper.writeValue(writer, new ErrorResponse(message));
    }

    protected long parseId(String pathInfo){
        if(pathInfo == null || pathInfo.equals("/")){
            throw new IllegalArgumentException("Missing Id");
        }
        return Long.parseLong(pathInfo.substring(1));
    }

    protected record ErrorResponse(String error) {}
}
