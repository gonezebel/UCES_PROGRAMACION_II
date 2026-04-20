
![Logo UCES](../02_imagenes/logo_uces.svg)


**Carrera:** Tecnicatura Universitaria en Programación

**Asignatura:** Programación II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui


## PROYECTO: Sistema de turnos de centro de salud de cercanía

### Fundamentación

El proyecto consiste en desarrollar un sistema de gestión de turnos para un centro de salud local, el cual permitirá registrar pacientes y profesionales, crear agendas de atención, asignar y anular turnos, y enviar recordatorios de consultas. El tema surge del boceto trabajado en la materia Diseño Orientado a Objetos (2024), donde se definieron teóricamente las clases, actividades, secuencias y casos de uso, bajo los parámetros de diseño UML. Link del repositorio público: https://github.com/gonezebel/SistemaGestionTurnos.git

La propuesta es pertinente y factible de prototipar en Java, porque permite aplicar correctamente encapsulamiento, herencia, polimorfismo, interfaces, constructores, getters, setters y métodos de negocio en clases representativas del dominio.

## Lineamientos del proyecto

- Nombre del proyecto: beloqui_gonzalo
- Paquete para guardar las clases de los objetos: com.beloqui.modelo
- Paquete para guardar el archivo principal: com.beloqui.main
- Nombres de las clases: representativos del contenido de cada clase
- Nombre del archivo principal: Principal.java

## Estructura del proyecto

```
beloqui_gonzalo
|- 00_principal
|  \- Principal.java
|- 01_modelo
|  |- Persona.java
|  |- Paciente.java
|  |- Profesional.java
|  |- Agenda.java
|  |- Turno.java
|  |- Notificacion.java
|  \- Notificable.java
|- 02_controlador
|  |- GestorPacientesTexto.java
|  |- GestorProfesionalesTexto.java
|  \- GestorAgendasXML.java
|- 04_datos
|  |- pacientes.txt
|  |- profesionales.txt
|  \- agendas.xml
```

## Actividades de entrega

I.[Act1_Clases principales del proyecto_20260328](../00_Documentos_Desarrollo/01_Actividad_ClasesPrincipales.md)

II.[Act2_Técnicas de entrada/salida y manejo de archivos_20260421](../00_Documentos_Desarrollo/02_Actividad_Archivos.md)






## Activdad Número 2 -  Técnicas de entrada/salida y manejo de archivos

### Diseño de archivos de texto para persistencia de clases 

Para aplicar el concepeto de persistencia se diseñaron dos archivos independientes que permiten guardar y recuperar datos correspondientes a dos clases principales del sistema: Paciente y Profesional. El primer archivo se denomina pacientes.txt y almacena la información de los objetos de la clase Paciente. Cada línea del archivo representa un paciente completo, utilizando una estructura de campos separados por punto y coma. Esta organización permite que los datos puedan guardarse como texto plano y, posteriormente, recuperarse para reconstruir los objetos dentro del sistema.

Para administrar este archivo se implementó la clase GestorPacientesTexto, ubicada en el paquete controlador. Esta clase contiene los métodos necesarios para guardar una lista de pacientes, agregar un nuevo paciente al archivo y leer los registros existentes para reconstruir objetos Paciente.

La estructura definida para cada registro de paciente es la siguiente:

```
nombre;apellido;dni;telefono;numeroHistoriaClinica;obraSocial;email
```

El segundo archivo se denomina profesionales.txt y almacena la información de los objetos de la clase Profesional. Al igual que en el caso anterior, cada línea representa un profesional completo, con sus atributos separados por punto y coma.

La estructura definida para cada registro de profesional es la siguiente:

```
nombre;apellido;dni;telefono;matricula;especialidad;emailInstitucional
```

Para administrar este archivo se implementó la clase GestorProfesionalesTexto, también ubicada en el paquete controlador. Esta clase permite guardar profesionales, agregar nuevos registros y recuperar la información almacenada para reconstruir objetos Profesional.

En ambos casos, las clases Paciente y Profesional sobreescriben el método toString() para generar una representación en cadena de caracteres compatible con la estructura del archivo. Además, se agregaron métodos estáticos fromString(String linea) que permiten interpretar cada línea leída y convertirla nuevamente en un objeto del sistema.
Las operaciones de lectura y escritura utilizan BufferedReader, BufferedWriter, FileReader y FileWriter, aplicando manejo de excepciones mediante try-with-resources, según lo observado en el contenido de la unidad de estudio.

### Diseño de estructura XML para persistencia de una clase

La persistencia bajo formato XML se eligió para guardar objetos de la clase Paciente. El archivo generado se denomina pacientes.xml.y utiliza una etiqueta raíz <pacientes>, que contiene uno o más elementos <paciente>, donde cada paciente posee etiquetas internas que representan sus atributos principales.

Por ejemplo, la secuencia concreta de los objetos testeados es la siguiente:

```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<pacientes>
    <paciente>
        <nombre>Gonzalo</nombre>
        <apellido>Beloqui</apellido>
        <dni>35426789</dni>
        <telefono>1155551234</telefono>
        <numeroHistoriaClinica>1025</numeroHistoriaClinica>
        <obraSocial>Swiss Medical</obraSocial>
        <email>gonzalo.beloqui@mail.com</email>
    </paciente>
    <paciente>
        <nombre>Ana</nombre>
        <apellido>Gomez</apellido>
        <dni>40111222</dni>
        <telefono>1166667788</telefono>
        <numeroHistoriaClinica>1026</numeroHistoriaClinica>
        <obraSocial>OSDE</obraSocial>
        <email>ana.gomez@mail.com</email>
    </paciente>
</pacientes>

```

Para implementar esta funcionalidad se creó la clase GestorPacientesXML, ubicada en el paquete controlador. Esta clase permite guardar una lista de pacientes en formato XML y recuperar posteriormente los datos almacenados. En el diseño utilizan las clases de JAXP y DOM, tales como DocumentBuilderFactory, DocumentBuilder, Document, Element, NodeList, TransformerFactory y Transformer.
En su funcionamiento, primeramente, el método de guardado construye un documento XML con la etiqueta raíz <pacientes> y agrega un nodo <paciente> por cada objeto de la lista. Dentro de cada nodo se crean etiquetas individuales para nombre, apellido, dni, telefono, numeroHistoriaClinica, obraSocial y email. Luego, el método de lectura carga el archivo XML, recorre los nodos <paciente> y obtiene el contenido de cada etiqueta para reconstruir objetos de la clase Paciente. 
