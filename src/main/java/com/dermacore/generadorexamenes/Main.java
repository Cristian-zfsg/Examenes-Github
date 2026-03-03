package com.dermacore.generadorexamenes;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Estudiante> AlumnosLTI = new ArrayList<>();
        Estudiante ericbueno = new Estudiante("123", "Eric", "Rodriguez","Sanchez","rodri","rodrisito","rooo");
        Estudiante ericMALO1 = new Estudiante("123", "Eric Yabin", "Atanasio","Sanchez","rodri","rodrisito","rooo");
        Estudiante dirty = new Estudiante("123", "Arainam", "Aca","Sanchez","santiago","rodrisito","rooo");
        AlumnosLTI.add(ericbueno);
        AlumnosLTI.add(ericMALO1);
        AlumnosLTI.add(dirty);


        List<Pregunta> Preguntasu1 = new ArrayList<>();
        List<Inciso> BancoPreguntau1 = new ArrayList<>();
        List<Inciso> BancoPreguntau2 = new ArrayList<>();
        List<Inciso> BancoPreguntau3 = new ArrayList<>();
        List<Pregunta> Sumas = new ArrayList<>();
        Profesor Usuario = new Profesor("123", "Edgar Israel", "Otamendi","Garcia","Otamendi","1234","otamendixd");
        List<Asignatura> AsignaturasUsuario = new ArrayList<>();
        List<Unidad> Parcial1 = new ArrayList<>();
        List<Tema> TemasParcial1 = new ArrayList<>();

        Asignatura Matematicas = new Asignatura("Matematicas Discretas",Parcial1);
        Matematicas.setEstudiantesinscritos(AlumnosLTI);
        AsignaturasUsuario.add(Matematicas);
        Unidad u1 = new Unidad("Unidad 1", TemasParcial1);
        Matematicas.agregarUnidad(u1);
        Tema sumas = new Tema("Sumas", Sumas);
        u1.agregarTema(sumas);


        Inciso s1 = new Inciso( "3",  false);
        Inciso s2 = new Inciso( "4",  false);
        Inciso s3 = new Inciso( "pez",  true);
        Inciso s4 = new Inciso( "Ninguna de las anteriores",  false);
        List<Inciso> Incisos = new ArrayList<>();
        Incisos.add(s1);
        Incisos.add(s2);
        Incisos.add(s3);
        Incisos.add(s4);
        Pregunta ps1 = new Pregunta("Cuanto es 2 + 2?", Incisos);
        sumas.agregarPregunta(ps1);



        Usuario.setAsignaturas(AsignaturasUsuario);



        Inciso a = new Inciso( "Quiropractico",  false);
        Inciso b = new Inciso( "Trex",  false);
        Inciso c = new Inciso( "Unmungosaurio",  true);
        Inciso d = new Inciso( "Ninguna de las anteriores",  false);
        BancoPreguntau1.add(a);
        BancoPreguntau1.add(b);
        BancoPreguntau1.add(c);
        BancoPreguntau1.add(d);
        Pregunta p1 = new Pregunta("Cual es el mejor dinosaurio?", BancoPreguntau1);


        Inciso a1 = new Inciso( "Rojo",  false);
        Inciso b1 = new Inciso( "Azul",  false);
        Inciso c1 = new Inciso( "Verde",  true);
        Inciso d1 = new Inciso( "Ninguna de las anteriores",  false);
        BancoPreguntau2.add(a1);
        BancoPreguntau2.add(b1);
        BancoPreguntau2.add(c1);
        BancoPreguntau2.add(d1);
        Pregunta p2 = new Pregunta("Cual es el mejor color?", BancoPreguntau2);
        Preguntasu1.add(p2);
        Preguntasu1.add(p1);

        Inciso a2 = new Inciso( "Cebolla",  false);
        Inciso b2 = new Inciso( "Lechuga",  false);
        Inciso c2 = new Inciso( "Jitomate",  false);
        Inciso d2 = new Inciso( "Manzana caramelizada",  true);
        BancoPreguntau3.add(a2);
        BancoPreguntau3.add(b2);
        BancoPreguntau3.add(c2);
        BancoPreguntau3.add(d2);
        Pregunta p3 = new Pregunta("Cual es el mejor ingrediente de una hamburguesa?", BancoPreguntau3);
        Preguntasu1.add(p3);
        sumas.agregarPregunta(p1);
        sumas.agregarPregunta(p2);
        sumas.agregarPregunta(p3);
        Usuario.crearExamen(Matematicas,u1,4,Usuario);
        Examen si = Usuario.crearExamen(Matematicas,u1,4,Usuario);

       /// System.out.println(si);
        ///System.out.println(si.generarHojaDeRespuestas());

        AlumnosLTI.sort(Comparator.comparing(Estudiante::getApellidop));
        //login que identifique si es alumno o no:
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
      ///  System.out.println(Matematicas.AlumnosInsritos());

        for(Estudiante e : AlumnosLTI){
            System.out.println(e);
            Examen Examengenerado = new Examen(Sumas,Matematicas,u1,Usuario);
            e.setExamen(Examengenerado);
            System.out.println(e.getExamen());
            System.out.println(Examengenerado.generarHojaDeRespuestas());
        }

    }
}