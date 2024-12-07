package DAO;

import BBDD.Conexion;
import DTO.Ejemplar;
import DTO.Prestamo;
import DTO.Usuario;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOPrestamo {

    List<Prestamo> listaPrestamos;
    Conexion c = new Conexion();
    Integer numMaxPrestamos = 3;
    Integer numPrestamos = 0;
    Integer diasPenalizacion = 0;

    public DAOPrestamo() {
        this.listaPrestamos = new ArrayList<>();
        rellenarLista();
    }

    private void rellenarLista() {
        List<Prestamo> prestamos = c.getGestor().createQuery("select d from Prestamo d").getResultList();
        for (Prestamo prestamo : prestamos) {
            listaPrestamos.add(prestamo);
        }
    }

    public void nuevoPrestamo(Prestamo prestamo) {
        //VALIDAR SI TIENE EL Nº MAX DE PRESTAMOS
        if (validarNPrestamos(prestamo)) {
            //VALIDAR SI TIENE PENALIZACION ACTIVA
            if (validarPenalizacion(prestamo)) {
                //NO SE HACE UN PRESTAMO SI EL ESTADO DEL EJEMPLAR ES DAÑADO O PRESTADO
                if (!(prestamo.getEjemplar().getEstado().equals("Dañado") || prestamo.getEjemplar().getEstado().equals("Prestado"))) {
                    c.getTransaccion().begin();
                    Ejemplar ej = prestamo.getEjemplar();
                    ej.setPrestado();
                    c.getGestor().merge(ej);
                    c.getGestor().merge(prestamo);
                    c.getTransaccion().commit();
                    System.out.println("Prestamo agregado");
                    if (!validarFecha15d(prestamo)) {
                        this.diasPenalizacion += 15;
                        prestamo.getUsuario().setPenalizacionHasta(prestamo.getFechaInicio().plusDays(diasPenalizacion));
                    }
                } else {
                    System.out.println("El libro que quiere coger está dañado o prestado");
                }
            } else {
                System.out.println("El usuario tiene una penalizacion activa");
            }
        } else {
            System.out.println("Número máximo de prestamos alcanzados");
        }
    }

    private boolean validarNPrestamos(Prestamo p) {
        List<Prestamo> listap = c.getGestor().createQuery("select p from Prestamo p").getResultList();
        Integer numP = 0;
        for (Prestamo prestamo : listap) {
            if (prestamo.getUsuario() == p.getUsuario()) {
                numP++;
                System.out.println(numP);
            }
        }
        //MAXIMO 3 PRESTAMOS POR USUARIO
        if (numP < numMaxPrestamos) {
            return true;
        }

        return false;
    }


    public void eliminarPrestamo(Integer id) {
        c.getTransaccion().begin();
        c.getGestor().remove(c.getGestor().find(Prestamo.class, id));
        c.getTransaccion().commit();
        System.out.println("Prestamo eliminado");
    }

    public void verPrestamos() {
        actualizarLista();
        for (Prestamo prestamo : listaPrestamos) {
            System.out.println(prestamo);
        }
    }

    public Boolean validarPenalizacion(Prestamo prestamo) {
        //FALSE = SI TIENE PENALIZACION
        //TRUE = NO TIENE PENALIZACION
        if (prestamo.getUsuario().getPenalizacionHasta() != null) {
            return false;
        }
        return true;
    }

    public void actualizarLista() {
        listaPrestamos.clear();
        rellenarLista();
    }

    private boolean validarFecha15d(Prestamo prestamo) {
        if (!(prestamo.getFechaInicio() == null)) {
            Period p = Period.between(prestamo.getFechaDevolucion(), prestamo.getFechaInicio());
            //MENOR QUE 15 DIAS = TRUE
            //MAYOR QUE 15 DIAS = FALSE
            if (p.getDays() <= 15) {
                return true;
            }
        }
        return false;
    }

    public void updatePrestamo(Integer id, Integer opcion, String nuevoValor) {
        Prestamo p = c.getGestor().find(Prestamo.class, id);

        switch (opcion) {
            case 1:
                //CAMBIAR USUARIO ID
                c.getTransaccion().begin();
                p.setUsuario(Integer.valueOf(nuevoValor));
                c.getTransaccion().commit();
                break;
            case 2:
                //CAMBIAR EJEMPLAR ID
                c.getTransaccion().begin();
                p.setEjemplar(Integer.valueOf(nuevoValor));
                c.getTransaccion().commit();
                break;
        }
        System.out.println("Prestamo actualizado");
    }

}
