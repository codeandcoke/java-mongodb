package org.sfaci.holamongodb.base;

import org.bson.types.ObjectId;
import org.sfaci.holamongodb.util.Util;

import java.util.Date;

/**
 * Un libro
 *
 * @author Santiago Faci
 * @version curso 2015-2016
 */
public class Libro {

    public static String COLECCION = "libros";

    private ObjectId id;
    private String titulo;
    private String descripcion;
    private String autor;
    private Date fecha;

    public Libro() {

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return titulo;
    }

    /**
     * Convierte el objeto a format JSON
     * @return
     */
    public String toJSON() {
        return "{'titulo':'" + titulo + "', 'descripcion':'" + descripcion +
                "', 'autor':'" + autor + "', 'fecha':'" + Util.formatFecha(fecha)+ "'}";
    }
}
