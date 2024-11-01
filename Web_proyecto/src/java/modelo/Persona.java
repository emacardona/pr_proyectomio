/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author pc
 */
abstract class Persona {
    
    //Atributos
    private int id;
    private String nit,direccion,telefono;
    
    //constructos vacio
    public Persona(){}
    //constructor de los atributos 
    public Persona(int id, String nit, String direccion, String telefono) {
        this.id = id;
       
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    
    //metodos getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
     // Métodos agregar, modificar y eliminar 
    
    public int agregar(){return 0;}
    public int modificar (){return 0;}
    public int eliminar (){return 0;}
    
    
}
