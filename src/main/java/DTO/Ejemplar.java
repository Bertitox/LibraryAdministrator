package DTO;

import BBDD.Conexion;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Ejemplar", schema = "bibliotecaNueva")
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "isbn", nullable = false)
    private Libro isbn;

    @ColumnDefault("'Disponible'")
    @Lob
    @Column(name = "estado")
    private String estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn.getIsbn();
    }

    public void setIsbn(String isbn) {
        Conexion c = new Conexion();
        Libro l = c.getGestor().find(Libro.class, isbn);
        this.isbn = l;
    }

    public String getEstado() {
        return estado;
    }

    public void setPrestado() {
        this.estado = "Prestado";
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return String.format(
                "%s, %s, %s", this.id, this.isbn, this.estado);
    }
}