package com.example.taller3.model.data.dao;

import com.example.taller3.model.Artista;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.VARCHAR;

public class ArtistaDAO {

    public static void agregarArtista(DSLContext query, Artista artista) {
        Table tablaArtista = table(name("Artista"));
        Field[] columnas = tablaArtista.fields("nombre_artistico", "genero_musical");
        query.insertInto(tablaArtista, columnas[0], columnas[1])
                .values(artista.getNombreArtistico(), artista.getGeneroMusical())
                .execute();
    }
    private static List obtenerListaArtistas(Result resultados){
        List<Artista> artistas= new ArrayList<>();
        for(int fila=0; fila<resultados.size();fila++){
            String nombreArtistico = (String) resultados.getValue(fila,"nombreArtistico");
            String generoMusical = (String) resultados.getValue(fila,"generoMusical");
            artistas.add(new Artista(nombreArtistico,generoMusical));
        }
        return artistas;
    }



    private static String[][] exportardatos(Result resultados){
        String[][] datosResultado=new String[resultados.size()][4];
        for(int registro = 0; registro < resultados.size(); registro ++){
            datosResultado[registro][0] = (String) resultados.getValue(registro,"nombre");
            datosResultado[registro][1] = (String) resultados.getValue(registro,"matricula");
            datosResultado[registro][2] = (String) resultados.getValue(registro,"nombre_carrera");
            datosResultado[registro][3] = (String) resultados.getValue(registro,"codigo");
        }
        return datosResultado;
    }
    public static List obtenerArtista(DSLContext query, String columnaTabla, Object dato){
        Result resultados = query.select().from(table("Artista")).where(field(columnaTabla).eq(dato)).fetch();
        return obtenerListaArtistas(resultados);
    }

    public static boolean validarExistenciaArtista(DSLContext query,String columnaTabla, Object dato){
        Result resultados = query.select().from(DSL.table("Artista")).where(DSL.field(columnaTabla).eq(dato)).fetch();
        if(resultados.size()>=1){
            return true;
        }
        else{
            return false;
        }
    }
}

