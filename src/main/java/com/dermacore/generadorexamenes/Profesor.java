package com.dermacore.generadorexamenes;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Profesor extends Usuario {
    @XmlElement(name = "asignatura")
    private List<Asignatura> asignaturas;

    public Profesor() {
        super();
    }


    public Profesor(String id, String nombre,String apellidop,String apellidom, String usuario, String password, String correo) {
        super(id, nombre, apellidop, apellidom, usuario, password, correo);
        this.asignaturas = new ArrayList<Asignatura>();
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
    public Examen crearExamen(Asignatura asignatura, Unidad unidad, int numeroDePreguntas,Profesor profesor) {

        if (!this.asignaturas.contains(asignatura) || !asignatura.getUnidades().contains(unidad)) {
            System.out.println("Error: La asignatura o unidad no corresponden a este profesor.");
            return null;
        }

        List<Pregunta> bancoDePreguntasDeUnidad = new ArrayList<>();
        for (Tema tema : unidad.getTemas()) {
            bancoDePreguntasDeUnidad.addAll(tema.getPreguntas());
        }

        if (bancoDePreguntasDeUnidad.size() < numeroDePreguntas) {
            System.out.println("Error: No hay suficientes preguntas en la unidad para crear un examen de " + numeroDePreguntas + " preguntas.");
            return null;
        }
        GeneradorExamenes generador = new GeneradorExamenes(bancoDePreguntasDeUnidad);
        return generador.generar(numeroDePreguntas, asignatura, unidad, profesor);
    }

    // Metodos CRUD

    public void agregarAsignatura(Asignatura nuevaAsignatura) {
        if (!this.asignaturas.contains(nuevaAsignatura)) {
            this.asignaturas.add(nuevaAsignatura);
        }
    }

    public boolean eliminarAsignatura(Asignatura asignaturaAEliminar) {
        return this.asignaturas.remove(asignaturaAEliminar);
    }

}
