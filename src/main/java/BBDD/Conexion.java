package BBDD;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Conexion {
    EntityManagerFactory factoria;
    EntityManager gestor;
    EntityTransaction transaccion;

    public Conexion() {
        this.factoria = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        this.gestor = factoria.createEntityManager();
        this.transaccion = gestor.getTransaction();
    }

    public EntityManagerFactory getFactoria() {
        return factoria;
    }

    public void setFactoria(EntityManagerFactory factoria) {
        this.factoria = factoria;
    }

    public EntityManager getGestor() {
        return gestor;
    }

    public void setGestor(EntityManager gestor) {
        this.gestor = gestor;
    }

    public EntityTransaction getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(EntityTransaction transaccion) {
        this.transaccion = transaccion;
    }
}
