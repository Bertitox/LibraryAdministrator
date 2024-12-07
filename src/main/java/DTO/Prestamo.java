package DTO;

import BBDD.Conexion;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "Prestamo", schema = "bibliotecaNueva")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ejemplar_id", nullable = false)
    private Ejemplar ejemplar;

    @Column(name = "fechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fechaDevolucion")
    private LocalDate fechaDevolucion;



    public Prestamo(Usuario usuario, Ejemplar ejemplar, LocalDate fechaInicio) {
        this.usuario = usuario;
        this.ejemplar = ejemplar;
        this.fechaInicio = fechaInicio;
    }

    public Prestamo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer idUsuario) {
        Conexion conexion = new Conexion();
        this.usuario = conexion.getGestor().find(Usuario.class, idUsuario);
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Integer idEjemplar) {
        Conexion conexion = new Conexion();
        this.ejemplar = conexion.getGestor().find(Ejemplar.class, idEjemplar);
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        setFechaDevolucion();
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion() {
        this.fechaDevolucion = fechaInicio.plusDays(15);
    }

    @Override
    public String toString() {
        return String.format(
                "%s, %s, %s, %s, %s", this.id, this.usuario.getNombre().toString(), this.ejemplar.getIsbn().toString(), this.fechaInicio.toString(), this.fechaDevolucion);
    }
}