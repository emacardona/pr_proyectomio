/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Cliente {
    private int id;
    private String nombre;
    private String apellidos;
    private String nit;
    private Genero genero;
    private String telefono;
    private String correo;
    private String fechaIngreso;

    public enum Genero {
        MASCULINO, FEMENINO, OTRO
    }

    public Cliente(int id, String nombre, String apellidos, String nit, Genero genero, String telefono, String correo, String fechaIngreso) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nit = nit;
        this.genero = genero;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaIngreso = fechaIngreso;
    }

    public Cliente() {
        // Constructor vacío
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nit='" + nit + '\'' +
                ", genero=" + genero +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", fechaIngreso='" + fechaIngreso + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        if (apellidos == null || apellidos.isEmpty()) {
            throw new IllegalArgumentException("Los apellidos no pueden estar vacíos.");
        }
        this.apellidos = apellidos;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        if (nit == null || nit.isEmpty()) {
            throw new IllegalArgumentException("El NIT no puede estar vacío.");
        }
        this.nit = nit;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }
        this.correo = correo;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        if (fechaIngreso == null || fechaIngreso.isEmpty()) {
            throw new IllegalArgumentException("La fecha de ingreso no puede estar vacía.");
        }
        this.fechaIngreso = fechaIngreso;
    }
}
