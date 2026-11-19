# Reuniones y rotación de parejas

**Responsable:** Leonel Morales (Gestor) · **Prácticas XP:** Reuniones diarias de pie (stand-up) y rotación de parejas

El Gestor convoca las reuniones, controla el tiempo y anota los acuerdos aquí. Las reuniones son **de pie y de máximo 10 minutos**: no se resuelven problemas en la reunión, se acuerda con quién se resuelven.

## Calendario de reuniones

| Reunión | Cuándo | Duración | Para qué |
|---|---|---|---|
| Inicio del Día 1 | Antes de la primera ronda | 10 min | Arrancar la Iteración 1: repasar las historias del día y confirmar roles y parejas. |
| Mediodía del Día 1 | Entre la mañana y la tarde | 10 min | Ver el avance, mover impedimentos y rotar parejas. |
| Inicio del Día 2 | Antes de la primera ronda | 10 min | Revisar la velocidad del Día 1 (`xp velocidad`) y arrancar la Iteración 2. |
| Mediodía del Día 2 | Entre la mañana y la tarde | 10 min | Ver el avance, ordenar lo que falta de la entrega. |
| Cierre y retrospectiva | Al final del Día 2 | 20 min | Revisar lo que salió bien y lo que se mejora; verificar las directrices. |

## Rotación de parejas

Cada día tiene dos bloques (mañana y tarde) y en cada bloque trabajan **tres parejas**, de modo que todos programan con todos y cada pareja se repite una sola vez. La tabla es la que entrega `xp parejas`.

| Bloque | Ronda | Pareja A | Pareja B | Pareja C |
|---|---:|---|---|---|
| Día 1 · mañana | 1 | Sebastián Jibaja – Leonel Morales | Karen Mosquera – Daniel Mera | Kened Lugmaña – David Jumbo |
| Día 1 · tarde | 2 | Sebastián Jibaja – Daniel Mera | Leonel Morales – David Jumbo | Karen Mosquera – Kened Lugmaña |
| Día 2 · mañana | 3 | Sebastián Jibaja – David Jumbo | Daniel Mera – Kened Lugmaña | Leonel Morales – Karen Mosquera |
| Día 2 · tarde | 4 | Sebastián Jibaja – Kened Lugmaña | David Jumbo – Karen Mosquera | Daniel Mera – Leonel Morales |

Reglas:

1. En cada pareja hay un **conductor** (teclea) y un **navegante** (piensa y revisa); se cambian de rol cada 20–30 minutos.
2. La pareja trabaja en la historia de uno de los dos; el responsable de la historia (ver `xp/historias.csv`) decide el orden de las tareas.
3. Al cambiar de ronda, quien se queda con la tarea explica el estado a su nueva pareja.
4. Se integra y se prueba en cada cambio de ronda: «Pruebas + reporte de aceptación» en VS Code debe pasar antes de rotar.

## Formato de cada reunión

Cada integrante responde en una o dos frases:

1. ¿Qué terminé desde la última reunión?
2. ¿Qué voy a hacer ahora y con quién?
3. ¿Qué me impide avanzar?

Después, el Gestor anota **acuerdos**, **impedimentos** (con responsable y plazo) y los **puntos terminados** del bloque.

### Plantilla

```text
## Reunión: <nombre> · <día y hora>
Asistentes: Sebastián Jibaja, Karen Mosquera, Kened Lugmaña, David Jumbo, Daniel Mera y Leonel Morales
Duración: <min>
Parejas del bloque: A-B / C-D / E-F

Avances:
- Integrante n: ...
Impedimentos:
- ... (responsable: Integrante n, plazo: ...)
Acuerdos:
- ...
Puntos terminados en el bloque: <n> · Acumulado de la iteración: <n> de <planificados>
```

## Registro de reuniones

### Reunión: Inicio del Día 1 · Iteración 1

- **Asistentes:** Sebastián Jibaja, Karen Mosquera, Kened Lugmaña, David Jumbo, Daniel Mera y Leonel Morales · **Duración:** 10 min
- **Parejas del bloque:** Sebastián–Leonel, Karen–Daniel, Kened–David (ronda 1)
- **Objetivo del día:** 35 puntos (13 historias).
- **Avances:** el Cliente (Karen Mosquera) presentó el backlog `xp/historias.csv`; el Consultor (Daniel Mera) propuso la metáfora «Hormiguero de guerra».
- **Impedimentos:** ninguno.
- **Acuerdos:**
  - Primero el núcleo del framework (XP-01) y el autómata (HU-06): son la base de lo demás.
  - Las cédulas se escriben en `config/integrantes.csv` cuando cada uno las tenga; mientras tanto la aplicación avisa y sigue funcionando.
  - Se integra cada vez que cambia la ronda.

### Reunión: Mediodía del Día 1

- **Asistentes:** Sebastián Jibaja, Karen Mosquera, Kened Lugmaña, David Jumbo, Daniel Mera y Leonel Morales · **Duración:** 10 min
- **Parejas del bloque siguiente:** Sebastián–Daniel, Leonel–David, Karen–Kened (ronda 2)
- **Avances:** núcleo y directrices en marcha; autenticación y generación de `Grupo##.csv` con sus pruebas.
- **Impedimentos:** VS Code no reconocía JavaFX en una computadora porque se abrió una subcarpeta; siempre se abre la raíz del repositorio, donde están `.vscode/` y `lib/` (responsable: Daniel Mera).
- **Acuerdos:** cada pareja deja su historia con pruebas antes del cambio de ronda; el Encargado de seguimiento actualiza los estados en `xp/historias.csv`.

### Reunión: Inicio del Día 2 · Iteración 2

- **Asistentes:** Sebastián Jibaja, Karen Mosquera, Kened Lugmaña, David Jumbo, Daniel Mera y Leonel Morales · **Duración:** 10 min
- **Parejas del bloque:** Sebastián–David, Daniel–Kened, Leonel–Karen (ronda 3)
- **Velocidad del Día 1:** se leyó con `xp velocidad`; el objetivo del Día 2 es de 18 puntos (8 historias).
- **Acuerdos:**
  - Orden del Día 2 según `xp/plan-entregas.md`: HU-15, HU-14, HU-04 y HU-09 por la mañana; HU-07, HU-10, HU-11 y HU-02 por la tarde.
  - Si algo no cabe se mueve primero HU-10 (prioridad BAJA).

### Reunión: Mediodía del Día 2

- **Asistentes:** Sebastián Jibaja, Karen Mosquera, Kened Lugmaña, David Jumbo, Daniel Mera y Leonel Morales · **Duración:** 10 min
- **Parejas del bloque siguiente:** Sebastián–Kened, David–Karen, Daniel–Leonel (ronda 4)
- **Avances:** configuración por archivo, bomba y coordenadas con loading listas.
- **Impedimentos:** ninguno anotado.
- **Acuerdos:** cada integrante escribe su cédula en `config/integrantes.csv` y se pulsa «Recargar configuración» para regenerar `Grupo##.csv`, la bomba y el autómata del equipo.

### Reunión: Cierre y retrospectiva · Día 2

- **Asistentes:** Sebastián Jibaja, Karen Mosquera, Kened Lugmaña, David Jumbo, Daniel Mera y Leonel Morales · **Duración:** 20 min
- **Verificación:** «Pruebas + reporte de aceptación» en VS Code sin fallos y `xp verificar` con todas las directrices en `[OK]`.
- **Salió bien:** las parejas rotativas repartieron el conocimiento; el framework avisó qué artefacto faltaba y de quién era.
- **A mejorar:** estimar con más holgura el primer día y escribir las cédulas desde el inicio.
- **Acuerdos finales:** entregar el repositorio con `config/integrantes.csv` completo y los archivos de `datos/` regenerados.

## Actas de las sesiones (S1 a S4)

Las elabora Leonel Morales (Gestor). Las fases I y II se documentan con capturas y una reunión de coordinación; las fases III y IV se registran en video. Al empezar cada video se dice: «Grupo 4 Pragmatics, fecha, fase X de XP». Los enlaces a videos y capturas se registran también en `docs/evidencias.md`.

### Pareja de la sesión grabada

| Sesión | Pareja | Trabajo | Evidencia |
|---|---|---|---|
| S3 · Desarrollo | Karen Mosquera (conductora) – Sebastián Jibaja (navegante) | HU-01 Autenticación | Video de S3 |

La rotación del equipo se registra en la tabla de rotación de parejas (práctica *Rotaciones*).

### Acta de coordinación · S1 y S2 (Planificación y Diseño)

**Proyecto:** ANTDRON  
**Grupo:** 4 — Pragmatics  
**Metodología:** Extreme Programming (XP)  
**Fecha:** 17 de noviembre de 2026  
**Hora de inicio:** 09:00 a. m.  
**Duración:** 30 minutos  
**Modalidad:** Reunión virtual  
**Responsable:** Leonel Morales — Gestor

#### 1. Participantes

- Sebastián Jibaja — Entrenador (Coach).
- Karen Mosquera — Cliente.
- Kened Lugmaña — Encargado de pruebas (Tester).
- David Jumbo — Encargado de seguimiento (Tracker).
- Daniel Mera — Consultor.
- Leonel Morales — Gestor.

#### 2. Objetivo de la reunión

Coordinar la recopilación y presentación de evidencias de las cuatro fases de XP para el proyecto ANTDRON, estableciendo responsables, actividades y fechas de entrega.

#### 3. Organización de las sesiones

**S1 — Planificación:** participan los seis integrantes. Se presentan historias de usuario, plan de entregas, velocidad, iteraciones, rotaciones y reuniones mediante capturas y un acta.

**S2 — Diseño:** participan los seis integrantes. Se presentan la metáfora del sistema, tarjetas CRC, spikes, funcionalidad mínima, reciclaje y los cinco diagramas obligatorios.

**S3 — Desarrollo:** participan Karen Mosquera y Sebastián Jibaja. Se demuestra la colaboración mediante Live Share, la validación de HU-01, las pruebas y la integración mediante Pull Request.

**S4 — Pruebas y retrospectiva:** participan Leonel Morales, Kened Lugmaña y Daniel Mera. Se demuestra el despliegue con Docker, las pruebas de aceptación y la retrospectiva final.

#### 4. Fechas coordinadas

- **S3 — Desarrollo:** 18 de noviembre de 2026, 5:30 p. m.
- **S4 — Pruebas:** 18 de noviembre de 2026, 8:00 p. m.
- **Fecha límite para entregar capturas:** 19 de noviembre de 2026, 10:00 a. m.

Todas las horas corresponden a Ecuador.

#### 5. Distribución de responsabilidades

Según el plan de organización del Grupo 4:

- **Sebastián (Coach):** reglas XP, autómata, spikes 1 y 5, reciclaje con Utility, colaboración en S3 y versión v1.0.
- **Karen (Cliente):** historias de usuario, criterios de aceptación, casos de uso, funcionalidad mínima y validación de HU-01.
- **Kened (Tester):** pruebas unitarias, spike 4 y reporte de pruebas de aceptación.
- **David (Tracker):** plan de entregas, velocidad, iteraciones, diagrama de clases y seguimiento de historias.
- **Daniel (Consultor):** metáfora, tarjetas CRC, spike 2, arquitectura, refactorización y despliegue Docker.
- **Leonel (Gestor):** coordinación, rotaciones, actas, modelo entidad-relación, spike 3, índice de evidencias y retrospectiva.

#### 6. Resumen de la fase de Diseño

La fase de Diseño contempla la metáfora del hormiguero, las tarjetas CRC, cinco investigaciones técnicas (spikes), funcionalidad mínima, refactorización mediante la clase Utility y cinco diagramas: clases, casos de uso, autómata y gramática, entidad-relación y arquitectura.

Esta descripción corresponde al plan de trabajo; las intervenciones realizadas durante la reunión deben corroborarse con los participantes.

#### 7. Registro de evidencias

**Captura de la reunión:**

`docs/evidencias/1-planificacion/f1-reunion-coordinacion.png`

**Captura de rotaciones:**

`docs/evidencias/1-planificacion/f1-rotaciones.png`

**Documentación:** `xp/reuniones.md`

**Índice general:** `docs/evidencias.md`

Leonel Morales será responsable de recopilar las evidencias y verificar que cada práctica tenga su documentación correspondiente.

#### 8. Acuerdos de organización

Se toma como referencia el plan del Grupo 4 para distribuir las evidencias entre los seis integrantes, presentar S3 y S4 en los horarios indicados y completar las capturas antes de la fecha límite.

Estos compromisos deberán coincidir con los acuerdos efectivamente alcanzados durante la reunión.

#### 9. Cierre

Se establece la organización documental para las cuatro fases de XP, con responsables, entregables y fechas de presentación.

La ejecución y el cumplimiento de cada actividad se demostrarán mediante las capturas, actas, videos y registros de GitHub correspondientes.
### S3 Fase III: Desarrollo

**Proyecto:** ANTDRON  
**Grupo:** 4 — Pragmatics  
**Metodología:** Extreme Programming (XP)  
**Fecha:** 18 de noviembre de 2026  
**Hora de inicio programada:** 17:30  
**Duración de la grabación:** 2 minutos y 40 segundos  
**Responsable del acta:** Leonel Morales (Gestor)

#### 1. Participantes

- Karen Mosquera — Cliente.
- Sebastián Jibaja — Entrenador (Coach).

#### 2. Objetivo de la sesión

Evidenciar las prácticas de Extreme Programming durante el desarrollo y validación de la historia de usuario HU-01, correspondiente al sistema de autenticación de ANTDRON.

#### 3. Prácticas XP documentadas

**Programación en parejas:**

Se presenta una sesión colaborativa utilizando Discord para compartir la pantalla y Visual Studio Code para trabajar con el código Java.

Karen participa como conductora inicial y Sebastián como navegante, según la organización establecida para S3.

El cambio de roles se registra en el minuto **1:50** de la grabación, conforme al registro proporcionado por el equipo.

**Disponibilidad del cliente:**

Karen Mosquera participa en la sesión relacionada con HU-01, contribuyendo a la revisión del comportamiento esperado del sistema de autenticación.

**Pruebas unitarias:**

Se muestra el archivo `AutenticadorTest.java`, con pruebas relacionadas con la validación de credenciales y el límite de intentos.

**Integración:**

La implementación está documentada mediante el Pull Request #10, que figura como fusionado en el repositorio.

#### 4. Historia de usuario trabajada

**HU-01 — Autenticación**

Se aborda la corrección del comportamiento del bloqueo después de tres intentos fallidos.

El problema identificado consistía en que, después de agotar los intentos de autenticación, era posible recuperar tres nuevos intentos al regresar a la pantalla inicial desde el hormiguero virtual.

La corrección busca conservar el estado de bloqueo y evitar que se reinicie indebidamente el contador.

Los criterios asociados a HU-01 son:

- Validar las credenciales de los integrantes autorizados.
- Permitir un máximo de tres intentos fallidos.
- Bloquear el acceso cuando se agoten los intentos.
- Mantener el bloqueo cuando se vuelva a mostrar la pantalla de ingreso.
- Permitir la autenticación con credenciales válidas.

#### 5. Actividades observadas en el video

**Minutos 0:00–1:40:**

Se presenta código Java relacionado con las pruebas automatizadas de autenticación y el comportamiento de la aplicación.

**Minutos 1:40–2:15:**

Se muestra la aplicación ANTDRON, incluyendo el formulario de ingreso, el acceso al hormiguero virtual y los mensajes asociados a intentos incorrectos y al bloqueo.

**Minuto 1:50:**

Cambio de roles reportado por los participantes.

**Minutos 2:15–2:40:**

Se presenta documentación visual de la corrección realizada, incluyendo imágenes comparativas del comportamiento anterior y posterior.

#### 6. Resultados y validación

- **Historia:** HU-01 — Autenticación.
- **Estado registrado en el reporte:** ACEPTADA.
- **Pruebas aceptadas según `xp/pruebas-aceptacion.md`:** 7.
- **Pruebas fallidas según ese reporte:** 0.
- **Ejecución completa de pruebas en el video:** no se observa una salida concluyente del ejecutor.
- **Pull Request:** #10 — Fusionado.
- **Aprobación formal del cliente en GitHub:** no verificada.
- **Pruebas posteriores al merge:** no demostradas explícitamente en la grabación.

#### 7. Evidencias

**Video:** `Video4-Cliente.mp4` — enlace externo pendiente de registrar.

**Capturas disponibles en el repositorio:**

- `docs/evidencias/3-desarrollo/s3-liveshare-karen-autenticador.png`
- `docs/evidencias/3-desarrollo/s3-liveshare-karen-prueba.png`
- `docs/evidencias/3-desarrollo/s3-pruebas-hu01-sebas.png`
- `docs/evidencias/3-desarrollo/s3-hu01-antes-despues.png`

**Pull Request:** https://github.com/amsipan/ProyectoAgiles1erBim/pull/10

#### 8. Observaciones y seguimiento

- Mantener documentada la corrección del bloqueo de tres intentos.
- Conservar las capturas de programación colaborativa y del comportamiento anterior y posterior.
- Vincular el video con el índice general de evidencias.
- Adjuntar los resultados de las pruebas automatizadas como respaldo independiente de la demostración visual.

Estas acciones se registran como seguimiento documental; no se presentan como acuerdos verbales confirmados.

#### 9. Conclusión

La sesión documenta el trabajo colaborativo sobre HU-01 y demuestra visualmente el funcionamiento del inicio de sesión y el bloqueo por intentos fallidos.

El repositorio respalda la integración de la corrección mediante el Pull Request #10 y registra la aceptación de HU-01 con siete pruebas satisfactorias.

La grabación sirve como evidencia de colaboración y demostración funcional, mientras que el reporte de pruebas y GitHub complementan los resultados que no se observan completamente en el video.

### S4 · Fase IV: Pruebas y retrospectiva

- **Fecha:** ____ · **Hora de inicio:** ____ · **Duración:** ____ min
- **Participantes en el video:** Leonel Morales (Gestor), Kened Lugmaña (Encargado de pruebas) y Daniel Mera (Consultor)
- **Evidencia previa:** Karen Mosquera (aceptación en GitHub), David Jumbo (estado y velocidad), Sebastián Jibaja (v1.0 y evaluación de XP)
- **Prácticas defendidas:** implantación (Daniel) y pruebas de aceptación (Kened + Karen)
- **Docker:** `docker compose up --build` funcionando ( ) · app abierta en `http://localhost:6080/vnc.html` ( )
- **Reporte de aceptación:** ____/21 historias aceptadas
- **Aceptación del Cliente (Karen):** enlace al comentario en GitHub ____
- **Estado final y velocidad (David):** ____
- **Evaluación de XP y versión v1.0 (Sebastián):** enlace ____
- **Retrospectiva** (una frase por integrante):

| Integrante | Qué funcionó | Qué mejoraría | Medio |
|---|---|---|---|
| Sebastián Jibaja | | | Escrita |
| Karen Mosquera | | | Escrita |
| Kened Lugmaña | | | Video |
| David Jumbo | | | Escrita |
| Daniel Mera | | | Video |
| Leonel Morales | | | Video |

- **Acuerdos finales:**
  - ____
- **Video:** ____ · **Captura:** `docs/evidencias/4-pruebas/s4-participantes.png`
