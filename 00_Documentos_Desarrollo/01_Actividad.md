![Logo UCES](../02_imagenes/logo_uces.svg)


**Carrera:** Tecnicatura Universitaria en Programación

**Asignatura:** Programación II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui

**Fecha de engrega:** 2026/03/28


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
