![Logo UCES](../02_imagenes/logo_uces.svg)


**Carrera:** Tecnicatura Universitaria en Programacion

**Asignatura:** Programacion II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui

**Fecha de entrega:** 2026/05/15


## Actividad Numero 4 - TAD

### Analisis de clases del sistema

El sistema de turnos medicos se encuentra organizado alrededor de clases del modelo como Paciente, Profesional, Agenda, Turno y Especialidad, junto con clases gestoras para persistencia en archivos de texto y XML. Estas clases se utilizan desde Principal para cargar datos, buscar entidades, validar reglas de negocio y confirmar operaciones de agenda y turnos.

Hasta esta actividad, varias operaciones se resolvian recorriendo listas completas mediante ciclos for. Ese criterio es correcto para colecciones chicas, pero en el sistema aparecen busquedas repetidas por claves concretas: DNI de paciente, DNI de profesional, matricula, email institucional, identificador de agenda, identificador de turno y nombre de especialidad. Tambien aparece la necesidad de saber rapidamente si un horario ya esta ocupado dentro de una agenda para una fecha determinada.

Por ese motivo se incorporaron dos tipos abstractos de datos diferentes provistos por Java:

```
- Map, implementado con HashMap
- Set, implementado con HashSet
```

La implementacion se realizo en la clase Principal, donde se concentra el flujo de interaccion por consola.

+ [Clase Principal](../01_Proyecto/beloqui_gonzalo/00_principal/Principal.java)

### 1. TAD Map - HashMap

Se eligio el TAD Map porque permite asociar una clave con un valor. En este sistema las entidades principales tienen claves naturales o identificadores que se usan constantemente para buscarlas:

```
- DNI -> Paciente
- DNI -> Profesional
- ID -> Profesional
- Matricula -> Profesional
- Email institucional -> Profesional
- ID -> Agenda
- ID -> Turno
- Nombre -> Especialidad
```

La clase concreta utilizada fue HashMap, porque no se necesita mantener un orden especifico de las claves. Lo importante es acceder rapidamente al objeto asociado a una clave determinada.

Los metodos incorporados son:

```
- indexarPacientesPorDni(List<Paciente> pacientes)
- indexarProfesionalesPorDni(List<Profesional> profesionales)
- indexarProfesionalesPorId(List<Profesional> profesionales)
- indexarProfesionalesPorMatricula(List<Profesional> profesionales)
- indexarProfesionalesPorEmail(List<Profesional> profesionales)
- indexarAgendasPorId(List<Agenda> agendas)
- indexarTurnosPorId(List<Turno> turnos)
- indexarEspecialidadesPorNombre(List<Especialidad> especialidades)
```

Estos metodos construyen indices temporales a partir de las listas cargadas desde archivos. Luego los metodos de busqueda reutilizan esos indices:

```
- buscarPacientePorDni(List<Paciente> pacientes, String dni)
- buscarProfesionalPorDni(List<Profesional> profesionales, String dni)
- buscarProfesionalPorId(List<Profesional> profesionales, int idProfesional)
- buscarProfesionalPorMatricula(List<Profesional> profesionales, String matricula)
- buscarProfesionalPorEmailInstitucional(List<Profesional> profesionales, String emailInstitucional)
- buscarAgendaPorId(List<Agenda> agendas, int idAgenda)
- buscarTurnoPorId(List<Turno> turnos, int idTurno)
- buscarEspecialidadPorNombre(List<Especialidad> especialidades, String nombre)
```

La eleccion mejora la claridad del codigo porque la intencion queda expresada como una busqueda por clave, y no como un recorrido manual de todos los elementos.

### 2. TAD Set - HashSet

Se eligio el TAD Set porque representa una coleccion de elementos sin duplicados. En el sistema hay dos situaciones donde esta propiedad resulta adecuada:

```
- Controlar los horarios ya ocupados de una agenda en una fecha.
- Verificar si una especialidad ya existe antes de agregarla.
```

La clase concreta utilizada fue HashSet, porque no se necesita mostrar los elementos ordenados. En ambos casos solo interesa saber si un elemento pertenece o no al conjunto.

Los metodos incorporados son:

```
- obtenerHorariosOcupados(List<Turno> turnos, int idAgenda, String fecha)
- existeEspecialidad(List<Especialidad> especialidades, String nombre)
```

El metodo obtenerHorariosOcupados construye un conjunto con las horas ocupadas de una agenda para una fecha determinada. Luego obtenerHorariosDisponibles consulta ese conjunto con contains para decidir que horarios puede ofrecer al usuario.

El metodo existeEspecialidad arma un conjunto con los nombres normalizados de las especialidades ya cargadas. De esta forma se evita cargar dos veces la misma especialidad aunque el usuario cambie mayusculas, minusculas o espacios.

### Justificacion general

La incorporacion de Map y Set resulta coherente con el dominio del sistema:

```
- Map se usa cuando el problema necesita localizar objetos por una clave.
- Set se usa cuando el problema necesita controlar pertenencia y evitar repetidos.
```

Ambos TAD se implementaron usando clases provistas por Java dentro del paquete java.util, siguiendo el estilo de los ejemplos teoricos de la unidad donde se trabajan colecciones como HashSet, TreeMap, ArrayList y operaciones de busqueda sobre colecciones.
