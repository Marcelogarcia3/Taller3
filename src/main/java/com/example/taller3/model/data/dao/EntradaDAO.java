package com.example.taller3.model.data.dao;
import com.example.taller3.model.Entrada;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.*;
public class EntradaDAO {

    public static void agregarEntrada(DSLContext query, Entrada entrada) {
        Table tablaEntrada = table(name("Entrada"));
        Field[] columnas = tablaEntrada.fields("tipo_entrada", "precio", "disponibilidad");
        query.insertInto(tablaEntrada, columnas[0], columnas[1], columnas[2])
                .values(entrada.getTipoEntrada(), entrada.getPrecio(), entrada.getDisponibilidad())
                .execute();
    }
    private static List obtenerListaEntradas(Result resultados) {
        List<Entrada> entradas = new ArrayList<>();
        for (int fila = 0; fila < resultados.size(); fila++) {
            String tipoEntrada = (String) resultados.getValue(fila, "tipoEntrada");
            int precio = (int) resultados.getValue(fila, "precio");
            String disponibilidad = (String) resultados.getValue(fila, "disponibilidad");

            entradas.add(new Entrada(tipoEntrada, precio, disponibilidad));
        }
        return entradas;
    }

    private static String[][] exportarDatos(Result resultados) {
        String[][] datosResultado = new String[resultados.size()][3];
        for (int registro = 0; registro < resultados.size(); registro++) {
            datosResultado[registro][0] = (String) resultados.getValue(registro, "tipoEntrada");
            datosResultado[registro][1] = String.valueOf(resultados.getValue(registro, "precio"));
            datosResultado[registro][2] = (String) resultados.getValue(registro, "disponibilidad");
        }
        return datosResultado;
    }

    public static List obtenerEntrada(DSLContext query, String columnaTabla, Object dato) {
        Result resultados = query.select().from(table("Entrada")).where(field(columnaTabla).eq(dato)).fetch();
        return obtenerListaEntradas(resultados);
    }

}
