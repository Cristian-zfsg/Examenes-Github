package com.dermacore.generadorexamenes;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class GuardadoEnXML {

    private static final String RUTA_ARCHIVO = "profesor_datos.xml";

    public static void guardarProfesor(Profesor profesor) {
        try {
            JAXBContext context = JAXBContext.newInstance(Profesor.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(profesor, new File(RUTA_ARCHIVO));
            System.out.println("Datos guardados en XML correctamente.");
        } catch (JAXBException e) {
            System.err.println("Error al guardar en XML: " + e.getMessage());
        }
    }

    public static Profesor cargarProfesor() {
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("No se encontró el archivo XML. Se usará un profesor nuevo.");
            return null;
        }

        try {
            JAXBContext context = JAXBContext.newInstance(Profesor.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Profesor) unmarshaller.unmarshal(archivo);
        } catch (JAXBException e) {
            System.err.println("Error al cargar desde XML: " + e.getMessage());
            Throwable cause = e.getLinkedException();
            if (cause != null) {
                cause.printStackTrace();
            } else {
                e.printStackTrace();
            }
            return null;
        }
    }



}
