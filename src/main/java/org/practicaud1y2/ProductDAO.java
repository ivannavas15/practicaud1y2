package org.practicaud1y2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase {@code ProductDAO} se encarga de realizar las operaciones de acceso a datos
 * relacionadas con los productos (en este caso, los juegos) almacenados en la base de datos.
 *
 * <p>
 * En esta clase se utiliza una conexión JDBC a una base de datos PostgreSQL para consultar
 * todos los registros de la tabla <strong>juegos</strong>.
 * </p>
 *
 * @author
 * @version 1.0
 */
public class ProductDAO {

    /**
     * Objeto de tipo {@link Connection} que representa la conexión con la base de datos.
     */
    private Connection connection;

    /**
     * Constructor de la clase {@code ProductDAO}.
     * <p>
     * Cuando se crea un nuevo objeto de esta clase, se obtiene una conexión
     * a la base de datos a través de la clase {@code SqlConnect}.
     * </p>
     */
    public ProductDAO() {
        this.connection = SqlConnect.getConnection();
    }

    /**
     * Muestra por consola todos los productos almacenados en la tabla <strong>juegos</strong>.
     * <p>
     * Este método realiza una consulta SQL para seleccionar todos los registros de la tabla.
     * Por cada registro encontrado, imprime en consola los valores de cada campo.
     * </p>
     *
     * <p>
     * En caso de que ocurra un error durante la ejecución de la consulta, se captura la excepción
     * {@link SQLException} y se muestra un mensaje de error por consola, además de imprimir
     * la traza del error para depuración.
     * </p>
     *
     * @throws SQLException Si ocurre un error al ejecutar la consulta o al comunicarse con la base de datos.
     */
    public void readAllProducts() throws SQLException {

        try {
            String sql3 = "SELECT * FROM juegos;";

            PreparedStatement statement = connection.prepareStatement(sql3);
            ResultSet resultSet = statement.executeQuery();

            // Se recorre el resultado de la consulta para imprimir cada juego
            while (resultSet.next()) {
                System.out.println(
                        "ID: " + resultSet.getInt("id") +
                                " | Título: " + resultSet.getString("titulo") +
                                " | Plataforma: " + resultSet.getString("plataforma") +
                                " | Género: " + resultSet.getString("genero") +
                                " | Año: " + resultSet.getInt("año") +
                                " | Desarrollador: " + resultSet.getString("desarrollador") +
                                " | Editor: " + resultSet.getString("editor") +
                                " | Edición especial: " + resultSet.getBoolean("esEdicionEspecial") +
                                " | Estado: " + resultSet.getString("estado") +
                                " | Valor estimado: " + resultSet.getInt("valorEstimado") +
                                " | Nota: " + resultSet.getString("notas")
                );
            }
        } catch (SQLException e) {
            // Si ocurre un error, se informa al usuario y se imprime la traza del error
            System.err.println("Error al conectar con la base de datos.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}