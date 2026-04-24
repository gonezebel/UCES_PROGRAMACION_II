![Logo UCES](../02_imagenes/logo_uces.svg)


**Carrera:** Tecnicatura Universitaria en Programación

**Asignatura:** Programación II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui

**Fecha de engrega:** 2026/04/24
<br><br>

## Primer exámen parcial: Propuesta de mejoras del sistema de turnos

### I. Introducción

El desarrollo corresponde a un sistema de turnos médicos en Java, organizado en paquetes de modelo, controlador y principal, con la propuesta de incorporar una clase en el paquete vista para separar la interacción por consola, y desarrollado bajo el paradigma de la programación orientada a objetos, ya que utiliza clases para representar entidades, encapsulamiento de atributos, herencia en la clase Persona, una interfaz (Notificable) y persistencia en archivos de texto y XML mediante clases gestoras.

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

II.I.I Se propone agregar una clase controladora llamada GestorTurnosXML, optando por la persistencia en formato XML en lugar de texto plano, ya que Turno es una entidad con una estructura más compleja y con asociaciones directas con otras clases del modelo, como Paciente, Profesional y Agenda. Este formato permite conservar una estructura jerárquica más clara y facilita la identificación de cada dato almacenado. La implementación realizada se basa en la siguiente estructura, con un atributo para el nombre del archivo y métodos específicos para guardar y leer turnos:
```
public class GestorTurnosXML {
    private final String nombreArchivo;
    public GestorTurnosXML(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    public void guardarTurnos(List<Turno> turnos) {
        // lógica de persistencia en XML
    }
    public List<Turno> leerTurnos() {
        return new ArrayList<>();
    }
}
```

II.I.II Se propone reducir la responsabilidad de la clase Principal, separando de ella la lógica de interacción por consola para ubicarla en una clase del paquete vista, de modo de lograr una mejor organización del sistema según el patrón vista-controlador trabajado en la materia. Esta organización permite distribuir responsabilidades de manera más clara entre las clases, favorece el mantenimiento del código y facilita futuras ampliaciones del sistema sin concentrar toda la lógica en la clase principal. La clase propuesta tendría como responsabilidad principal mostrar menús, solicitar datos al usuario y devolver los valores ingresados:
```
public class VistaConsola {
    private final Scanner scanner;
    public VistaConsola(Scanner scanner) {
        this.scanner = scanner;
    }
    public void mostrarMenu() {
        System.out.println("1. Agregar paciente");
        System.out.println("2. Agregar profesional");
        System.out.println("3. Agregar agenda");
    }
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
```
<br><br><br><br>
#### II.II Clases a modificar

I.  Paciente y Profesional: para reforzar validaciones de datos de entrada y mantener uniformidad con la persistencia. En particular, se propone validar que nombre y apellido no estén vacíos, que el DNI contenga solo números y una longitud válida, y que el correo electrónico tenga un formato básico correcto. 

```
public boolean validarNombreApellido() {
    return !getNombre().isEmpty() && !getApellido().isEmpty();
}
public boolean validarDni() {
    return getDni().matches("\\d{7,8}");
}
public boolean validarEmail() {
    return getEmail().contains("@") && getEmail().contains(".");
}
public boolean validarDatos() {
    return validarNombreApellido() && validarDni() && validarEmail();
}
```

II. Agenda: para incorporar validaciones vinculadas a disponibilidad y consistencia horaria. En este punto, se propone controlar que el día ingresado sea válido, que la hora de inicio sea anterior a la hora de fin y que no existan superposiciones horarias para un mismo profesional y día.

```
public boolean validarDiaSemana() {
    return this.diaSemana.equalsIgnoreCase("Lunes")
            || this.diaSemana.equalsIgnoreCase("Martes")
            || this.diaSemana.equalsIgnoreCase("Miercoles")
            || this.diaSemana.equalsIgnoreCase("Jueves")
            || this.diaSemana.equalsIgnoreCase("Viernes")
            || this.diaSemana.equalsIgnoreCase("Sabado");
}
public boolean validarHorario() {
    return this.horaInicio.compareTo(this.horaFin) < 0;
}
public boolean seSuperponeCon(Agenda otraAgenda) {
    if (otraAgenda == null || this.profesional == null || otraAgenda.getProfesional() == null) {
        return false;
    }
    return this.profesional.getIdProfesional() == otraAgenda.getProfesional().getIdProfesional()
            && this.diaSemana.equalsIgnoreCase(otraAgenda.getDiaSemana())
            && this.horaInicio.compareTo(otraAgenda.getHoraFin()) < 0
            && this.horaFin.compareTo(otraAgenda.getHoraInicio()) > 0;
}
```

III.  Turno: para integrarla al funcionamiento real del sistema y completar la lógica del dominio. También se propone validar que solo pueda asignarse un turno cuando exista una agenda activa y cuando los datos del paciente, profesional y horario sean consistentes.

```
public boolean validarDatosTurno() {
    return this.paciente != null
            && this.profesional != null
            && this.agenda != null
            && this.fecha != null && !this.fecha.trim().isEmpty()
            && this.hora != null && !this.hora.trim().isEmpty();
}
public boolean puedeAsignarse() {
    return validarDatosTurno() && this.agenda.estaActiva();
}
public boolean asignarTurno() {
    if (puedeAsignarse()) {
        this.estado = "Asignado";
        return true;
    }
    return false;
}
```

IV. Notificacion: para vincularla con eventos concretos del sistema, por ejemplo la confirmación o anulación de turnos, de modo que esta clase deje de formar parte solamente del modelo teórico e intervenga en el flujo real del sistema.

```
public void prepararConfirmacionTurno(Turno turno) {
    this.tipo = "Confirmacion";
    this.mensaje = "Turno confirmado para " + turno.getFecha() + " a las " + turno.getHora();
    this.enviada = false;
}
public void prepararAnulacionTurno(Turno turno) {
    this.tipo = "Anulacion";
    this.mensaje = "Turno anulado para " + turno.getFecha() + " a las " + turno.getHora();
    this.enviada = false;
}
```

V. Principal: para reducir su nivel de responsabilidad y delegar tareas específicas. En esta clase se propone centralizar la búsqueda de pacientes y profesionales en métodos específicos y encapsular en una sola operación el cálculo del próximo identificador disponible, manteniendo una lógica más clara y reutilizable.

```
private static Paciente buscarPacientePorDni(List<Paciente> pacientes, String dni) {
    for (Paciente paciente : pacientes) {
        if (paciente.getDni().equals(dni)) {
            return paciente;
        }
    }
    return null;
}
private static Profesional buscarProfesionalPorDni(List<Profesional> profesionales, String dni) {
    for (Profesional profesional : profesionales) {
        if (profesional.getDni().equals(dni)) {
            return profesional;
        }
    }
    return null;
}
private static int obtenerSiguienteIdAgenda(List<Agenda> agendas) {
    int mayorId = 0;
    for (Agenda agenda : agendas) {
        if (agenda.getIdAgenda() > mayorId) {
            mayorId = agenda.getIdAgenda();
        }
    }
    return mayorId + 1;
}
```


#### II.III Clases a eliminar

No se considera necesario eliminar clases del modelo actual, dado que las existentes responden correctamente al dominio planteado

### III. Análisis de métodos en base a recursividad e iteración

El proyecto actual resuelve las búsquedas y recorridos de listas mediante estructuras iterativas, por ejemplo en los métodos de búsqueda de pacientes, profesionales y agendas, así como en la obtención del siguiente identificador de agenda. Si bien técnicamente todos los métodos podrían reescribirse de forma recursiva, no se considera que esa decisión aporte una mejora real en este caso. La recursividad resulta más adecuada cuando el problema presenta una estructura naturalmente recursiva, como árboles, jerarquías o subdivisión del problema en partes equivalentes. En este sistema, los datos se almacenan en listas lineales obtenidas desde archivos, por lo que el recorrido secuencial es más simple, más legible y más coherente con el nivel de complejidad del proyecto. Asimismo, la iteración permite mantener un control más explícito del recorrido y de las condiciones de corte, lo que resulta conveniente en métodos que dependen de validaciones puntuales y de la lectura de datos persistidos en archivos.


