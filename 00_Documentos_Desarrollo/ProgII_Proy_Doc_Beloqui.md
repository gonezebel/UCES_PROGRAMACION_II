
![Logo UCES](../02_imagenes/logo_uces.svg)






**Carrera:** Tecnicatura Universitaria en Programación

**Asignatura:** Programación II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui

**Nombre de la actividad:** Unidad 1 - Actividad 1

**Fecha de entrega:** 28/03/2026




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

```text
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
\- 03_vista
```

## Activdad Número 1-  Clases principales del proyecto

### 1. Clase abstracta Persona

+ [ClasePersona](../01_Proyecto/beloqui_gonzalo/01_modelo/Persona.java)

**Atributos**

- String nombre
- String apellido
- String dni
- String telefono

**Constructores**

- Persona()
- Persona(String nombre, String apellido, String dni, String telefono)

**Métodos getters y setters**

- getNombre() / setNombre(String nombre)
- getApellido() / setApellido(String apellido)
- getDni() / setDni(String dni)
- getTelefono() / setTelefono(String telefono)

**Métodos necesarios**

- String getNombreCompleto()
- boolean validarDni()
- String mostrarDatos()

### 2. Clase Paciente

+ [ClasePaciente](../01_Proyecto/beloqui_gonzalo/01_modelo/Paciente.java)

Hereda de Persona e implementa la interfaz Notificable.

**Atributos**

- int numeroHistoriaClinica
- String obraSocial
- String email

**Constructores**

- Paciente()
- Paciente(String nombre, String apellido, String dni, String telefono, int numeroHistoriaClinica, String obraSocial, String email)

**Métodos getters y setters**

- getNumeroHistoriaClinica() / setNumeroHistoriaClinica(int numeroHistoriaClinica)
- getObraSocial() / setObraSocial(String obraSocial)
- getEmail() / setEmail(String email)

**Métodos necesarios**

- boolean validarEmail()
- String obtenerDestinoNotificacion()
- String enviarNotificacion(String mensaje)
- String mostrarDatos()
- String toString()

### 3. Clase Profesional

+ [ClaseProfesional](../01_Proyecto/beloqui_gonzalo/01_modelo/Profesional.java)

Hereda de Persona e implementa la interfaz Notificable.

**Atributos**

- String matricula
- String especialidad
- String emailInstitucional

**Constructores**

- Profesional()
- Profesional(String nombre, String apellido, String dni, String telefono, String matricula, String especialidad, String emailInstitucional)

**Métodos getters y setters**

- getMatricula() / setMatricula(String matricula)
- getEspecialidad() / setEspecialidad(String especialidad)
- getEmailInstitucional() / setEmailInstitucional(String emailInstitucional)

**Métodos necesarios**

- boolean estaDisponible(String estadoAgenda)
- String obtenerDestinoNotificacion()
- String enviarNotificacion(String mensaje)
- String mostrarDatos()
- String toString()

### 4. Clase Agenda

+ [ClaseAgenda](../01_Proyecto/beloqui_gonzalo/01_modelo/Agenda.java)

**Atributos**

- int idAgenda
- Profesional profesional
- String diaSemana
- String horaInicio
- String horaFin
- String estado

**Constructores**

- Agenda()
- Agenda(int idAgenda, Profesional profesional, String diaSemana, String horaInicio, String horaFin, String estado)

**Métodos getters y setters**

- getIdAgenda() / setIdAgenda(int idAgenda)
- getProfesional() / setProfesional(Profesional profesional)
- getDiaSemana() / setDiaSemana(String diaSemana)
- getHoraInicio() / setHoraInicio(String horaInicio)
- getHoraFin() / setHoraFin(String horaFin)
- getEstado() / setEstado(String estado)

**Métodos necesarios**

- void suspenderAgenda()
- void activarAgenda()
- boolean estaActiva()
- String toString()

### 5. Clase Turno

+ [ClaseTurno](../01_Proyecto/beloqui_gonzalo/01_modelo/Turno.java)

**Atributos**

- int idTurno
- Paciente paciente
- Profesional profesional
- Agenda agenda
- String fecha
- String hora
- String estado

**Constructores**

- Turno()
- Turno(int idTurno, Paciente paciente, Profesional profesional, Agenda agenda, String fecha, String hora, String estado)

**Métodos getters y setters**

- getIdTurno() / setIdTurno(int idTurno)
- getPaciente() / setPaciente(Paciente paciente)
- getProfesional() / setProfesional(Profesional profesional)
- getAgenda() / setAgenda(Agenda agenda)
- getFecha() / setFecha(String fecha)
- getHora() / setHora(String hora)
- getEstado() / setEstado(String estado)

**Métodos necesarios**

- boolean asignarTurno()
- void anularTurno()
- String mostrarResumenTurno()
- String toString()

### 6. Clase Notificacion

+ [ClaseNotificacion](../01_Proyecto/beloqui_gonzalo/01_modelo/Notificacion.java)

**Atributos**

- int idNotificacion
- String tipo
- String mensaje
- String fechaEnvio
- boolean enviada

**Constructores**

- Notificacion()
- Notificacion(int idNotificacion, String tipo, String mensaje, String fechaEnvio, boolean enviada)

**Métodos getters y setters**

- getIdNotificacion() / setIdNotificacion(int idNotificacion)
- getTipo() / setTipo(String tipo)
- getMensaje() / setMensaje(String mensaje)
- getFechaEnvio() / setFechaEnvio(String fechaEnvio)
- isEnviada() / setEnviada(boolean enviada)

**Métodos necesarios**

- String enviarA(Notificable destinatario)
- String mostrarDetalle()
- String toString()

## Interfaz implementada

Se implementa la interfaz Notificable en las clases Paciente y Profesional.

+ [InterfazNotificable](../01_Proyecto/beloqui_gonzalo/01_modelo/Notificable.java)

**Métodos de la interfaz**

- String obtenerDestinoNotificacion()
- String enviarNotificacion(String mensaje)

La interfaz es utilizada por la clase Notificacion, que puede enviar mensajes a cualquier objeto que implemente Notificable.

## Métodos sobreescritos

En el proyecto se sobreescriben varios métodos heredados:

- mostrarDatos() en Paciente
- mostrarDatos() en Profesional
- toString() en Paciente
- toString() en Profesional

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
