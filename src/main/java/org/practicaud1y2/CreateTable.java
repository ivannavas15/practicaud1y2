package org.practicaud1y2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * La clase {@code CreateTable} se encarga de crear la tabla juegos
 * en la base de datos PostgreSQL y de insertar algunos registros iniciales.
 * <p>
 * Su objetivo principal es asegurar que la tabla exista y tenga datos disponibles
 * para que las demás clases del proyecto puedan trabajar con ellos.
 * </p>
 *
 * <p>
 * Esta clase se apoya en {@link SqlConnect} para obtener la conexión a la base de datos.
 * </p>
 *
 * @author
 * @version 1.0
 */
public class CreateTable {

    /**
     * Objeto {@link Connection} utilizado para realizar operaciones sobre la base de datos.
     */
    private static Connection connection;

    /**
     * Constructor de la clase {@code CreateTable}.
     * <p>
     * Al instanciar esta clase, se establece una conexión con la base de datos
     * usando el método {@link SqlConnect#getConnection()}.
     * </p>
     */
    public CreateTable() {
        connection = SqlConnect.getConnection();
    }

    /**
     * Crea la tabla juegos en la base de datos (si no existe)
     * e inserta una serie de registros iniciales con información de videojuegos populares.
     *
     * <p>
     * Si la tabla ya existe, simplemente se omite su creación.
     * Luego se insertan varios registros de ejemplo usando sentencias SQL {@code INSERT INTO}.
     * </p>
     *
     * <p>
     * En caso de que ocurra algún error durante la ejecución de las sentencias SQL,
     * se captura una {@link SQLException}, se muestra un mensaje de error por consola
     * y se lanza una {@link RuntimeException}.
     * </p>
     */
    public void createTable() {
        // Sentencia SQL para crear la tabla si no existe
        String sql1 = "CREATE TABLE IF NOT EXISTS juegos (" +
                "id SERIAL PRIMARY KEY, " +
                "titulo VARCHAR(200), " +
                "plataforma VARCHAR(100), " +
                "genero VARCHAR(100), " +
                "año INT, " +
                "desarrollador VARCHAR(150), " +
                "editor VARCHAR(150), " +
                "esEdicionEspecial BOOLEAN, " +
                "estado VARCHAR(50), " +
                "valorEstimado DOUBLE PRECISION, " +
                "notas TEXT);";

        // Sentencia SQL para insertar varios registros iniciales en la tabla
        String sql2 = "INSERT INTO juegos (titulo, plataforma, genero, año, desarrollador, editor, esEdicionEspecial, estado, valorEstimado, notas) VALUES " +
                "('The Legend of Zelda: Tears of the Kingdom', 'Nintendo Switch', 'Aventura', 2023, 'Nintendo EPD', 'Nintendo', false, 'Nuevo', 69.99, 'Incluye póster y caja metálica'), " +
                "('Starfield', 'PC', 'RPG', 2023, 'Bethesda Game Studios', 'Bethesda Softworks', true, 'Nuevo', 79.90, 'Edición especial con contenido adicional digital'), " +
                "('Hogwarts Legacy', 'PlayStation 5', 'Acción/Aventura', 2023, 'Avalanche Software', 'Warner Bros. Games', false, 'Usado', 49.50, 'Buen estado, incluye mapa del castillo'), " +
                "('Resident Evil 4 Remake', 'PlayStation 5', 'Terror', 2023, 'Capcom', 'Capcom', false, 'Nuevo', 59.99, 'Versión europea, idioma español'), " +
                "('Baldur''s Gate 3', 'PC', 'RPG', 2023, 'Larian Studios', 'Larian Studios', true, 'Nuevo', 69.99, 'Edición Deluxe con banda sonora digital');";

        try {
            // Se crea la tabla (si no existe)
            PreparedStatement statement = connection.prepareStatement(sql1);
            statement.executeUpdate();

            // Se insertan los registros en la tabla
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.executeUpdate();

            System.out.println("Tabla creada e insertados los datos correctamente.");

        } catch (SQLException e) {
            // Si hay un error, se muestra por consola y se lanza una excepción
            System.err.println("Error al conectar con la base de datos.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}