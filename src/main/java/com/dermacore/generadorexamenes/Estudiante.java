package com.dermacore.generadorexamenes;

import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Usuario implements Comparable<Estudiante> {
    private List<Examen> examenesRealizados = new ArrayList<>();
    private List<Double> calificaciones = new ArrayList<>();
    private Examen examen;
    public Estudiante(String id, String nombre,String apellidop,String apellidom, String usuario, String password, String correo) {
        super(id, nombre, apellidop, apellidom, usuario, password, correo);
    }


    public List<Examen> getExamenesRealizados() {
        return examenesRealizados;
    }

    public void setExamenesRealizados(List<Examen> examenesRealizados) {
        this.examenesRealizados = examenesRealizados;
    }

    public List<Double> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Double> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public void registrarExamen(Examen examen, double calificacion) {
        examenesRealizados.add(examen);
        calificaciones.add(calificacion);
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    @Override
    public int compareTo(Estudiante o) {
        return  o.apellidop.compareTo(this.apellidop);
    }

    @Override
    public String toString() {
        return  "\n"+nombre + " " + apellidop + " " + apellidom + " " + usuario ;
    }
}
