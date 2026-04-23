![Logo UCES](../02_imagenes/logo_uces.svg)


**Carrera:** Tecnicatura Universitaria en Programación

**Asignatura:** Programación II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui

**Fecha de engrega:** 2026/04/24

## Primer exámen Parcial : Análisis y propuesta de mejoras del sistema de turnos

### Introducción

El desarrollo actual corresponde a un sistema de turnos médicos implementado en Java, organizado en paquetes de "modelo", "controlador", "vista" y "principal", y bajo el paradigma de la programación orientada a objetos, ya que utiliza clases para representar entidades del dominio, encapsulamiento de atributos, herencia en la clase "Persona", una interfaz ("Notificable") y persistencia en archivos de texto y XML mediante clases gestoras.

En línea con los criterios mencionados, se realizan las siguientes propuestas de mejora: 

### I. Análisis del desarrollo actual y propuesta de agregar, quitar o modificar clases

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




