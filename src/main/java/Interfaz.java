import DAO.DAOEjemplar;
import DAO.DAOLibro;
import DAO.DAOPrestamo;
import DAO.DAOUsuario;
import DTO.Ejemplar;
import DTO.Libro;
import DTO.Prestamo;
import DTO.Usuario;

import java.time.LocalDate;
import java.util.Scanner;

public class Interfaz {
    public ConsoleColors letraColores = new ConsoleColors();
    Scanner sc = new Scanner(System.in);
    DAOEjemplar daoEjemplar = new DAOEjemplar();
    DAOPrestamo daoPrestamo = new DAOPrestamo();
    DAOLibro daoLibro = new DAOLibro();
    DAOUsuario daoUsuario = new DAOUsuario();

    Boolean tipo = false;

    public Interfaz() {
        String valor = "";
        Boolean fin = false;
        menu();
        do {
            valor = cabezera();
            fin = opciones(valor);
        } while (!fin);
    }

    public void menu() {
        System.out.println("\n" +
                "┌─┐┌─┐┌─┐┌─┐┌─┐┌─┐  ┌─┐  ┌┬┐┌─┐┌┬┐┌─┐┌─┐\n" +
                "├─┤│  │  ├┤ └─┐│ │  ├─┤   ││├─┤ │ │ │└─┐\n" +
                "┴ ┴└─┘└─┘└─┘└─┘└─┘  ┴ ┴  ─┴┘┴ ┴ ┴ └─┘└─┘\n");
        cuentaAtras(1);
    }

    public String cabezera() {
        saltoLinea();
        String mensaje = "INTRODUCE UNA OPCIÓN";
        int longitudBarra = 40;
        System.out.println("╔" + "═".repeat(longitudBarra) + " " + mensaje + " " + "═".repeat(longitudBarra) + "╗");
        System.out.println("╚" + "═".repeat(2 * longitudBarra + mensaje.length() + 2) + "╝");
        System.out.println();


        System.out.printf("%-35s", letraColores.GREEN + "1. Crear usuario" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.GREEN + "5. Crear libro" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.GREEN + "9. Crear ejemplar" + letraColores.RESET);
        System.out.printf(letraColores.GREEN + "14. Crear prestamo" + letraColores.RESET + "\n");

        System.out.printf("%-35s", letraColores.ORANGE + "2. Ver usuarios" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.ORANGE + "6. Ver libros" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.ORANGE + "10. Ver ejemplares" + letraColores.RESET);
        System.out.printf(letraColores.ORANGE + "15. Ver prestamos" + letraColores.RESET + "\n");

        System.out.printf("%-35s", letraColores.RED + "3. Eliminar usuarios" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.RED + "7. Eliminar libros" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.RED + "11. Eliminar ejemplares" + letraColores.RESET);
        System.out.printf(letraColores.RED + "16. Eliminar prestamos" + letraColores.RESET + "\n");

        System.out.printf("%-35s", letraColores.PURPLE + "4. Modificar usuario" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.PURPLE + "8.Modificar libro" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.PURPLE + "12. Modificar ejemplar" + letraColores.RESET);
        System.out.printf(letraColores.PURPLE + "17.Modificar prestamo" + letraColores.RESET + "\n");

        System.out.printf("%-35s", letraColores.PURPLE + "" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.PURPLE + "" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.YELLOW + "13. Libros en stock" + letraColores.RESET);
        System.out.printf("%-35s", letraColores.PURPLE + "" + letraColores.RESET + "\n");



        System.out.println();
        mensaje = "X -> SALIR";

        longitudBarra = 45;
        System.out.println("╔" + "═".repeat(longitudBarra) + " " + mensaje + " " + "═".repeat(longitudBarra) + "╗");
        System.out.println("╚" + "═".repeat(2 * longitudBarra + mensaje.length() + 2) + "╝");

        System.out.print("Acción -> ");
        String valor = sc.next();
        System.out.println();
        System.out.println();
        return valor;
    }

    private Boolean opciones(String valor) {
        switch (valor) {
            case "1":
                Usuario u = new Usuario();
                saltoLinea();
                String dni = pedirDato("el dni del usuario");
                String nombre = pedirDato("el nombre del usuario");
                String email = pedirDato("el email del usuario");
                String pass = pedirDato("la contraseña del usuario");

                String tipo;
                System.out.println("""
                        Introduce el tipo de usuario:
                        1. Normal
                        2. Admin""");
                tipo = sc.next();

                u.setDni(dni);
                u.setNombre(nombre);
                u.setEmail(email);
                u.setPassword(pass);
                if (tipo == "1") {
                    u.setTipo("normal");
                } else {
                    u.setTipo("administrador");
                }
                daoUsuario.agregarUsuario(u);
                bucleTecla();
                break;
            case "2":
                saltoLinea();
                daoUsuario.verUsuarios();
                bucleTecla();
                break;
            case "3":
                saltoLinea();
                daoUsuario.verUsuarios();
                Integer id = Integer.valueOf(pedirDato("el id del usuario a eliminar"));
                daoUsuario.eliminarUsuario(id);
                bucleTecla();
                break;
            case "4":
                saltoLinea();
                daoUsuario.verUsuarios();
                Integer id5 = Integer.valueOf(pedirDato("el id del usuario a modificar"));
                System.out.println("""
                        Que quieres cambiar:
                        1. DNI
                        2. Nombre
                        3. Email
                        4. Contraseña
                        5. Tipo""");
                Integer opcion = sc.nextInt();
                String nuevoValor = pedirDato("el nuevo valor");
                daoUsuario.updateUsuario(id5, opcion, nuevoValor);
                bucleTecla();
                break;
            case "5":
                saltoLinea();
                Libro libro = new Libro();
                String isbn = pedirDato("el ISBN del libro");
                String titulo = pedirDato("el titulo del libro");
                String autor = pedirDato("el autor del libro");
                libro.setIsbn(isbn);
                libro.setTitulo(titulo);
                libro.setAutor(autor);
                daoLibro.agregarLibro(libro);
                bucleTecla();
                break;
            case "6":
                saltoLinea();
                daoLibro.verLibros();
                bucleTecla();
                break;
            case "7":
                saltoLinea();
                daoLibro.verLibros();
                String isbn1 = pedirDato("el isbn del libro a eliminar");
                daoLibro.eliminarLibro(isbn1);
                bucleTecla();
                break;
            case "8":
                saltoLinea();
                 daoLibro.verLibros();
                 String id6 = (pedirDato("el isbn del libro a modificar"));
                System.out.println("""
                        Que quieres modificar:
                        1. Titulo
                        2. Autor""");
                Integer opcion2 = sc.nextInt();
                String nuevoValor2 = pedirDato("el nuevo valor");

                daoLibro.updateLibro(id6, opcion2, nuevoValor2);

                bucleTecla();
                break;
            case "9":
                saltoLinea();
                daoLibro.verLibros();
                String isbn2 = pedirDato("el isbn del libro");
                Ejemplar e = new Ejemplar();
                System.out.println("""
                        Selecciona el estado del libro:
                        1. Disponible
                        2. Prestado
                        3. Dañado""");
                Integer estado = sc.nextInt();

                e.setIsbn(isbn2);
                if (estado == 1) {
                    e.setEstado("Disponible");
                } else if (estado == 2) {
                    e.setEstado("Prestado");
                } else {
                    e.setEstado("Dañado");
                }
                daoEjemplar.agregarEjemplar(e);
                bucleTecla();
                break;
            case "10":
                saltoLinea();
                daoEjemplar.verEjemplares();
                bucleTecla();
                break;
            case "11":
                saltoLinea();
                daoEjemplar.verEjemplares();
                String id1 = pedirDato("el id del ejemplar a eliminar");
                daoEjemplar.eliminarEjemplar(id1);
                bucleTecla();
                break;
            case "12":
                saltoLinea();
                daoEjemplar.verEjemplares();
                Integer id7 = Integer.valueOf(pedirDato("el id del ejemplar a eliminar"));
                System.out.println("""
                        1. ISBN
                        2. Estado""");
                Integer opcion3 = sc.nextInt();

                Boolean opcion4 = false;

                if(opcion3 == 1){
                    opcion4 = true;
                }

                String  nuevoValor3 = pedirDato("el nuevo valor");
                daoEjemplar.updateEjemplar(id7, opcion4, nuevoValor3);

                bucleTecla();
                break;
            case "13":
                saltoLinea();
                System.out.println("Ejemplares con stock en el almacen: " + daoEjemplar.stockDisponible());
                bucleTecla();
                break;
            case "14":
                saltoLinea();
                Prestamo p = new Prestamo();
                daoUsuario.verUsuarios();
                Integer id2 = Integer.valueOf(pedirDato("el id del usuario"));
                daoEjemplar.verEjemplares();
                Integer id3 = Integer.valueOf(pedirDato("el id del ejemplar"));
                Integer dia = Integer.valueOf(pedirDato("el día de inicio del prestamo"));
                Integer mes = Integer.valueOf(pedirDato("el mes de inicio del prestamo"));
                Integer anno = Integer.valueOf(pedirDato("el año de inicio del prestamo"));
                LocalDate fecha = LocalDate.of(anno, mes, dia);
                p.setUsuario(id2);
                p.setEjemplar(id3);
                p.setFechaInicio(fecha);
                daoPrestamo.nuevoPrestamo(p);
                daoEjemplar.actualizarLista();
                bucleTecla();
                break;
            case "15":
                saltoLinea();
                daoPrestamo.verPrestamos();
                bucleTecla();
                break;
            case "16":
                saltoLinea();
                daoPrestamo.verPrestamos();
                Integer id4 = Integer.valueOf(pedirDato("el id del prestamo a eliminar"));
                daoPrestamo.eliminarPrestamo(id4);
                bucleTecla();
                break;
            case "17":
                saltoLinea();
                daoPrestamo.verPrestamos();
                Integer id8 =Integer.valueOf(pedirDato("el id del prestamo"));
                System.out.println("""
                        Que quieres modificar
                        1. Usuario id
                        2. Ejemplar id""");
                Integer opcion1 = sc.nextInt();
                String  nuevoValor1 = pedirDato("el nuevo valor");
                daoPrestamo.updatePrestamo(id8, opcion1, nuevoValor1);
                bucleTecla();
                break;
            case "x":
            case "X":
                return true;
            default:
                System.out.printf(letraColores.RED + "ERROR, INTRODUCE LA ACCION DE NUEVO" + letraColores.RESET);
        }
        return false;
    }

    public void saltoLinea() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    public String pedirDato(String opcion) {
        System.out.println("Introduce " + opcion);
        String dato = sc.next();
        return dato;
    }

    public Boolean presionarEnter() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Presiona \"Enter\" para continuar...");
        String resultado = sc.nextLine();
        if (resultado.isEmpty()) {
            return true;
        }
        return false;
    }

    public void bucleTecla() {
        while (!presionarEnter()) {
        }
    }

    public void cuentaAtras(Integer segundos) {
        try {
            for (int i = segundos; i >= 0; i--) {
                System.out.print("\r" + i + "s");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDate convertirFecha(String valor) {
        String partes[] = valor.split("/");
        LocalDate fecha = LocalDate.of(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
        return fecha;
    }
}
