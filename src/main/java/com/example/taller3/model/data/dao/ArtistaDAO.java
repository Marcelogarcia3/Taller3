package com.example.taller3.model.data.dao;

import com.example.taller3.model.Artista;
import model.Estudiante;
import org.jooq.*;
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

    public void modificarEstudiante(DSLContext query, String rut, String columnaTabla, Object dato){
        query.update(DSL.table("Estudiante")).set(DSL.field(columnaTabla),dato).
                where(DSL.field("rut").eq(rut)).execute();
    }
    public List obtenerEstudiante(DSLContext query, String columnaTabla, Object dato){
        Result resultados = query.select().from(DSL.table("Estudiante")).where(DSL.field(columnaTabla).eq(dato)).fetch();
        return obtenerListaArtistas(resultados);
    }
    public void eliminarEstudiante(DSLContext query, String rut){
        Table tablaEstudiante= table(name("Estudiante"));
        query.delete(DSL.table("Estudiante")).where(DSL.field("rut").eq(rut)).execute();
    }
    public List obtenerCursosEstudiante(DSLContext query){
        Table tablaEstudiante= table(name("Estudiante"));
        Result resultados= query.select().from(DSL.table("Curso"))
                .fetch();
        return obtenerListaArtistas(resultados);
    }
    private List obtenerListaArtistas(Result resultados){
        List<Artista> artistas= new ArrayList<>();
        for(int fila=0; fila<resultados.size();fila++){
            String  = (String) resultados.getValue(fila,"matricula");
            String rut = (String) resultados.getValue(fila,"rut");
            String nombre = (String) resultados.getValue(fila,"nombre");
            artistas.add(new Artista(nombreArtistico,apellido, rut, ));
        }
        return artistas;
    }
    public static String[][] obtenerEstudiantesCursoNombre(DSLContext query, String nombre, String codigo){
        Table estudiante = DSL.table("Estudiante");
        Table carrera = DSL.table("Carrera");
        Result resultados = query.select().from(estudiante).join(carrera).on(DSL.field("codigo").eq(DSL.field("codigo_carrera")))
                .where(DSL.field("nombre").eq(nombre)).and(DSL.field("codigo_carrera").eq(codigo)).fetch();
        return exportardatos(resultados);
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

