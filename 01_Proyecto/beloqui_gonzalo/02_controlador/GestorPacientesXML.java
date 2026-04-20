package com.beloqui.controlador;

import com.beloqui.modelo.Paciente;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;

public class GestorPacientesXML {
    private final String nombreArchivo;

    public GestorPacientesXML(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void guardarPacientes(List<Paciente> pacientes) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();

            Element raiz = documento.createElement("pacientes");
            documento.appendChild(raiz);

            for (Paciente paciente : pacientes) {
                raiz.appendChild(crearElementoPaciente(documento, paciente));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(documento), new StreamResult(new File(this.nombreArchivo)));
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Error al guardar pacientes XML: " + e.getMessage());
        }
    }

    public List<Paciente> leerPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        File archivo = new File(this.nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("Archivo XML de pacientes no encontrado.");
            return pacientes;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(archivo);
            NodeList nodosPaciente = documento.getElementsByTagName("paciente");

            for (int i = 0; i < nodosPaciente.getLength(); i++) {
                Element elemento = (Element) nodosPaciente.item(i);
                pacientes.add(new Paciente(
                        obtenerTexto(elemento, "nombre"),
                        obtenerTexto(elemento, "apellido"),
                        obtenerTexto(elemento, "dni"),
                        obtenerTexto(elemento, "telefono"),
                        Integer.parseInt(obtenerTexto(elemento, "numeroHistoriaClinica")),
                        obtenerTexto(elemento, "obraSocial"),
                        obtenerTexto(elemento, "email")));
            }
        } catch (ParserConfigurationException | SAXException | IOException
                | NumberFormatException e) {
            System.out.println("Error al leer pacientes XML: " + e.getMessage());
        }

        return pacientes;
    }

    private Element crearElementoPaciente(Document documento, Paciente paciente) {
        Element elementoPaciente = documento.createElement("paciente");

        agregarElemento(documento, elementoPaciente, "nombre", paciente.getNombre());
        agregarElemento(documento, elementoPaciente, "apellido", paciente.getApellido());
        agregarElemento(documento, elementoPaciente, "dni", paciente.getDni());
        agregarElemento(documento, elementoPaciente, "telefono", paciente.getTelefono());
        agregarElemento(documento, elementoPaciente, "numeroHistoriaClinica",
                String.valueOf(paciente.getNumeroHistoriaClinica()));
        agregarElemento(documento, elementoPaciente, "obraSocial", paciente.getObraSocial());
        agregarElemento(documento, elementoPaciente, "email", paciente.getEmail());

        return elementoPaciente;
    }

    private void agregarElemento(Document documento, Element padre, String nombre, String valor) {
        Element elemento = documento.createElement(nombre);
        elemento.appendChild(documento.createTextNode(valor));
        padre.appendChild(elemento);
    }

    private String obtenerTexto(Element elemento, String etiqueta) {
        NodeList nodos = elemento.getElementsByTagName(etiqueta);
        if (nodos.getLength() == 0) {
            return "";
        }
        return nodos.item(0).getTextContent();
    }
}
