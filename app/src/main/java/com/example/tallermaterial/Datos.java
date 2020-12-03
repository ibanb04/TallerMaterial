package com.example.tallermaterial;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Datos {
    private static String db = "Libros";
    private static DatabaseReference databaseReference =
            FirebaseDatabase.getInstance().getReference();

    private static ArrayList<Libro> libros = new ArrayList();

    public static String getId(){
        return databaseReference.push().getKey();
    }

    public static void guardar(Libro l){
        databaseReference.child(db).child(l.getId()).setValue(l);
    }

    public static void setLibros(ArrayList<Libro> libros){
        libros = libros;
    }


}
