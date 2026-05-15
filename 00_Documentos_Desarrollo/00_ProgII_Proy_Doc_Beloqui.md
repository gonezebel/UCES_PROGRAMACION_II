
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

+ [I.Act1_Clases principales del proyecto_20260328](../00_Documentos_Desarrollo/01_Actividad_ClasesPrincipales.md)

+ [II:Act2_Técnicas de entrada/salida y manejo de archivos_20260421](../00_Documentos_Desarrollo/02_Actividad_Archivos.md)

+ [Parcial Nro.1](../00_Documentos_Desarrollo/03_Primer_Parcial.md)

+ [III:Act3_TAD_20260421](../00_Documentos_Desarrollo/04_Actividad_TAD.md)

