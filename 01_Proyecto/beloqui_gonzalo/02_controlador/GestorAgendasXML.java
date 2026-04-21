package com.beloqui.controlador;

import com.beloqui.modelo.Agenda;
import com.beloqui.modelo.Profesional;
import java.io.File;
import java.io.IOException;
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

public class GestorAgendasXML {
    private final String nombreArchivo;

    public GestorAgendasXML(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void guardarAgendas(List<Agenda> agendas) {
        try {
            crearCarpetaDatos();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();

            Element raiz = documento.createElement("agendas");
            documento.appendChild(raiz);

            for (Agenda agenda : agendas) {
                raiz.appendChild(crearElementoAgenda(documento, agenda));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(documento), new StreamResult(new File(this.nombreArchivo)));
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Error al guardar agendas XML: " + e.getMessage());
        }
    }

    public List<Agenda> leerAgendas() {
        return leerAgendas(new ArrayList<Profesional>());
    }

    public List<Agenda> leerAgendas(List<Profesional> profesionales) {
        List<Agenda> agendas = new ArrayList<>();
        File archivo = new File(this.nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("Archivo XML de agendas no encontrado.");
            return agendas;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(archivo);
            NodeList nodosAgenda = documento.getElementsByTagName("agenda");

            for (int i = 0; i < nodosAgenda.getLength(); i++) {
                Element elementoAgenda = (Element) nodosAgenda.item(i);
                int idProfesional = Integer.parseInt(obtenerTexto(elementoAgenda, "idProfesional"));
                Profesional profesional = buscarProfesionalPorId(profesionales, idProfesional);

                agendas.add(new Agenda(
                        Integer.parseInt(obtenerTexto(elementoAgenda, "idAgenda")),
                        profesional,
                        obtenerTexto(elementoAgenda, "diaSemana"),
                        obtenerTexto(elementoAgenda, "horaInicio"),
                        obtenerTexto(elementoAgenda, "horaFin"),
                        obtenerTexto(elementoAgenda, "estado")));
            }
        } catch (ParserConfigurationException | SAXException | IOException
                | NumberFormatException e) {
            System.out.println("Error al leer agendas XML: " + e.getMessage());
        }

        return agendas;
    }

    private Element crearElementoAgenda(Document documento, Agenda agenda) {
        Element elementoAgenda = documento.createElement("agenda");

        agregarElemento(documento, elementoAgenda, "idAgenda", String.valueOf(agenda.getIdAgenda()));
        agregarElemento(documento, elementoAgenda, "idProfesional",
                String.valueOf(obtenerIdProfesional(agenda)));
        agregarElemento(documento, elementoAgenda, "diaSemana", agenda.getDiaSemana());
        agregarElemento(documento, elementoAgenda, "horaInicio", agenda.getHoraInicio());
        agregarElemento(documento, elementoAgenda, "horaFin", agenda.getHoraFin());
        agregarElemento(documento, elementoAgenda, "estado", agenda.getEstado());

        return elementoAgenda;
    }

    private int obtenerIdProfesional(Agenda agenda) {
        if (agenda.getProfesional() == null) {
            return 0;
        }
        return agenda.getProfesional().getIdProfesional();
    }

    private Profesional buscarProfesionalPorId(List<Profesional> profesionales, int idProfesional) {
        for (Profesional profesional : profesionales) {
            if (profesional.getIdProfesional() == idProfesional) {
                return profesional;
            }
        }
        return null;
    }

    private void agregarElemento(Document documento, Element padre, String nombre, String valor) {
        Element elemento = documento.createElement(nombre);
        elemento.appendChild(documento.createTextNode(valor == null ? "" : valor));
        padre.appendChild(elemento);
    }

    private String obtenerTexto(Element elemento, String etiqueta) {
        NodeList nodos = elemento.getElementsByTagName(etiqueta);
        if (nodos.getLength() == 0) {
            return "";
        }
        return nodos.item(0).getTextContent();
    }

    private void crearCarpetaDatos() {
        File archivo = new File(this.nombreArchivo);
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}
