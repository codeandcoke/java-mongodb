package org.sfaci.holamongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.sfaci.holamongodb.base.Libro;
import org.sfaci.holamongodb.util.Constantes;
import org.sfaci.holamongodb.util.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Modelo para la ventana
 *
 * @author Santiago Faci
 * @version curso 2015-2016
 */
public class VentanaModel {

    private MongoClient mongoClient;
    private MongoDatabase db;

    /**
     * Conecta con la Base de Datos
     */
    public void conectar() {
        mongoClient = new MongoClient();
        db = mongoClient.getDatabase(Constantes.NOMBRE_BASEDEDATOS);
    }

    /**
     * Libera todos los recursos (conexiones en red y conexiones con Bases de Datos)
     */
    public void desconectar() {
        mongoClient.close();
    }

    /**
     * Añade un libro en la base de datos (como un documento)
     * @param libro
     */
    public void anadirLibro(Libro libro) {

        Document documento = new Document()
                .append("titulo", libro.getTitulo())
                .append("descripcion", libro.getDescripcion())
                .append("autor", libro.getAutor())
                .append("fecha", Util.formatFecha(libro.getFecha()));
        db.getCollection(Libro.COLECCION).insertOne(documento);

        // También es posible pasar directamente la información en formato JSON
        // db.getCollection(Libro.COLECCION).insertOne(Document.parse(libro.toJSON()));
    }

    /**
     * Modifica un libro en la Base de Datos
     * @param libro
     */
    public void modificarLibro(Libro libro) {

        db.getCollection(Libro.COLECCION).replaceOne(new Document("_id", libro.getId()),
                new Document()
                        .append("titulo", libro.getTitulo())
                        .append("descripcion", libro.getDescripcion())
                        .append("autor", libro.getAutor())
                        .append("fecha", Util.formatFecha(libro.getFecha())));
    }

    /**
     * Elimina un libro de la Base de Datos (por título)
     * @param titulo
     */
    public void eliminarLibro(String titulo) {
        db.getCollection(Libro.COLECCION).deleteOne(new Document("titulo", titulo));
    }

    /**
     * Busca un libro buscando en varios de sus atributos
     * También ordena los resultados
     * @param busqueda
     */
    public List<Libro> buscarLibro(String busqueda) throws ParseException {

        // Búsqueda utilizando el operador OR
        /*Document documento = new Document("$or", Arrays.asList(
                new Document("titulo", busqueda),
                new Document("descripcion", busqueda),
                new Document("autor", busqueda)));*/

        // Búsqueda utilizando expresiones regulares
        BasicDBObject documento = new BasicDBObject();
        documento.put("titulo", new BasicDBObject("$regex", "/*" + busqueda + "/*"));

        FindIterable findIterable = db.getCollection(Libro.COLECCION)
                .find(documento)
                .sort(new Document("titulo", 1));
        return getListaLibros(findIterable);

        // También es posible realizar la búsqueda de esta manera
        /*FindIterable findIterable = db.getCollection(Libro.COLECCION).find(Filters.or(
                Filters.eq("titulo", busqueda),
                Filters.eq("descripcion", busqueda),
                Filters.eq("autor", busqueda)))
                .sort(new Document("titulo", 1));
        return getListaLibros(findIterable);*/
    }

    /**
     * Prepara una lista de Libro a partir del resultado de una búsqueda
     * @param findIterable
     * @return
     * @throws ParseException
     */
    private List<Libro> getListaLibros(FindIterable<Document> findIterable) throws ParseException {

        List<Libro> libros = new ArrayList<>();
        Libro libro = null;
        Iterator<Document> iter = findIterable.iterator();

        while (iter.hasNext()) {
            Document documento = iter.next();
            libro = new Libro();
            libro.setId(documento.getObjectId("_id"));
            libro.setTitulo(documento.getString("titulo"));
            libro.setDescripcion(documento.getString("descripcion"));
            libro.setAutor(documento.getString("autor"));
            libro.setFecha(Util.parseFecha(documento.getString("fecha")));
            libros.add(libro);
        }

        return libros;
    }

    /**
     * Obtiene un objeto Libro de un Documento extraído de la Base de Datos
     * @param documento
     * @return
     * @throws ParseException
     */
    private Libro getLibro(Document documento) throws ParseException {
        Libro libro = new Libro();
        libro.setId(documento.getObjectId("_id"));
        libro.setTitulo(documento.getString("titulo"));
        libro.setDescripcion(documento.getString("descripcion"));
        libro.setAutor(documento.getString("autor"));
        libro.setFecha(Util.parseFecha(documento.getString("fecha")));

        return libro;
    }

    /**
     * Obtiene todos los libros de la Base de Datos
     * @return
     * @throws ParseException
     */
    public List<Libro> getLibros() throws ParseException {

        FindIterable<Document> findIterable = db.getCollection(Libro.COLECCION).find();
        return getListaLibros(findIterable);
    }

    /**
     * Obtiene el libro que tiene el título especificado
     * @param titulo
     * @return
     * @throws ParseException
     */
    public Libro getLibro(String titulo) throws ParseException {

        FindIterable<Document> findIterable = db.getCollection(Libro.COLECCION).find(new Document("titulo", titulo));
        Document documento = findIterable.first();
        return getLibro(documento);
    }

    /**
     * Obtiene todos los libros de un autor
     * @param autor
     * @return
     * @throws ParseException
     */
    public List<Libro> getLibros(String autor) throws ParseException {

        FindIterable<Document> findIterable = db.getCollection(Libro.COLECCION).find(new Document("autor", autor));
        return getListaLibros(findIterable);
    }
}
