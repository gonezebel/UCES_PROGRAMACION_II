![Logo UCES](../02_imagenes/logo_uces.svg)

**Carrera:** Tecnicatura Universitaria en Programacion

**Asignatura:** Programacion II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui

**Fecha de entrega:** 2026/06/25


## Unidad 4 - Actividad 2

### Introduccion

En esta actividad se amplio el sistema de turnos medicos desarrollado en la
actividad anterior, incorporando componentes avanzados de Swing y reforzando la
separacion por patron Modelo-Vista-Controlador.

El proyecto quedo agrupado en cuatro paquetes principales:

```
- modelo
- controlador
- vista
- principal
```

La clase de inicio de la aplicacion grafica se encuentra en
[PrincipalApp](../01_Proyecto/beloqui_gonzalo/principal/PrincipalApp.java) y
delega la ejecucion en la ventana Swing ubicada en
[vista/PrincipalApp](../01_Proyecto/beloqui_gonzalo/vista/PrincipalApp.java).

### Aplicacion del patron MVC

**Modelo**

El paquete [modelo](../01_Proyecto/beloqui_gonzalo/modelo) contiene las clases
propias del dominio: pacientes, profesionales, especialidades, agendas, turnos,
notificaciones y personas. Estas clases concentran datos y reglas propias de
cada entidad, como validacion de DNI, telefono, fecha de nacimiento, matricula,
vigencia de agenda y restricciones de turnos.

**Vista**

El paquete [vista](../01_Proyecto/beloqui_gonzalo/vista) contiene la interfaz
grafica de Swing y la vista de consola. La clase `PrincipalApp` arma la ventana,
los formularios, tablas, listas, menus, botones y cuadros de dialogo. Su
responsabilidad es mostrar informacion, capturar datos del usuario y delegar la
operacion al controlador.

Tambien se agrego `LoginDialog` como pantalla inicial de acceso al sistema.

**Controlador**

El paquete [controlador](../01_Proyecto/beloqui_gonzalo/controlador) contiene
`SistemaTurnos` y los gestores de persistencia. `SistemaTurnos` concentra las
operaciones del sistema: alta de pacientes, alta de profesionales, gestion de
especialidades, creacion de agendas, asignacion de turnos, anulacion de turnos,
busquedas y persistencia.

Tambien se incorporo `Autenticador`, que valida las credenciales de ingreso sin
mezclar esa regla con la construccion visual de Swing.

**Principal**

El paquete [principal](../01_Proyecto/beloqui_gonzalo/principal) contiene los
puntos de entrada del sistema. De esta forma, el arranque de la aplicacion no
queda mezclado con la construccion visual ni con la logica de negocio.

### Componentes Swing incorporados

La interfaz incorpora cajas de dialogo, casillas de verificacion, botones de
opcion y listas para resolver operaciones concretas del sistema.

**Pantalla de login**

Antes de mostrar la ventana principal, la aplicacion solicita usuario y clave.
La validacion se realiza mediante el controlador `Autenticador`. Para esta
entrega academica se configuraron credenciales locales:

```
- Usuario: admin
- Clave: admin123
```

**Cajas de dialogo**

Se utiliza `JOptionPane` para informar operaciones exitosas, mostrar errores de
validacion y confirmar acciones sensibles, como la anulacion de un turno o la
salida del sistema. Esto evita que el usuario administrativo dependa de mensajes
por consola.

**Casillas de verificacion**

Se utiliza `JCheckBox` en la consulta de agendas con la opcion "Mostrar solo
agendas activas". Esta casilla permite cambiar el filtro visual sin modificar
los datos persistidos.

**Botones de opcion**

Se utilizan `JRadioButton` y `ButtonGroup` en el alta de pacientes para elegir
el sexo. Esta decision evita cargar textos libres y permite aplicar correctamente
las reglas de especialidades, por ejemplo Ginecologia y Pediatria.

**Listas**

Se utiliza `JList` en la asignacion de turnos para mostrar los horarios
disponibles de una agenda en una fecha determinada. El empleado selecciona un
horario libre y luego confirma la asignacion.

### Carga y guardado de datos

La aplicacion carga los datos desde los archivos ubicados en
[04_datos](../01_Proyecto/beloqui_gonzalo/04_datos) al iniciar y tambien permite
recargarlos desde el menu y la barra de herramientas.

Las acciones principales de registro y modificacion guardan automaticamente los
datos en los archivos del proyecto. Esto evita que el usuario administrativo
olvide persistir una operacion confirmada.

Tambien se conserva una opcion explicita **Guardar datos** en la interfaz. Esta
opcion delega en `SistemaTurnos.guardarDatos()` y persiste el estado completo
actual de:

```
- pacientes.txt
- profesionales.txt
- especialidades.txt
- agendas.xml
- turnos.xml
```

Aunque algunos archivos utilizan formato XML, siguen siendo archivos de texto y
pueden inspeccionarse sin herramientas binarias.

La interfaz tambien detecta cuando existen formularios con datos cargados que
todavia no fueron registrados. Si el usuario intenta cerrar el sistema o
recargar datos en esa situacion, se muestra un cuadro de dialogo advirtiendo que
esos datos escritos en pantalla se perderan si continua.

### Funcionalidades disponibles

La ventana principal permite:

```
- Registrar pacientes.
- Buscar pacientes por DNI.
- Registrar profesionales.
- Buscar profesionales por DNI.
- Registrar especialidades.
- Registrar agendas de atencion.
- Consultar agendas por dia y especialidad.
- Buscar paciente para asignar turno.
- Seleccionar especialidad, profesional, agenda, fecha y horario disponible.
- Asignar turnos.
- Buscar turnos por paciente.
- Anular turnos vigentes.
- Recargar datos desde archivos.
- Guardar datos en archivos.
- Ingresar mediante pantalla de login.
- Advertir formularios incompletos al cerrar o recargar.
```

### Conclusion

La actividad incorpora los componentes avanzados solicitados por la consigna y
mantiene la separacion entre modelo, vista, controlador y principal. La vista
Swing no guarda datos directamente ni decide reglas de negocio: esas tareas se
resuelven mediante el controlador `SistemaTurnos` y los gestores de archivos.
