package org.practicaud1y2;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * La clase principal del programa gestiona la ejecución completa del flujo de trabajo.
 * <p>
 * Esta clase es el punto de entrada de la aplicación y se encarga de coordinar
 * la creación de la tabla en la base de datos, la lectura de un archivo XML,
 * la visualización de los datos almacenados en la base de datos,
 * la generación de un nuevo archivo XML con los datos actuales
 * y, finalmente, el reseteo de la base de datos.
 * </p>
 *
 * <p>
 * El programa está diseñado para trabajar con una base de datos PostgreSQL
 * y utiliza varias clases auxiliares para realizar tareas específicas:
 * <ul>
 *     <li>{@code CreateTable} → crea la tabla en la base de datos.</li>
 *     <li>{@code ReadXML} → lee los datos de un archivo XML y los inserta en la base de datos.</li>
 *     <li>{@code ProductDAO} → permite consultar los productos (juegos) almacenados en la base de datos.</li>
 *     <li>{@code CreateXML} → genera un nuevo archivo XML a partir de los datos almacenados en la base de datos.</li>
 *     <li>{@code ResetSql} → borra o reinicia la base de datos para dejarla en su estado inicial.</li>
 * </ul>
 * </p>
 *
 * @author
 * @version 1.0
 */
public class Main {

    /**
     * Nombre del archivo XML que el usuario genera.
     * Este valor se pide por consola y se utiliza para crear el nuevo archivo XML.
     */
    private static String nameFile;

    /**
     * Método principal del programa.
     * <p>
     * Este método controla la ejecución del flujo principal de la aplicación:
     * <ol>
     *     <li>Conecta con la base de datos PostgreSQL.</li>
     *     <li>Crea la tabla de juegos (si no existe).</li>
     *     <li>Lee los datos desde un archivo XML y los inserta en la base de datos.</li>
     *     <li>Muestra por consola todos los registros almacenados.</li>
     *     <li>Pide al usuario el nombre del archivo XML de salida.</li>
     *     <li>Genera un nuevo archivo XML con los datos actuales de la base de datos.</li>
     *     <li>Restablece la base de datos a su estado original.</li>
     * </ol>
     * </p>
     *
     * <p>
     * Si ocurre un error durante la conexión o manipulación de la base de datos,
     * se captura una {@link SQLException} y se muestra un mensaje de error.
     * </p>
     *
     * @param args Argumentos que se pueden pasar por consola (no se usan en este programa).
     */
    public static void main(String[] args) {

        // Se crean los objetos necesarios para ejecutar las diferentes tareas del programa
        CreateTable table = new CreateTable();
        ReadXML xml = new ReadXML();
        ProductDAO dao = new ProductDAO();
        CreateXML createXml = new CreateXML();
        ResetSql reset = new ResetSql();

        try {
            System.out.println("Te has conectado correctamente a la base de datos PostgreSQL");

            // Se crea la tabla en la base de datos
            table.createTable();

            // Se leen los datos desde un archivo XML y se insertan en la base de datos
            xml.readXML();

            // Se muestran por consola todos los juegos almacenados en la base de datos
            dao.readAllProducts();

            // Se solicita al usuario el nombre del nuevo archivo XML
            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce el nombre del archivo XML (sin extensión): ");
            nameFile = sc.nextLine();
            sc.close();

            // Se genera un nuevo archivo XML con los datos actuales de la base de datos
            createXml.createXMLFromDatabase(nameFile);

            // Se resetea la base de datos (por ejemplo, eliminando los registros o la tabla)
            reset.resetSql();

        } catch (SQLException e) {
            // Si ocurre un error con la base de datos, se muestra un mensaje y la traza del error
            System.err.println("Error al conectar con la base de datos.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}