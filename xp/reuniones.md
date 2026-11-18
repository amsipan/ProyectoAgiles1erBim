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

- **Fecha:** ____ · **Hora de inicio:** ____ · **Duración:** ____ min · **Medio:** chat / llamada
- **Asistentes:** Sebastián Jibaja (Entrenador), Karen Mosquera (Cliente), Kened Lugmaña (Encargado de pruebas), David Jumbo (Encargado de seguimiento), Daniel Mera (Consultor), Leonel Morales (Gestor)
- **Fechas acordadas:** S3 ____ · S4 ____ · plazo para subir capturas ____
- **Participantes de los videos:** S3: Karen Mosquera y Sebastián Jibaja · S4: Leonel Morales, Kened Lugmaña y Daniel Mera
- **Resumen de la fase de Diseño (Daniel):** ____
- **Capturas de S1 y S2:** cada rol según `docs/evidencias/LEEME.md`
- **Acuerdos:**
  - ____
- **Captura de la reunión:** `docs/evidencias/1-planificacion/f1-reunion-coordinacion.png`

### S3 · Fase III: Desarrollo

- **Fecha:** ____ · **Hora de inicio:** ____ · **Duración:** ____ min
- **Participantes en el video:** Karen Mosquera (Cliente) y Sebastián Jibaja (Entrenador)
- **Evidencia previa:** Kened Lugmaña (captura de las pruebas: `s3-pruebas-verde-hu01.png`)
- **Prácticas defendidas:** programación en parejas, disponibilidad del cliente, unidad de pruebas e integración
- **Historia trabajada:** HU-01 Autenticación · conductora: Karen Mosquera · navegante: Sebastián Jibaja · cambio de rol a los ____ min
- **Criterios de HU-01 confirmados por el Cliente:** máximo 3 intentos ( ) · ingreso de integrantes ( ) · ingreso de `patmic`/`123` ( )
- **Pruebas:** ____ en verde · HU-01: ____ pruebas aceptadas
- **PR:** aprobado por Karen ( ) · fusionado por Sebastián ( ) · pruebas en verde después de fusionar ( )
- **Acuerdos:**
  - ____
- **Video:** ____ · **Captura:** `s3-live-share-karen-sebas.png`

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
