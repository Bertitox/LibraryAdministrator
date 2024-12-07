package DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "Libro", schema = "bibliotecaNueva")
public class Libro {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "autor", nullable = false, length = 100)
    private String autor;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (validarISBN(isbn)) {
            this.isbn = isbn;
        }
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    private boolean validarISBN(String isbn) {
        String isbnRegex13 = "^(?:ISBN(?:-13)?:?\\ )?(?=[0-9]{13}$|(?=(?:[0-9]+[-\\ ]){4})[-\\ 0-9]{17}$)97[89][-\\ ]?[0-9]{1,5}[-\\ ]?[0-9]+[-\\ ]?[0-9]+[-\\ ]?[0-9]$";
        Pattern pattern = Pattern.compile(isbnRegex13);
        Matcher matcher = pattern.matcher(isbn);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(
                "%s, %s, %s", this.isbn, this.titulo, this.autor);
    }
}

