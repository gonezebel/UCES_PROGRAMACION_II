![Logo UCES](../02_imagenes/logo_uces.svg)


**Carrera:** Tecnicatura Universitaria en Programación

**Asignatura:** Programación II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui

**Fecha de engrega:** 2026/04/21



## Activdad Número 2 -  Técnicas de entrada/salida y manejo de archivos

### Diseño de archivos de texto para persistencia de clases 

Para aplicar el concepeto de persistencia se diseñaron dos archivos independientes que permiten guardar y recuperar datos correspondientes a dos clases principales del sistema: Paciente y Profesional. El primer archivo se denomina pacientes.txt y almacena la información de los objetos de la clase Paciente. Cada línea del archivo representa un paciente completo, utilizando una estructura de campos separados por punto y coma. Esta organización permite que los datos puedan guardarse como texto plano y, posteriormente, recuperarse para reconstruir los objetos dentro del sistema.

Para administrar este archivo se implementó la clase GestorPacientesTexto, ubicada en el paquete controlador. Esta clase contiene los métodos necesarios para guardar una lista de pacientes, agregar un nuevo paciente al archivo y leer los registros existentes para reconstruir objetos Paciente.

+ [GestorPacientesTexto](../01_Proyecto/beloqui_gonzalo/02_controlador/GestorPacientesTexto.java)

Las operaciones principales son:

```
- guardarPacientes(List<Paciente> pacientes)
- agregarPaciente(Paciente paciente)
- leerPacientes()
```

El guardado completo sobrescribe el archivo, mientras que la operación de agregado utiliza escritura en modo append para incorporar nuevos registros sin eliminar los existentes.

La estructura definida para cada registro de paciente es la siguiente:

```
idPaciente;nombre;apellido;dni;telefono;numeroHistoriaClinica;obraSocial;email
```

El segundo archivo se denomina profesionales.txt y almacena la información de los objetos de la clase Profesional. Al igual que en el caso anterior, cada línea representa un profesional completo, con sus atributos separados por punto y coma.

+ [GestorProfesionalesTexto](../01_Proyecto/beloqui_gonzalo/02_controlador/GestorProfesionalesTexto.java)

La estructura definida para cada registro de profesional es la siguiente:

```
idProfesional;nombre;apellido;dni;telefono;matricula;especialidad;emailInstitucional
```

Para administrar este archivo se implementó la clase GestorProfesionalesTexto, también ubicada en el paquete controlador. Los principales métodos son: 

```
- guardarProfesionales(List<Profesional> profesionales)
- agregarProfesional(Profesional profesional)
- leerProfesionales()
```

En ambos casos, las clases Paciente y Profesional sobreescriben el método toString() para generar una representación en cadena de caracteres compatible con la estructura del archivo. Además, se agregaron métodos estáticos fromString(String linea) que permiten interpretar cada línea leída y convertirla nuevamente en un objeto del sistema.
Las operaciones de lectura y escritura utilizan BufferedReader, BufferedWriter, FileReader y FileWriter, aplicando manejo de excepciones mediante try-with-resources, según lo observado en el contenido de la unidad de estudio.

### Diseño de estructura XML para persistencia de una clase

La persistencia bajo formato XML se eligió para guardar objetos de la clase Agenda. El archivo generado se denomina agenda.xml.y utiliza una etiqueta raíz <pacientes>, que contiene uno o más elementos <agenda>, donde cada agenda posee etiquetas internas que representan sus atributos principales: 'idAgenda', 'diaSemana', 'horaInicio', 'horaFin' y 'estado', además de la relación con 'idProfesional'.

Por ejemplo, la secuencia concreta de los objetos testeados es la siguiente:

```xml
<agendas>
    <agenda>
        <idAgenda>1</idAgenda>
        <idProfesional>1</idProfesional>
        <diaSemana>Lunes</diaSemana>
        <horaInicio>08:00</horaInicio>
        <horaFin>12:00</horaFin>
        <estado>Activa</estado>
    </agenda>
</agendas>
```

Para implementar esta funcionalidad se creó la clase GestorAgendasXML, ubicada en el paquete controlador. Esta clase permite guardar una lista de agendas en formato XML y recuperar posteriormente los datos almacenados. En el diseño utilizan las clases de JAXP y DOM, tales como DocumentBuilderFactory, DocumentBuilder, Document, Element, NodeList, TransformerFactory y Transformer.

+ [GestorPacientesXML](../01_Proyecto/beloqui_gonzalo/02_controlador/GestorPacientesXML.java)

