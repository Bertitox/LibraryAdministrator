package DAO;

import BBDD.Conexion;
import DTO.Ejemplar;


import java.util.ArrayList;
import java.util.List;

public class DAOEjemplar {
    List<Ejemplar> listaEjemplares;
    Conexion c = new Conexion();

    public DAOEjemplar() {
        listaEjemplares = new ArrayList<>();
        rellenarLista();
    }

    public void agregarEjemplar(Ejemplar e) {
        c.getTransaccion().begin();
        c.getGestor().merge(e);
        c.getTransaccion().commit();
        System.out.println("Ejemplar agregado");
    }

    public void eliminarEjemplar(String id) {
        Ejemplar e = c.getGestor().find(Ejemplar.class, id);
        c.getTransaccion().begin();
        c.getGestor().remove(e);
        c.getTransaccion().commit();
        System.out.println("Ejemplar eliminado");
    }

    public void verEjemplares() {
        actualizarLista();
        for (Ejemplar e : listaEjemplares) {
            System.out.println(e);
        }
    }

    public void actualizarLista() {
        listaEjemplares.clear();
        rellenarLista();
    }

    public void rellenarLista() {
        c.getGestor().clear();
        List<Ejemplar> lista = c.getGestor().createQuery("select d from Ejemplar d").getResultList();
        for (Ejemplar e : lista) {
            listaEjemplares.add(e);
        }
    }


    public Integer stockDisponible() {
        Integer e = ((Number) c.getGestor().createQuery("select count(d) from Ejemplar d where estado = 'Disponible' ").getSingleResult()).intValue();
        return e;

        /*List<Ejemplar> lista = c.getGestor().createQuery("select d from Ejemplar d where estado = 'Disponible' ").getResultList();
        Integer contador = 0;

        for (Ejemplar ej: lista){
            if(ej.getEstado().equals("Disponible")){
                contador++;
            }
        }

        return contador;*/
    }

    public void updateEjemplar(Integer id, Boolean opcion, String nuevoValor) {
        Ejemplar e = c.getGestor().find(Ejemplar.class, id);

        if (opcion) {
            //CAMBIAR EL ISBN
            c.getTransaccion().begin();
            e.setIsbn(nuevoValor);
            c.getTransaccion().commit();
        } else {
            //CAMBIAR EL ESTADO
            c.getTransaccion().begin();
            e.setEstado(nuevoValor);
            c.getTransaccion().commit();
        }
    }


}
