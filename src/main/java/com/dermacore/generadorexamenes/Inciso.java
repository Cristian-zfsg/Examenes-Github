package com.dermacore.generadorexamenes;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Inciso implements Comparable<Inciso> {
    String respuesta;
    char letra;
    boolean valor_verdad;

    public Inciso() {} // Serializar en XML

    public Inciso(String respuesta, boolean valor_verdad) {
        this.respuesta = respuesta;
        this.valor_verdad = valor_verdad;
        this.letra = ' ';
    }

    public Inciso(String respuesta, char letra, boolean valor_verdad) {
        this.respuesta = respuesta;
        this.letra = letra;
        this.valor_verdad = valor_verdad;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public boolean isValor_verdad() {
        return valor_verdad;
    }

    public void setValor_verdad(boolean valor_verdad) {
        this.valor_verdad = valor_verdad;
    }

    @Override
    public String toString() {
        return "\n"+ letra + ")" + respuesta ;
    }
    @Override
    public int compareTo(Inciso otro) {
        return Character.compare(this.letra, otro.letra);
    }

    public boolean isValorVerdad() {
        return valor_verdad;
    }
}
