package com.usta;

public class Mascotas {
    private String nombre;
    private String animal;
    private String sexo;
    private String nacimiento;
    
    public Mascotas() {
    }
    public Mascotas(String nombre, String animal, String sexo, String nacimiento) {
        this.nombre = nombre;
        this.animal = animal;
        this.sexo = sexo;
        this.nacimiento = nacimiento;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getAnimal() {
        return animal;
    }
    public void setAnimal(String animal) {
        this.animal = animal;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public String getNacimiento() {
        return nacimiento;
    }
    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }
    

    @Override
    public String toString() {
        return nombre + "|" + animal + "|" + sexo + "|" + nacimiento;
    }
    
    

}
