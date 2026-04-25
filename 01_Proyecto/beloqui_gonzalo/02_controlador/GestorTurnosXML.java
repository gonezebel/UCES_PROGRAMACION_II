package com.beloqui.controlador;

import com.beloqui.modelo.Agenda;
import com.beloqui.modelo.Paciente;
import com.beloqui.modelo.Profesional;
import com.beloqui.modelo.Turno;
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

public class GestorTurnosXML {
    private final String nombreArchivo;

    public GestorTurnosXML(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void guardarTurnos(List<Turno> turnos) {
        try {
            crearCarpetaDatos();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();

            Element raiz = documento.createElement("turnos");
            documento.appendChild(raiz);

            for (Turno turno : turnos) {
                raiz.appendChild(crearElementoTurno(documento, turno));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(documento), new StreamResult(new File(this.nombreArchivo)));
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Error al guardar turnos XML: " + e.getMessage());
        }
    }

    public List<Turno> leerTurnos(List<Paciente> pacientes, List<Profesional> profesionales,
            List<Agenda> agendas) {
        List<Turno> turnos = new ArrayList<>();
        File archivo = new File(this.nombreArchivo);
        if (!archivo.exists()) {
            return turnos;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(archivo);
            NodeList nodosTurno = documento.getElementsByTagName("turno");

            for (int i = 0; i < nodosTurno.getLength(); i++) {
                Element elementoTurno = (Element) nodosTurno.item(i);
                Paciente paciente = buscarPacientePorId(pacientes,
                        Integer.parseInt(obtenerTexto(elementoTurno, "idPaciente")));
                Profesional profesional = buscarProfesionalPorId(profesionales,
                        Integer.parseInt(obtenerTexto(elementoTurno, "idProfesional")));
                Agenda agenda = buscarAgendaPorId(agendas,
                        Integer.parseInt(obtenerTexto(elementoTurno, "idAgenda")));

                turnos.add(new Turno(
                        Integer.parseInt(obtenerTexto(elementoTurno, "idTurno")),
                        paciente,
                        profesional,
                        agenda,
                        obtenerTexto(elementoTurno, "fecha"),
                        obtenerTexto(elementoTurno, "hora"),
                        obtenerTexto(elementoTurno, "estado")));
            }
        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException e) {
            System.out.println("Error al leer turnos XML: " + e.getMessage());
        }

        return turnos;
    }

    private Element crearElementoTurno(Document documento, Turno turno) {
        Element elementoTurno = documento.createElement("turno");

        agregarElemento(documento, elementoTurno, "idTurno", String.valueOf(turno.getIdTurno()));
        agregarElemento(documento, elementoTurno, "idPaciente", String.valueOf(obtenerIdPaciente(turno)));
        agregarElemento(documento, elementoTurno, "idProfesional", String.valueOf(obtenerIdProfesional(turno)));
        agregarElemento(documento, elementoTurno, "idAgenda", String.valueOf(obtenerIdAgenda(turno)));
        agregarElemento(documento, elementoTurno, "fecha", turno.getFecha());
        agregarElemento(documento, elementoTurno, "hora", turno.getHora());
        agregarElemento(documento, elementoTurno, "estado", turno.getEstado());

        return elementoTurno;
    }

    private int obtenerIdPaciente(Turno turno) {
        return turno.getPaciente() == null ? 0 : turno.getPaciente().getIdPaciente();
    }

    private int obtenerIdProfesional(Turno turno) {
        return turno.getProfesional() == null ? 0 : turno.getProfesional().getIdProfesional();
    }

    private int obtenerIdAgenda(Turno turno) {
        return turno.getAgenda() == null ? 0 : turno.getAgenda().getIdAgenda();
    }

    private Paciente buscarPacientePorId(List<Paciente> pacientes, int idPaciente) {
        for (Paciente paciente : pacientes) {
            if (paciente.getIdPaciente() == idPaciente) {
                return paciente;
            }
        }
        return null;
    }

    private Profesional buscarProfesionalPorId(List<Profesional> profesionales, int idProfesional) {
        for (Profesional profesional : profesionales) {
            if (profesional.getIdProfesional() == idProfesional) {
                return profesional;
            }
        }
        return null;
    }

    private Agenda buscarAgendaPorId(List<Agenda> agendas, int idAgenda) {
        for (Agenda agenda : agendas) {
            if (agenda.getIdAgenda() == idAgenda) {
                return agenda;
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
