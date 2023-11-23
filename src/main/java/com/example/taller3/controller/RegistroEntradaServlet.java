package com.example.taller3.controller;
import com.example.taller3.model.Entrada;
import com.example.taller3.model.data.DBGenerator;
import com.example.taller3.model.data.dao.EntradaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jooq.DSLContext;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "registroEntradaServlet", value = "/registroEntrada")
public class RegistroEntradaServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        try {
            DBGenerator.iniciarBD("entradasDB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher respuesta = req.getRequestDispatcher("/registroEntrada.jsp");
        respuesta.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher respuesta = req.getRequestDispatcher("/registroErroneo.jsp");
        if (req.getParameter("tipoEntrada").length() != 0 && req.getParameter("precio").length() != 0) {
            String tipoEntrada = req.getParameter("tipoEntrada");
            int precio = Integer.parseInt(req.getParameter("precio"));
            String disponibilidad = req.getParameter("dispponibilidad");
            Entrada entrada = new Entrada(tipoEntrada, precio,disponibilidad);
            try {
                if (agregarEntrada(entrada)) {
                    req.setAttribute("entrada", entrada);
                    respuesta = req.getRequestDispatcher("/registroExitoso.jsp");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        respuesta.forward(req, resp);
    }

    private boolean agregarEntrada(Entrada entrada) throws ClassNotFoundException {
        DSLContext query = DBGenerator.conectarBD("entradasDB");
        List<Entrada> entradas = EntradaDAO.obtenerEntrada(query, "tipoEntrada", entrada.getTipoEntrada());
        if (entradas.size() != 0) {
            return false;
        } else {
            EntradaDAO.agregarEntrada(query, entrada);
            return true;
        }
    }
}
