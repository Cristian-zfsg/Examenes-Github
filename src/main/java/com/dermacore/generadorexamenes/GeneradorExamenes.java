package com.dermacore.generadorexamenes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class GeneradorExamenes {
    private List<Pregunta> bancoPreguntas;

    public GeneradorExamenes(List<Pregunta> bancoPreguntas) {
        this.bancoPreguntas = bancoPreguntas;
    }
    
    public Examen generar(int numeroDePreguntas, Asignatura asignatura, Unidad unidad, Profesor profesor) {

        Collections.shuffle(bancoPreguntas);

        List<Pregunta> preguntasSeleccionadas = new ArrayList<>(bancoPreguntas.subList(0, numeroDePreguntas));

        return new Examen(preguntasSeleccionadas, asignatura, unidad, profesor);
    }
}
