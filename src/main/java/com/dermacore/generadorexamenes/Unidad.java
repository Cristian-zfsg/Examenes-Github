package com.dermacore.generadorexamenes;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Unidad {
    private String nombre;
    @XmlElement(name = "tema")
    private List<Tema> temas = new ArrayList<>();

    public Unidad() {} // Serializar en XML

    public Unidad(String nombre, List<Tema> temas) {
        this.temas = temas;
        this.nombre = nombre;
    }

    // Constructor para la interfaz
    public Unidad(String nombre) {
        this.nombre = nombre;
    }


    public void agregarTema(Tema t) {
        temas.add(t);
    }
    public List<Tema> getTemas() {
        return temas;
    }
    public void setTemas(List<Tema> temas) {
        this.temas = temas;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    // Metodo CRUD
    public boolean eliminarTema(Tema temaAEliminar) {
        return temas.remove(temaAEliminar);
    }
}
