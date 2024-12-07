package DAO;

import BBDD.Conexion;
import DTO.Libro;
import DTO.Prestamo;

import java.util.ArrayList;
import java.util.List;

public class DAOLibro {
    Conexion con = new Conexion();
    List<Libro> listaLibros;

    public DAOLibro() {
        listaLibros = new ArrayList<>();
        rellenarListaLibros();
    }

    public void agregarLibro(Libro libro) {
        con.getTransaccion().begin();
        con.getGestor().merge(libro);
        con.getTransaccion().commit();
        System.out.println("Libro agregado");
        listaLibros.add(libro);
    }

    public void eliminarLibro(String isbn) {
        Libro l = con.getGestor().find(Libro.class, isbn);
        con.getTransaccion().begin();
        con.getGestor().remove(l);
        con.getTransaccion().commit();
        System.out.println("Libro eliminado");
        listaLibros.remove(isbn);
    }

    public void verLibros(){
        actualizarLista();
        for(Libro libro : listaLibros){
            System.out.println(libro);
        }
    }

    public void rellenarListaLibros(){
        List<Libro> listaL = con.getGestor().createQuery("Select d from Libro d", Libro.class).getResultList();
        for(Libro libro : listaL){
            listaLibros.add(libro);
        }
    }

    public void actualizarLista(){
        listaLibros.clear();
        rellenarListaLibros();
    }

    public void updateLibro(String isbn, Integer opcion, String nuevoValor) {
        Libro l = con.getGestor().find(Libro.class, isbn);
        switch (opcion) {
            case 1:
                //CAMBIAR TITULO
                con.getTransaccion().begin();
                l.setTitulo(nuevoValor);
                con.getTransaccion().commit();
                break;
            case 2:
                //CAMBIAR AUTOR
                con.getTransaccion().begin();
                l.setAutor(nuevoValor);
                con.getTransaccion().commit();
                break;
        }
        System.out.println("Libro actualizado");
    }
}
