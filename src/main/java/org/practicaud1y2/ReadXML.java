package org.practicaud1y2;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * La clase {@code ReadXML} se encarga de leer un archivo XML con información de videojuegos
 * y de insertar esos datos en la base de datos PostgreSQL en la tabla juegos.
 * <p>
 * Esta clase utiliza el parser DOM (Document Object Model) para analizar la estructura del archivo XML,
 * extraer los datos de cada elemento juego y guardarlos en la base de datos mediante
 * sentencias SQL {@code INSERT INTO}.
 * </p>
 *
 * <p>
 * El archivo XML debe encontrarse en la ruta src/main/resources/juegos.xml
 * </p>
 *
 * @author
 * @version 1.0
 */
public class ReadXML {

    /** Título del videojuego leído desde el XML. */
    public String tituloJuego;

    /** Plataforma en la que se ejecuta el videojuego. */
    public String plataformaJuego;

    /** Género o categoría del videojuego. */
    public String generoJuego;

    /** Año de lanzamiento del videojuego. */
    public Integer añoJuego;

    /** Nombre del desarrollador del videojuego. */
    public String desarrolladorJuego;

    /** Nombre del editor o distribuidor del videojuego. */
    public String editorJuego;

    /** Indica si el videojuego es una edición especial (true/false). */
    public Boolean esEdicionEspecialJuego;

    /** Estado físico del videojuego (por ejemplo, nuevo o usado). */
    public String estadoJuego;

    /** Valor estimado o precio del videojuego. */
    public Double valorJuego;

    /** Notas adicionales o comentarios sobre el videojuego. */
    public String notasJuego;

    /** Lista de nodos XML que representan los juegos dentro del documento. */
    private NodeList juegosList;

    /** Conexión a la base de datos PostgreSQL. */
    private Connection connection;

    /**
     * Método principal de la clase encargado de leer el archivo XML y guardar los datos en la base de datos.
     * <p>
     * Este método realiza las siguientes operaciones:
     * </p>
     * <ol>
     *     <li>Establece conexión con la base de datos usando {@link SqlConnect}.</li>
     *     <li>Lee el archivo juegos.xml usando un parser DOM.</li>
     *     <li>Recorre todos los elementos juego dentro del XML.</li>
     *     <li>Extrae los valores de cada etiqueta (título, plataforma, género, etc.).</li>
     *     <li>Inserta los datos en la tabla juegos mediante una sentencia SQL preparada.</li>
     * </ol>
     *
     * <p>
     * En caso de que ocurra algún error durante la lectura o la inserción, se muestra un mensaje por consola
     * y se lanza una excepción de tipo {@link RuntimeException}.
     * </p>
     */
    public void readXML() {
        // Se obtiene la conexión con la base de datos
        this.connection = SqlConnect.getConnection();

        try {
            // Se prepara el parser DOM para leer el archivo XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Se carga el archivo XML ubicado en la carpeta resources
            Document document = builder.parse(new File("src/main/resources/juegos.xml"));

            // Se obtiene el nodo raíz del documento (<juegos>)
            Element root = document.getDocumentElement();

            // Se obtiene una lista de todos los elementos <juego>
            this.juegosList = root.getElementsByTagName("juego");

            // Se recorren todos los juegos del archivo XML
            for (int i = 0; i < juegosList.getLength(); i++) {
                Element juego = (Element) juegosList.item(i);

                // Se leen los valores de cada etiqueta y se asignan a las variables
                tituloJuego = juego.getElementsByTagName("titulo").item(0).getTextContent();
                plataformaJuego = juego.getElementsByTagName("plataforma").item(0).getTextContent();
                generoJuego = juego.getElementsByTagName("genero").item(0).getTextContent();
                añoJuego = Integer.parseInt(juego.getElementsByTagName("año").item(0).getTextContent());
                desarrolladorJuego = juego.getElementsByTagName("desarrollador").item(0).getTextContent();
                editorJuego = juego.getElementsByTagName("editor").item(0).getTextContent();
                esEdicionEspecialJuego = Boolean.parseBoolean(juego.getElementsByTagName("esEdicionEspecial").item(0).getTextContent());
                estadoJuego = juego.getElementsByTagName("estado").item(0).getTextContent();
                valorJuego = Double.parseDouble(juego.getElementsByTagName("valorEstimado").item(0).getTextContent());
                notasJuego = juego.getElementsByTagName("notas").item(0).getTextContent();

                // Sentencia SQL para insertar los datos del juego en la base de datos
                String sql4 = "INSERT INTO juegos (titulo, plataforma, genero, año, desarrollador, editor, esEdicionEspecial, estado, valorEstimado, notas) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // Se prepara y ejecuta la sentencia SQL con los valores extraídos del XML
                try (PreparedStatement statement = connection.prepareStatement(sql4)) {
                    statement.setString(1, tituloJuego);
                    statement.setString(2, plataformaJuego);
                    statement.setString(3, generoJuego);
                    statement.setInt(4, añoJuego);
                    statement.setString(5, desarrolladorJuego);
                    statement.setString(6, editorJuego);
                    statement.setBoolean(7, esEdicionEspecialJuego);
                    statement.setString(8, estadoJuego);
                    statement.setDouble(9, valorJuego);
                    statement.setString(10, notasJuego);

                    statement.executeUpdate();
                }
            }
        } catch (Exception e) {
            // Si ocurre un error durante la lectura o inserción, se informa por consola
            System.err.println("Error de lectura del documento.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
