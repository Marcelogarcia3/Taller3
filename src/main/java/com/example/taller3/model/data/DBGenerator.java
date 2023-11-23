package com.example.taller3.model.data;

import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static org.jooq.impl.DSL.foreignKey;
import static org.jooq.impl.DSL.primaryKey;
import static org.jooq.impl.SQLDataType.*;

public class DBGenerator {
    //Metodo inicial para crear una base de datos y sus respectivas tablas en caso de que no exista
    public static void iniciarBD(String EventoDB) throws ClassNotFoundException {
        Connection connection = DBConnector.connection("root","");
        DSLContext create = DSL.using(connection);
        crearBaseDato(create,EventoDB);
        create = actualizarConexion(connection,EventoDB);
        crearTablaEventoMusical(create);
        crearTablaArtista(create);
        crearTablaAsistente(create);
        crearTablaEntrada(create);
        relacionarTabla(create,"Artista","nombreArtistico","Evento");
        DBConnector.closeConnection();
    }
    //Metodo para conectarse a una base de datos ya creada
    public static DSLContext conectarBD(String nombre) throws ClassNotFoundException {
        Connection connection = DBConnector.connection(nombre,"root","");
        DSLContext create = DSL.using(connection);
        return create;
    }
    //Crea una base de datos en caso de que no exista
    private static void crearBaseDato(DSLContext create, String UniversidadDB){
        create.createDatabaseIfNotExists(UniversidadDB).execute();
    }
    //Actualiza la conexion inicial para conectar a la base de datos
    private static DSLContext actualizarConexion(Connection connection,String UniversidadDB){
        DBConnector.closeConnection();
        connection= DBConnector.connection(UniversidadDB,"root","");
        DSLContext create=DSL.using(connection);
        return create;
    }
    //Método estandar para crear una tabla

    private static void crearTablaArtista(DSLContext create) {
        // Crear tabla Artista
        create.createTableIfNotExists("Artista")
                .column("nombre_artistico", VARCHAR(100))
                .column("genero_musical", VARCHAR(50))
                .constraint(primaryKey("nombre_artistico"))
                .execute();
    }

    private static void crearTablaEventoMusical(DSLContext create) {
        // Crear tabla Evento
        create.createTableIfNotExists("Evento")
                .column("nombre_evento", VARCHAR(100))
                .column("fecha", DATE)
                .column("lugar", VARCHAR(100))
                .constraint(primaryKey("nombre_evento"))
                .execute();
    }
    private static void crearTablaAsistente(DSLContext create) {
        // Crear tabla Asistente
        create.createTableIfNotExists("Asistente")
                .column("informacion_contacto", VARCHAR(100))
                .column("preferencias_musicales", VARCHAR(50))
                .constraint(primaryKey("informacion_contacto"))
                .execute();
    }

    private static void crearTablaEntrada(DSLContext create) {
        // Crear tabla Entrada
        create.createTableIfNotExists("Entrada")
                .column("tipo_entrada", VARCHAR(50))
                .column("precio", INTEGER)
                .column("disponibilidad", VARCHAR(50))
                .constraint(primaryKey("tipo_entrada"))
                .execute();
    }

    private static void relacionarTabla(DSLContext create, String nombreTabla, String claveForanea, String
            nombreTablaRelacion){
        create.alterTableIfExists(nombreTabla).alterConstraint(foreignKey(claveForanea).references(nombreTablaRelacion)).enforced();
    }
    private static void agregarColumnaTabla(DSLContext create, String nombreTabla, String columna, DataType
            tipoColumna){
        create.alterTableIfExists(nombreTabla).addColumn(columna,tipoColumna);
    }
}