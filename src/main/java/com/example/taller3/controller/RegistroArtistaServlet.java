package com.example.taller3.controller;

import com.example.taller3.model.Artista;
import com.example.taller3.model.data.DBGenerator;
import com.example.taller3.model.data.dao.ArtistaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jooq.DSLContext;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "registroArtistaServlet", value = "/registroArtista")
public class RegistroArtistaServlet extends HttpServlet {
    @Override

    public void init() throws ServletException {
        try {
            DBGenerator.iniciarBD("artistaDB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //La respuesta que le vamos a devolver, va a ser la dirección del archivo JSP registroUsuario.jsp
        RequestDispatcher respuesta = req.getRequestDispatcher("/registroArtista.jsp");
        //envía la respuesta
        respuesta.forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        RequestDispatcher respuesta = req.getRequestDispatcher("/registroErroneo.jsp");
        if(req.getParameter("nombreArtistico").length()!=0 && req.getParameter("generoMusical").length()!=0){
            String nombreArtistico = req.getParameter("nombreArtisitico");
            String generoMusical = req.getParameter("generoMusical");
            Artista artista = new Artista(nombreArtistico, generoMusical);
            try {
                if(agregarArtista(artista)){
                    req.setAttribute("artista",artista);
                    respuesta = req.getRequestDispatcher("/registroExitoso.jsp");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        respuesta.forward(req,resp);
    }
    private boolean agregarArtista(Artista artista) throws ClassNotFoundException {
        DSLContext query= DBGenerator.conectarBD("artistasBD");
        List<Artista> artistas = ArtistaDAO.obtenerArtista(query,"nombreArtistico",artista.getNombreArtistico());
        if(artistas.size()!=0){
            return false;
        }
        else{
            ArtistaDAO.agregarArtista(query, (Artista) artistas);
            return true;
        }
    }
}