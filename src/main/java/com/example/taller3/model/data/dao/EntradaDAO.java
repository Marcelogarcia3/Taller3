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

    private List<Entrada> obtenerListaEntradas(Result<Record> resultados) {
        List<Entrada> entradas = new ArrayList<>();

        for (Record record : resultados) {
            String tipoEntrada = record.get("tipo_entrada", String.class);
            int precio = record.get("precio", int.class);
            String disponibilidad = record.get("disponibilidad", String.class);

            entradas.add(new Entrada(tipoEntrada, precio, disponibilidad));
        }

        return entradas;
    }

    private static String[][] exportarDatos(Result resultados) {
        String[][] datosResultado = new String[resultados.size()][3];
        for (int registro = 0; registro < resultados.size(); registro++) {
            datosResultado[registro][0] = (String) resultados.getValue(registro, "tipo_entrada");
            datosResultado[registro][1] = String.valueOf(resultados.getValue(registro, "precio"));
            datosResultado[registro][2] = String.valueOf(resultados.getValue(registro, "disponibilidad"));
        }
        return datosResultado;
    }

    public static boolean validarExistenciaEntrada(DSLContext query, String columnaTabla, Object dato) {
        Result resultados = query.select().from(DSL.table("Entrada")).where(DSL.field(columnaTabla).eq(dato)).fetch();
        return resultados.size() >= 1;
    }
}
