package com.dermacore.generadorexamenes;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;


import java.util.Collections;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Pregunta {
    private String Argumento;
    @XmlElement(name = "inciso")
    private List<Inciso> incisos;
    private int numero_pregunta;

    public Pregunta() {} // Serializar en el XML

    public Pregunta(String Argumento, List<Inciso> incisos) {
        this.Argumento = Argumento;
        this.numero_pregunta = 1;
        Collections.shuffle(incisos);
        asignarLetras(incisos);
        this.incisos = incisos;
    }
    public Pregunta(String Argumento, List<Inciso> incisos, int numero_pregunta) {
        this.Argumento = Argumento;
        this.numero_pregunta = numero_pregunta;
        Collections.shuffle(incisos);
        asignarLetras(incisos);
        this.incisos = incisos;
    }

    public String getArgumento() {
        return Argumento;
    }

    public void setArgumento(String argumento) {
        Argumento = argumento;
    }

    public List<Inciso> getIncisos() {
        return incisos;
    }

    public void setIncisos(List<Inciso> incisos) {
        // Al actualizar los incisos, también los mezclamos y re-asignamos las letras
        // para mantener la consistencia con cómo se crean las preguntas nuevas.
        Collections.shuffle(incisos);
        asignarLetras(incisos);
        this.incisos = incisos;
    }

    public int getNumeroPregunta() {
        return numero_pregunta;
    }

    public void setNumeroPregunta(int numero_pregunta) {
        this.numero_pregunta = numero_pregunta;
    }
    private void asignarLetras(List<Inciso> incisos) {
        for (int i = 0; i < incisos.size(); i++) {
            incisos.get(i).setLetra((char) ('a' + i));
        }
    }

    @Override
    public String toString() {
        return "\n" +numero_pregunta + ".-" +"\n" + Argumento + "\t" + incisos + "\t";
    }

}
