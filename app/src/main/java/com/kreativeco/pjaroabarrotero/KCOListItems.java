package com.kreativeco.pjaroabarrotero;

/**
 * Created by kreativeco on 04/03/15.
 */
public class KCOListItems
{
    protected long id;
    protected String rutaImagen;
    protected String rutaImagenAux;
    protected String nombre;

    public KCOListItems() {
        //this.nombre = "";
        this.rutaImagen = "";
    }

    public KCOListItems(long id, String rutaImagen) {
        this.id = id;
        //this.nombre = nombre;
        this.rutaImagen = rutaImagen;
    }

    public KCOListItems(long id, String nombre, String rutaImagen) {
        this.id = id;
        this.nombre = nombre;
        this.rutaImagen = rutaImagen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
