package com.example.taller3.model.data.dao;
import com.example.taller3.model.Asistente;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.*;
public class AsistenteDAO {
    public static void agregarAsistente(DSLContext query, Asistente asistente) {
        Table tablaAsistente = table(name("Asistente"));
        Field[] columnas = tablaAsistente.fields("informacion_contacto", "preferencias_musicales");
        query.insertInto(tablaAsistente, columnas[0], columnas[1])
                .values(asistente.getInformacionContacto(), asistente.getPreferenciasMusicales())
                .execute();
    }

}
