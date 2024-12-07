package DAO;

import BBDD.Conexion;
import DTO.Ejemplar;
import DTO.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DAOUsuario {
    List<Usuario> listaUsuarios;
    Conexion c = new Conexion();

    public DAOUsuario() {
        this.listaUsuarios = new ArrayList<>();
        rellenarLista();
    }

    public void agregarUsuario(Usuario usuario) {
        c.getTransaccion().begin();
        c.getGestor().merge(usuario);
        c.getTransaccion().commit();
        System.out.println("Usuario agregado");
    }

    public void eliminarUsuario(Integer id) {
        c.getTransaccion().begin();
        c.getGestor().remove(c.getGestor().find(Usuario.class, id));
        c.getTransaccion().commit();
        System.out.println("Usuario eliminado");
    }

    public void verUsuarios() {
        actualizarLista();
        for (Usuario usuario : listaUsuarios) {
            System.out.println(usuario);
        }
    }

    public void rellenarLista() {
        List<Usuario> usuarios = c.getGestor().createQuery("Select d from Usuario d").getResultList();
        for (Usuario usuario : usuarios) {
            listaUsuarios.add(usuario);
        }
    }

    public void actualizarLista() {
        listaUsuarios.clear();
        rellenarLista();
    }

    public void updateUsuario(Integer id, Integer opcion, String nuevoValor) {
        Usuario u = c.getGestor().find(Usuario.class, id);
        switch (opcion) {
            case 1:
                //CAMBIAR EL DNI
                c.getTransaccion().begin();
                u.setDni(nuevoValor);
                c.getTransaccion().commit();
                break;
            case 2:
                //CAMBIAR EL NOMBRE
                c.getTransaccion().begin();
                u.setNombre(nuevoValor);
                c.getTransaccion().commit();
                break;
            case 3:
                //CAMBIAR EL EMAIL
                c.getTransaccion().begin();
                u.setEmail(nuevoValor);
                c.getTransaccion().commit();
                break;
            case 4:
                //CAMBIAR LA CONTRASEÑA
                c.getTransaccion().begin();
                u.setPassword(nuevoValor);
                c.getTransaccion().commit();
                break;
            case 5:
                //CAMBIAR EL TIPO
                c.getTransaccion().begin();
                u.setTipo(nuevoValor);
                c.getTransaccion().commit();
                break;
        }
        System.out.println("Usuario actualizado");
    }
}
