![Logo UCES](../02_imagenes/logo_uces.svg)


**Carrera:** Tecnicatura Universitaria en Programación

**Asignatura:** Programación II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui

**Fecha de engrega:** 2026/04/24

## Primer exámen Parcial : Análisis y propuesta de mejoras del sistema de turnos

### I. Introducción

El desarrollo actual corresponde a un sistema de turnos médicos implementado en Java, organizado en paquetes de "modelo", "controlador", "vista" y "principal", y bajo el paradigma de la programación orientada a objetos, ya que utiliza clases para representar entidades del dominio, encapsulamiento de atributos, herencia en la clase "Persona", una interfaz ("Notificable") y persistencia en archivos de texto y XML mediante clases gestoras.

En línea con los criterios mencionados, se realizan las siguientes propuestas de mejora: 

### II. Análisis del desarrollo actual y propuesta de agregar, quitar o modificar clases

El sistema cuenta con las siguientes clases principales:
```
I.   "Persona" como clase abstracta base.
II.  "Paciente" y "Profesional" como especializaciones de “Persona”.
III. "Agenda" para representar la disponibilidad horaria de un profesional.
IV.  "Turno" para representar una reserva concreta entre paciente y profesional.
V.   "Notificacion" y "Notificable" para modelar el envío de avisos.
VI.  "GestorPacientesTexto", "GestorProfesionalesTexto" y "GestorAgendasXML" para persistencia.
VII.  "Principal" como punto de entrada y clase de interacción por consola.
```
Se propone agregar una clase para registrar efetcivamente la asignación y modificación de turnos concretos, dado que la clase "Turno" ya existe en el proyecto, pero todavía no forma parte del flujo principal del sistema.

 #### II.I Clases a agregar

II.I.I Se propone agregar una clase controladora llamada GestorTurnosXML, optando por la persistencia en formato XML en lugar de texto plano, ya que Turno es una entidad con una estructura más compleja y con asociaciones directas con otras clases del modelo, como Paciente, Profesional y Agenda. Este formato permite conservar una estructura jerárquica más clara y facilita la identificación de cada dato almacenado.

II.I.II Se propone reducir la responsabilidad de la clase Principal, separando de ella la lógica de interacción por consola para ubicarla en una clase del paquete vista, de modo de lograr una mejor organización del sistema según el patrón vista-controlador trabajado en la materia.

#### II.II Clases a modificar

Se propone modificar las siguientes clases:
```
I.  "Paciente" y "Profesional": para reforzar validaciones de datos de entrada y mantener uniformidad con la persistencia.
II. "Agenda": para incorporar validaciones vinculadas a disponibilidad y consistencia horaria
III.  "Turno": para integrarla al funcionamiento real del sistema y completar la lógica del dominio.
IV.   "Notificacion": para vincularla con eventos del sistema, por ejemplo la confirmación o anulación de turnos.
V.  "Principal": para reducir su nivel de responsabilidad y delegar tareas específicas..
```

 
