package org.practicaud1y2;

import java.sql.Connection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase {@code CreateXML} se encarga de generar un archivo XML
 * a partir de los datos almacenados en la base de datos PostgreSQL.
 * <p>
 * Esta clase consulta todos los registros de la tabla juegos
 * y construye un archivo XML con una estructura jerárquica que representa
 * cada juego con sus respectivos campos.
 * </p>
 *
 * <p>
 * El archivo se crea en el mismo directorio del proyecto con el nombre indicado
 * por el usuario (añadiendo la extensión .xml automáticamente).
 * </p>
 *
 * @author
 * @version 1.0
 */
public class CreateXML {

    /**
     * Conexión a la base de datos que se usará para obtener los datos.
     */
    private static Connection connection;

    /**
     * Constructor de la clase {@code CreateXML}.
     * <p>
     * Cuando se crea un nuevo objeto de esta clase, se establece la conexión con la base de datos
     * llamando al método {@code getConnection()} de la clase {@code SqlConnect}.
     * </p>
     */
    public CreateXML() {
        connection = SqlConnect.getConnection();
    }

    /**
     * Crea un archivo XML con los datos obtenidos de la base de datos.
     * <p>
     * Este método realiza una consulta SQL para obtener todos los registros
     * de la tabla juegos, y luego genera un archivo XML
     * que contiene la información de cada juego.
     * </p>
     *
     * <p>
     * El nombre del archivo se recibe como parámetro y el archivo se guarda
     * en la carpeta raíz del proyecto, con extensión ".xml".
     * </p>
     *
     * <p>
     * En caso de que ocurra un error durante la conexión a la base de datos
     * o al escribir el archivo XML, se capturan las excepciones correspondientes.
     * </p>
     *
     * @param fileName Nombre del archivo (sin la extensión .xml) donde se guardarán los datos.
     */
    public void createXMLFromDatabase(String fileName) {

        // Se define la ruta del archivo a crear
        Path filePath;
        filePath = Paths.get(fileName + ".xml");

        // Consulta SQL para obtener todos los juegos de la tabla
        String sql = "SELECT * FROM juegos";

        // Se ejecuta la consulta y se genera el XML
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Se crea un StringBuilder para construir el contenido del archivo XML
            StringBuilder xmlContent = new StringBuilder();
            xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xmlContent.append("<juegos>\n");

            // Se recorren los resultados de la consulta y se añaden al XML
            while (rs.next()) {
                xmlContent.append("  <juego>\n");
                xmlContent.append("    <id>").append(rs.getInt("id")).append("</id>\n");
                xmlContent.append("    <titulo>").append(rs.getString("titulo")).append("</titulo>\n");
                xmlContent.append("    <plataforma>").append(rs.getString("plataforma")).append("</plataforma>\n");
                xmlContent.append("    <genero>").append(rs.getString("genero")).append("</genero>\n");
                xmlContent.append("    <año>").append(rs.getInt("año")).append("</año>\n");
                xmlContent.append("    <desarrollador>").append(rs.getString("desarrollador")).append("</desarrollador>\n");
                xmlContent.append("    <editor>").append(rs.getString("editor")).append("</editor>\n");
                xmlContent.append("    <esEdicionEspecial>").append(rs.getBoolean("esEdicionEspecial")).append("</esEdicionEspecial>\n");
                xmlContent.append("    <estado>").append(rs.getString("estado")).append("</estado>\n");
                xmlContent.append("    <valorEstimado>").append(rs.getDouble("valorEstimado")).append("</valorEstimado>\n");
                xmlContent.append("    <notas>").append(rs.getString("notas")).append("</notas>\n");
                xmlContent.append("  </juego>\n");
            }

            xmlContent.append("</juegos>");

            // Se escribe el contenido en el archivo .xml
            Files.write(filePath, xmlContent.toString().getBytes());
            System.out.println("Archivo XML creado correctamente: " + filePath.toAbsolutePath());

        } catch (SQLException e) {
            // Error al consultar la base de datos
            System.err.println("Error al conectar con la base de datos.");
            e.printStackTrace();
            throw new RuntimeException(e);

        } catch (IOException e) {
            // Error al crear o escribir el archivo XML
            System.err.println("Error al escribir el archivo XML: " + e.getMessage());
        }
    }
}