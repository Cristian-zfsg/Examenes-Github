package com.dermacore.generadorexamenes;

import java.util.List;

public class Examen {
    private List<Pregunta> preguntas;
    private Asignatura asignatura;
    private Unidad unidad;
    private Profesor profesor;

    public Examen(List<Pregunta> preguntas, Asignatura asignatura, Unidad unidad) {
        this.asignatura = asignatura;
        this.unidad = unidad;
        this.preguntas = preguntas;
        asignarNumeroPregunta();
    }
    public Examen(List<Pregunta> preguntas, Asignatura asignatura, Unidad unidad, Profesor profesor) {
        this.asignatura = asignatura;
        this.unidad = unidad;
        this.preguntas = preguntas;
        this.profesor = profesor;
        asignarNumeroPregunta();
    }

    private void asignarNumeroPregunta() {
        for (int i = 0; i < preguntas.size(); i++) {
            preguntas.get(i).setNumeroPregunta(i + 1);
        }
    }

    public String generarHojaDeRespuestas() {
        StringBuilder sb = new StringBuilder("--- HOJA DE RESPUESTAS ---\n");

        preguntas.sort((p1, p2) -> Integer.compare(p1.getNumeroPregunta(), p2.getNumeroPregunta()));

        for (Pregunta pregunta : preguntas) {
            for (Inciso inciso : pregunta.getIncisos()) {
                if (inciso.isValorVerdad()) {
                    sb.append(pregunta.getNumeroPregunta()).append(". ").append(inciso.getLetra()).append("\n");
                    break;
                }
            }
        }
        return sb.toString();
    }

    // Getters y Setters
    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("EXAMEN DE: ").append(asignatura.getNombre().toUpperCase()).append("\n");
        sb.append("UNIDAD: ").append(unidad.getNombre().toUpperCase()).append("\n");
        sb.append("Imparte: ").append(profesor.nombre).append(" ").append(profesor.apellidop).append(" ").append(profesor.apellidom).append("\n");
        sb.append("========================================\n");
        for (Pregunta p : preguntas) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }
}
