package com.dermacore.generadorexamenes;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Asignatura {
    private HashMap<Asignatura, Integer> codigo;
    private List<Estudiante> estudiantesinscritos;
    private String nombre;
    @XmlElement(name = "unidad")
    private List<Unidad> unidades = new ArrayList<>();

    public Asignatura() {}

    /////lo llamas cuando ocupes crear la asignatura
    public Asignatura(String nombre, List<Unidad> unidades) {
        this.nombre = nombre;
    }

    // Constructor para la interfaz
    public Asignatura(String nombre) {
        this.nombre = nombre;
    }

    public Asignatura(String nombre, List<Unidad> unidades, HashMap<Asignatura, Integer> codigo, List<Estudiante> estudiantesinscritos) {
        this.nombre = nombre;
        this.unidades = unidades;
        this.codigo = codigo;
        this.estudiantesinscritos = estudiantesinscritos;
    }
    public void agregarUnidad(Unidad u) {
        if (!this.unidades.contains(u)) {
            unidades.add(u);
        }
    }
    public List<Unidad> getUnidades() {
        return unidades;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUnidades(List<Unidad> unidades) {
        this.unidades = unidades;
    }

    @XmlTransient // NO SE PUEDE GUARDAR UN HASH
    public HashMap<Asignatura, Integer> getCodigo() {
        return codigo;
    }

    public void setCodigo(HashMap<Asignatura, Integer> codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public List<Estudiante> getEstudiantesinscritos() {
        return estudiantesinscritos;
    }

    public void setEstudiantesinscritos(List<Estudiante> estudiantesinscritos) {
        this.estudiantesinscritos = estudiantesinscritos;
    }

    public StringBuilder AlumnosInsritos(){
        StringBuilder lista = new StringBuilder("La lista de alumnos inscritos en la asignatura" + getNombre() + " es: " + "\n");
        for(Estudiante e : estudiantesinscritos){
            lista.append(e.toString());
        }
        return lista;
    }

    @Override
    public String toString() {
        return nombre;
    }

    // --- Metodos CRUD ---

    public boolean eliminarUnidad(Unidad unidadAEliminar) {
        return unidades.remove(unidadAEliminar);
    }

    public void agregarEstudiante(Estudiante nuevoEstudiante) {
        if (!this.estudiantesinscritos.contains(nuevoEstudiante)) {
            this.estudiantesinscritos.add(nuevoEstudiante);
        }
    }

    public boolean eliminarEstudiante(Estudiante estudianteAEliminar) {
        return this.estudiantesinscritos.remove(estudianteAEliminar);
    }

}
