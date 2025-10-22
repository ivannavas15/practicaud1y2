package org.practicaud1y2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * La clase {@code ResetSql} se encarga de restablecer el estado de la tabla juegos
 * en la base de datos PostgreSQL.
 * <p>
 * Este proceso consiste en eliminar todos los registros almacenados en la tabla
 * y reiniciar el contador de la secuencia del campo id, de modo que
 * los nuevos registros comiencen nuevamente desde el valor 1.
 * </p>
 *
 * <p>
 * Esta clase es útil para reiniciar los datos después de realizar pruebas o
 * para dejar la base de datos en un estado limpio antes de volver a insertar información.
 * </p>
 *
 * @author
 * @version 1.0
 */
public class ResetSql {

    /**
     * Objeto {@link Connection} utilizado para comunicarse con la base de datos.
     */
    private static Connection connection;

    /**
     * Constructor de la clase {@code ResetSql}.
     * <p>
     * Al crear una nueva instancia de esta clase, se obtiene la conexión con la base de datos
     * mediante el método {@code getConnection()} de la clase {@code SqlConnect}.
     * </p>
     */
    public ResetSql() {
        connection = SqlConnect.getConnection();
    }

    /**
     * Restablece la tabla juegos en la base de datos PostgreSQL.
     * <p>
     * Este método ejecuta la sentencias SQL5 y SQL6
     * </p>
     *
     * <p>
     * En caso de que ocurra un error durante la ejecución de las sentencias SQL,
     * se captura una {@link SQLException}, se muestra un mensaje de error y se lanza
     * una excepción de tipo {@link RuntimeException}.
     * </p>
     */
    public void resetSql() {

        // Sentencias SQL: eliminar todos los registros y reiniciar la secuencia del ID
        String sql5 = "DELETE FROM juegos;";
        String sql6 = "ALTER SEQUENCE juegos_id_seq RESTART WITH 1;";

        try {
            // Se ejecuta la primera sentencia: eliminar todos los registros
            PreparedStatement statement = connection.prepareStatement(sql5);
            statement.executeUpdate();

            // Se ejecuta la segunda sentencia: reiniciar la secuencia del ID
            PreparedStatement statement2 = connection.prepareStatement(sql6);
            statement2.executeUpdate();

        } catch (SQLException e) {
            // Si ocurre un error al ejecutar las sentencias SQL, se muestra un mensaje y la traza
            System.err.println("Error al conectar con la base de datos.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}