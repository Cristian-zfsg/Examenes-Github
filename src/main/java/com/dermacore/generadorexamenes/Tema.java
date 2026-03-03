package com.dermacore.generadorexamenes;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Tema {
    private String nombre;
    @XmlElement(name = "pregunta")
    private List<Pregunta> preguntas = new ArrayList<>();

    public Tema() {} // Serializar en XML

    public Tema(String nombre, List<Pregunta> preguntas) {
        this.nombre = nombre;
        this.preguntas = preguntas;
    }

    // Constructor simplificado para la interfaz
    public Tema(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
    public List<Pregunta> getPreguntas() { return preguntas; }

    //Metodos CRUD
    public void agregarPregunta(Pregunta p) {
        preguntas.add(p);
    }

    public boolean eliminarPregunta(Pregunta preguntaAEliminar) {
        return preguntas.remove(preguntaAEliminar);
    }

    public void actualizarPregunta(Pregunta preguntaActualizada) {
        for (int i = 0; i < preguntas.size(); i++) {
            if (preguntas.get(i).getArgumento().equals(preguntaActualizada.getArgumento())) {
                preguntas.set(i, preguntaActualizada);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}