package com.example.tallermaterial;

public class Libro {
    private String ISBN;
    private String nombre;
    private String autor;
    private String id;

    public Libro(String ISBN, String nombre, String autor, String id){
        this.ISBN = ISBN;
        this.nombre = nombre;
        this.autor = autor;
        this.id = id;
    }
    public Libro(){

    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void guardar(){
        Datos.guardar(this);
    }

    public void eliminar(){ Datos.eliminar(this);}

}
