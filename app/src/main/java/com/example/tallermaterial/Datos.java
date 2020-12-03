package com.example.tallermaterial;

import java.util.ArrayList;

public class Datos {
    public static ArrayList<Libro> libros = new ArrayList();

    public static void guardar(Libro l){
        libros.add(l);
    }

    public static ArrayList<Libro> obtener(){
        return libros;
    }
}
