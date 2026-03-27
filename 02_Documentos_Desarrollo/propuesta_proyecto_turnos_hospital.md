# UNIVERSIDAD DE CIENCIAS EMPRESARIALES Y SOCIALES

## Portada

**Carrera:** Tecnicatura Univestiaria en Programación

**Asignatura:** Programación II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui

**Nombre de la actividad:** Unidad 1 - Actividad 1

**Fecha de entrega:** 28/03/2026

## PROYECTO: Sistema de turnos de hospital

## Fundamentacion

El proyecto consiste en desarrollar un sistema de gestion de turnos para un hospital o centro de salud. El sistema permitira registrar pacientes y profesionales, crear agendas de atencion, asignar y anular turnos, y enviar recordatorios de consultas. El tema surge del material trabajado en la carpeta `00_Boceto_POO`, donde se definieron como funciones principales el registro de pacientes y profesionales, la asignacion de turnos, la anulacion de turnos y el envio de recordatorios.

La propuesta es pertinente y factible de implementar en Java, porque permite aplicar correctamente encapsulamiento, herencia, polimorfismo, interfaces, constructores, getters, setters y metodos de negocio en clases representativas del dominio.

## Lineamientos del proyecto

- Nombre del proyecto: `apellido_nombre`
- Paquete para guardar las clases de los objetos: `com.apellido.modelo`
- Paquete para guardar el archivo principal: `com.apellido.main`
- Nombres de las clases: representativos del contenido de cada clase
- Nombre del archivo principal: `Principal.java`

## Clases principales del proyecto

### 1. Clase abstracta `Persona`

**Atributos**

- `String nombre`
- `String apellido`
- `String dni`
- `String telefono`

**Constructores**

- `Persona()`
- `Persona(String nombre, String apellido, String dni, String telefono)`

**Metodos getters y setters**

- `getNombre()` / `setNombre(String nombre)`
- `getApellido()` / `setApellido(String apellido)`
- `getDni()` / `setDni(String dni)`
- `getTelefono()` / `setTelefono(String telefono)`

**Metodos necesarios**

- `String getNombreCompleto()`
- `boolean validarDni()`
- `String mostrarDatos()`

### 2. Clase `Paciente`

Hereda de `Persona` e implementa la interfaz `Notificable`.

**Atributos**

- `int numeroHistoriaClinica`
- `String obraSocial`
- `String email`

**Constructores**

- `Paciente()`
- `Paciente(String nombre, String apellido, String dni, String telefono, int numeroHistoriaClinica, String obraSocial, String email)`

**Metodos getters y setters**

- `getNumeroHistoriaClinica()` / `setNumeroHistoriaClinica(int numeroHistoriaClinica)`
- `getObraSocial()` / `setObraSocial(String obraSocial)`
- `getEmail()` / `setEmail(String email)`

**Metodos necesarios**

- `boolean validarEmail()`
- `String obtenerDestinoNotificacion()`
- `String enviarNotificacion(String mensaje)`
- `String mostrarDatos()`
- `String toString()`

### 3. Clase `Profesional`

Hereda de `Persona` e implementa la interfaz `Notificable`.

**Atributos**

- `String matricula`
- `String especialidad`
- `String emailInstitucional`

**Constructores**

- `Profesional()`
- `Profesional(String nombre, String apellido, String dni, String telefono, String matricula, String especialidad, String emailInstitucional)`

**Metodos getters y setters**

- `getMatricula()` / `setMatricula(String matricula)`
- `getEspecialidad()` / `setEspecialidad(String especialidad)`
- `getEmailInstitucional()` / `setEmailInstitucional(String emailInstitucional)`

**Metodos necesarios**

- `boolean estaDisponible(String estadoAgenda)`
- `String obtenerDestinoNotificacion()`
- `String enviarNotificacion(String mensaje)`
- `String mostrarDatos()`
- `String toString()`

### 4. Clase `Agenda`

**Atributos**

- `int idAgenda`
- `Profesional profesional`
- `String diaSemana`
- `String horaInicio`
- `String horaFin`
- `String estado`

**Constructores**

- `Agenda()`
- `Agenda(int idAgenda, Profesional profesional, String diaSemana, String horaInicio, String horaFin, String estado)`

**Metodos getters y setters**

- `getIdAgenda()` / `setIdAgenda(int idAgenda)`
- `getProfesional()` / `setProfesional(Profesional profesional)`
- `getDiaSemana()` / `setDiaSemana(String diaSemana)`
- `getHoraInicio()` / `setHoraInicio(String horaInicio)`
- `getHoraFin()` / `setHoraFin(String horaFin)`
- `getEstado()` / `setEstado(String estado)`

**Metodos necesarios**

- `void suspenderAgenda()`
- `void activarAgenda()`
- `boolean estaActiva()`
- `String toString()`

### 5. Clase `Turno`

**Atributos**

- `int idTurno`
- `Paciente paciente`
- `Profesional profesional`
- `Agenda agenda`
- `String fecha`
- `String hora`
- `String estado`

**Constructores**

- `Turno()`
- `Turno(int idTurno, Paciente paciente, Profesional profesional, Agenda agenda, String fecha, String hora, String estado)`

**Metodos getters y setters**

- `getIdTurno()` / `setIdTurno(int idTurno)`
- `getPaciente()` / `setPaciente(Paciente paciente)`
- `getProfesional()` / `setProfesional(Profesional profesional)`
- `getAgenda()` / `setAgenda(Agenda agenda)`
- `getFecha()` / `setFecha(String fecha)`
- `getHora()` / `setHora(String hora)`
- `getEstado()` / `setEstado(String estado)`

**Metodos necesarios**

- `boolean asignarTurno()`
- `void anularTurno()`
- `String mostrarResumenTurno()`
- `String toString()`

### 6. Clase `Notificacion`

**Atributos**

- `int idNotificacion`
- `String tipo`
- `String mensaje`
- `String fechaEnvio`
- `boolean enviada`

**Constructores**

- `Notificacion()`
- `Notificacion(int idNotificacion, String tipo, String mensaje, String fechaEnvio, boolean enviada)`

**Metodos getters y setters**

- `getIdNotificacion()` / `setIdNotificacion(int idNotificacion)`
- `getTipo()` / `setTipo(String tipo)`
- `getMensaje()` / `setMensaje(String mensaje)`
- `getFechaEnvio()` / `setFechaEnvio(String fechaEnvio)`
- `isEnviada()` / `setEnviada(boolean enviada)`

**Metodos necesarios**

- `String enviarA(Notificable destinatario)`
- `String mostrarDetalle()`
- `String toString()`

## Interfaz implementada

Se implementa la interfaz `Notificable` en las clases `Paciente` y `Profesional`.

**Metodos de la interfaz**

- `String obtenerDestinoNotificacion()`
- `String enviarNotificacion(String mensaje)`

La interfaz es utilizada por la clase `Notificacion`, que puede enviar mensajes a cualquier objeto que implemente `Notificable`.

## Metodos sobreescritos

En el proyecto se sobreescriben varios metodos heredados, cumpliendo y superando el minimo requerido:

- `mostrarDatos()` en `Paciente`
- `mostrarDatos()` en `Profesional`
- `toString()` en `Paciente`
- `toString()` en `Profesional`

## Entrega

El documento en PDF debera ir acompañado por un archivo comprimido `.zip` o `.rar` con una copia completa del proyecto en Java. La carpeta a comprimir sera `apellido_nombre`, respetando los paquetes `com.apellido.modelo` y `com.apellido.main`, y el archivo principal `Principal.java`.
