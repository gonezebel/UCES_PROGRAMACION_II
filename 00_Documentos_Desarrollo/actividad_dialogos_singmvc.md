![Logo UCES](../02_imagenes/logo_uces.svg)

**Carrera:** Tecnicatura Universitaria en Programación

**Asignatura:** Programación II

**Nombre del/a docente:** Mario Daniel Detke

**Nombre del/a estudiante:** Gonzalo Ezequiel Beloqui

**Fecha de entrega:** 2026/06/25


## Unidad 4 - Actividad 2

### Introducción

En esta actividad se amplió el sistema de turnos médicos desarrollado en la
actividad anterior, incorporando componentes avanzados de Swing y reforzando la
separación por patrón Modelo-Vista-Controlador.

El proyecto quedó agrupado en cuatro paquetes principales:

```
- modelo
- controlador
- vista
- principal
```

La clase de inicio de la aplicación gráfica se encuentra en
[PrincipalApp](../01_Proyecto/beloqui_gonzalo/principal/PrincipalApp.java) y
delega la ejecución en la ventana Swing ubicada en
[vista/PrincipalApp](../01_Proyecto/beloqui_gonzalo/vista/PrincipalApp.java).

### Aplicación del patrón MVC

**Modelo**

El paquete [modelo](../01_Proyecto/beloqui_gonzalo/modelo) contiene las clases
propias del dominio: pacientes, profesionales, especialidades, agendas, turnos,
notificaciones y personas. Estas clases concentran datos y reglas propias de
cada entidad, como validación de DNI, teléfono, fecha de nacimiento, matrícula,
vigencia de agenda y restricciones de turnos.

**Vista**

El paquete [vista](../01_Proyecto/beloqui_gonzalo/vista) contiene la interfaz
gráfica de Swing y la vista de consola. La clase `PrincipalApp` arma la ventana,
los formularios, tablas, listas, menús, botones y cuadros de diálogo. Su
responsabilidad es mostrar información, capturar datos del usuario y delegar la
operación al controlador.

También se agregó `LoginDialog` como pantalla inicial de acceso al sistema.

**Controlador**

El paquete [controlador](../01_Proyecto/beloqui_gonzalo/controlador) contiene
`SistemaTurnos` y los gestores de persistencia. `SistemaTurnos` concentra las
operaciones del sistema: alta de pacientes, alta de profesionales, gestión de
especialidades, creación de agendas, asignación de turnos, anulación de turnos,
búsquedas y persistencia.

También se incorporó `Autenticador`, que valida las credenciales de ingreso sin
mezclar esa regla con la construcción visual de Swing.

**Principal**

El paquete [principal](../01_Proyecto/beloqui_gonzalo/principal) contiene los
puntos de entrada del sistema. De esta forma, el arranque de la aplicación no
queda mezclado con la construcción visual ni con la lógica de negocio.

### Componentes Swing incorporados

La interfaz incorpora cajas de diálogo, casillas de verificación, botones de
opción y listas para resolver operaciones concretas del sistema.

**Pantalla de login**

Antes de mostrar la ventana principal, la aplicación solicita usuario y clave.
La validación se realiza mediante el controlador `Autenticador`. Se configuraron
las siguientes credenciales locales:

```
- Usuario: admin
- Clave: admin123
```

![Cuadro de diálogo de login](../02_imagenes/ff_cuadro_diálogo_login.jpg)

**Cajas de diálogo**

Se utiliza `JOptionPane` para informar operaciones exitosas, mostrar errores de
validación y confirmar acciones sensibles, como la anulación de un turno o la
salida del sistema. Esto evita que el usuario administrativo dependa de mensajes
por consola.

![Confirmación de anulación de turno](../02_imagenes/gg_cuadro_confirmación_anulación_turno.jpg)

**Casillas de verificación**

Se utiliza `JCheckBox` en la consulta de agendas con la opción "Mostrar solo
agendas activas". Esta casilla permite cambiar el filtro visual sin modificar
los datos persistidos.

![Checkbox de agendas activas](../02_imagenes/hh_checkbox_agendas_activas.jpg)

**Botones de opción**

Se utilizan `JRadioButton` y `ButtonGroup` en el alta de pacientes para elegir
el sexo. Esta decisión evita cargar textos libres y permite aplicar
correctamente las reglas de especialidades, por ejemplo Ginecología y Pediatría.

![Opciones masculino femenino](../02_imagenes/ii_opciones_masc_fem.jpg)

**Listas**

Se utiliza `JList` en la asignación de turnos para mostrar los horarios
disponibles de una agenda en una fecha determinada. El empleado selecciona un
horario libre y luego confirma la asignación.

![Lista de horarios](../02_imagenes/jj_listas_horarios.jpg)

### Carga y guardado de datos

La aplicación carga los datos desde los archivos ubicados en
[04_datos](../01_Proyecto/beloqui_gonzalo/04_datos) al iniciar y también
permite recargarlos desde el menú y la barra de herramientas.

Las acciones principales de registro y modificación guardan automáticamente los
datos en los archivos del proyecto. Esto evita que el usuario administrativo
olvide persistir una operación confirmada.

También se conserva una opción explícita **Guardar datos** en la interfaz. Esta
opción delega en `SistemaTurnos.guardarDatos()` y persiste el estado completo
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

La interfaz también detecta cuando existen formularios con datos cargados que
todavía no fueron registrados. Si el usuario intenta cerrar el sistema o
recargar datos en esa situación, se muestra un cuadro de diálogo advirtiendo que
esos datos escritos en pantalla se perderán si continúa.

![Confirmación de guardado](../02_imagenes/kk_confirmación_de_guardado.jpg)

