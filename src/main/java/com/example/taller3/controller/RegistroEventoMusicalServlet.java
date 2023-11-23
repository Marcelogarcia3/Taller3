package com.example.taller3.controller;
import com.example.taller3.model.EventoMusical;
import com.example.taller3.model.data.DBGenerator;
import com.example.taller3.model.data.dao.EventoMusicalDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jooq.DSLContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "registroEventoMusicalServlet", value = "/registroEventoMusical")
public class RegistroEventoMusicalServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            DBGenerator.iniciarBD("eventosMusicalesDB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher respuesta = req.getRequestDispatcher("/registroEventoMusical.jsp");
        respuesta.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher respuesta = req.getRequestDispatcher("/registroErroneo.jsp");
        if (req.getParameter("nombreEvento").length() != 0 && req.getParameter("fecha").length() != 0
                && req.getParameter("lugar").length() != 0 && req.getParameter("nombreArtista").length() != 0) {

            String nombreEvento = req.getParameter("nombreEvento");
            String fechaStr = req.getParameter("fecha");
            Date fecha = null;

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                fecha = new java.sql.Date(dateFormat.parse(fechaStr).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String lugar = req.getParameter("lugar");
            String nombreArtista = req.getParameter("nombreArtista");

            List<String> artistas = new ArrayList<>();
            artistas.add(nombreArtista);

            EventoMusical eventoMusical = new EventoMusical(nombreEvento, fecha, lugar, (ArrayList<String>) artistas);

            try {
                if (agregarEventoMusical(eventoMusical)) {
                    req.setAttribute("eventoMusical", eventoMusical);
                    respuesta = req.getRequestDispatcher("/registroExitoso.jsp");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        respuesta.forward(req, resp);
    }


    private boolean agregarEventoMusical(EventoMusical eventoMusical) throws ClassNotFoundException {
        DSLContext query = DBGenerator.conectarBD("eventosMusicalesDB");
        List<EventoMusical> eventosMusicales = EventoMusicalDAO.obtenerEventoMusical(query, "nombre_evento", eventoMusical.getNombreEvento());
        if (eventosMusicales.size() != 0) {
            return false;
        } else {
            EventoMusicalDAO.agregarEventoMusical(query, eventoMusical);
            return true;
        }
    }
}
