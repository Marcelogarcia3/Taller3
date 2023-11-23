package com.example.taller3.model.data.dao;

import com.example.taller3.model.Artista;
import com.example.taller3.model.EventoMusical;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.jooq.impl.DSL.*;

public class EventoMusicalDAO {
    public static void agregarEventoMusical(DSLContext query, EventoMusical eventoMusical) {
        Table tablaEvento = table(name("Evento"));
        Field[] columnas = tablaEvento.fields("nombre_evento", "fecha", "lugar");
        query.insertInto(tablaEvento, columnas[0], columnas[1], columnas[2])
                .values(eventoMusical.getNombreEvento(), eventoMusical.getFecha(), eventoMusical.getLugar())
                .execute();
    }

    public static boolean validarExistenciaEventoMusical(DSLContext query,String columnaTabla, Object dato){
        Result resultados = query.select().from(DSL.table("Evento")).where(DSL.field(columnaTabla).eq(dato)).fetch();
        if(resultados.size()>=1){
            return true;
        }
        else{
            return false;
        }
    }
    public static EventoMusical buscarEventoMusical(DSLContext query, Object dato, ArrayList<String> artista){
        Result resultados= (Result) buscarEventoMusical(query, (Object) "nombre_evento", (ArrayList<String>) dato);
        String nombreEvento = (String) resultados.getValue(0,"nombre_Evento");
        Date fecha = (Date) resultados.getValue(0,"fecha");
        String lugar = (String) resultados.getValue(0,"lugar");
        ArrayList<Artista> artistas = (ArrayList<Artista>) resultados.getValue(0,"artista");
        return new EventoMusical(nombreEvento,fecha,lugar,artistas);
    }

    public static List buscarEventoMusical(DSLContext query, String columnaTabla, Object dato){
        Result resultados = query.select().from(DSL.table("EventoMusical")).where(DSL.field(columnaTabla).eq(dato)).fetch();
        return resultados;
    }
    public static Object[] getNombreEventoMusical(DSLContext query){
        Table eventoMusical = DSL.table("EventoMusical");
        Result resultados = query.select(eventoMusical.field("Nombre_evento")).from(eventoMusical).fetch();
        if(resultados.size()==0){
            return new String[]{"Error no hay eventos con ese nombre"};
        }
        else {
            return resultados.getValues("nombre_evento").toArray();
        }
    }

    private static List<EventoMusical> obtenerListaEventoMusical(Result resultados) {
        List<EventoMusical> eventosMusicales = new ArrayList<>();

        for (int fila = 0; fila < resultados.size(); fila++) {
            String nombreEvento = (String) resultados.getValue(fila, "nombreEvento");
            Date fecha = (Date) resultados.getValue(fila, "fecha");
            String lugar = (String) resultados.getValue(fila, "lugar");
            ArrayList<Artista> artistas = new ArrayList<>();

            eventosMusicales.add(new EventoMusical(nombreEvento, fecha, lugar, artistas));
        }

        return eventosMusicales;
    }




    public static List obtenerEventoMusical(DSLContext query, String columnaTabla, Object dato){
        Result resultados = query.select().from(table("Evento")).where(field(columnaTabla).eq(dato)).fetch();
        return obtenerListaEventoMusical(resultados);
    }
}
