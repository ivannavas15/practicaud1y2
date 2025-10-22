package org.practicaud1y2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase {@code SqlConnect} se encarga de establecer la conexión con la base de datos PostgreSQL.
 * <p>
 * Esta clase centraliza la lógica de conexión para que otras clases del proyecto,
 * como {@code ProductDAO}, {@code CreateXML} o {@code ResetSql},
 * puedan acceder fácilmente a la base de datos sin repetir código.
 * </p>
 *
 * <p>
 * Utiliza el controlador JDBC de PostgreSQL para crear la conexión mediante la clase {@link DriverManager}.
 * </p>
 *
 *
 * @author
 * @version 1.0
 */
public class SqlConnect {

    /**
     * URL de conexión a la base de datos PostgreSQL.
     * <p>
     * Incluye el nombre del host (localhost), el puerto (5433) y el nombre de la base de datos (practicaud1y2).
     * </p>
     */
    private static final String URL = "jdbc:postgresql://localhost:5433/practicaud1y2";

    /**
     * Nombre de usuario utilizado para conectarse a la base de datos.
     */
    private static final String USER = "postgres";

    /**
     * Contraseña del usuario que se usa para la conexión.
     */
    private static final String PASS = "admin";

    /**
     * Establece y devuelve una conexión con la base de datos PostgreSQL.
     * <p>
     * Este método utiliza el {@link DriverManager} de JDBC para intentar conectarse
     * a la base de datos usando la URL, el usuario y la contraseña definidos anteriormente.
     * </p>
     *
     * <p>
     * Si la conexión es exitosa, devuelve un objeto {@link Connection}.
     * En caso de fallo, muestra un mensaje de error por consola y lanza una {@link RuntimeException}.
     * </p>
     *
     * @return un objeto {@link Connection} que representa la conexión activa con la base de datos.
     * @throws RuntimeException si ocurre un error al intentar establecer la conexión.
     */
    public static Connection getConnection() {
        try {
            // Intenta establecer la conexión con la base de datos PostgreSQL
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            return conn;

        } catch (SQLException e) {
            // Si ocurre un error, se muestra un mensaje en consola y se imprime la traza
            System.err.println("Error al conectar con la base de datos.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}