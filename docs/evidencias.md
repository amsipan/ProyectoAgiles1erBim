# Evidencias de XP · Grupo 4 Pragmatics

**Responsable:** Leonel Morales (Gestor). Índice para el profesor: cada práctica XP con su artefacto y su evidencia (captura, video, acta o PR). El avance de cada tarea se ve en la pestaña **Tablero XP** de la aplicación, que lee `xp/tareas.csv`.

- Las capturas se guardan en [`docs/evidencias/`](evidencias/) con el nombre indicado; al subirla, la tarea pasa sola a «Hecha» en el tablero.
- Los videos se suben a Drive o YouTube (oculto) y se pega aquí el enlace; no se suben al repositorio.
- Nunca se capturan cédulas.

## I. Planificación (S1 · capturas)

| Práctica | Artefacto | Quién | Evidencia |
|---|---|---|---|
| Historias de usuario | [`xp/historias.csv`](../xp/historias.csv) | Karen Mosquera | [f1-historias.png](evidencias/f1-historias.png) · validación de Kened: [f1-validacion.png](evidencias/f1-validacion.png) |
| Plan de entregas | [`xp/plan-entregas.md`](../xp/plan-entregas.md) | David Jumbo | [f1-plan-entregas.png](evidencias/f1-plan-entregas.png) |
| Velocidad del proyecto | `xp velocidad` | David Jumbo | [f1-velocidad.png](evidencias/f1-velocidad.png) |
| Iteraciones | [`xp/plan-entregas.md`](../xp/plan-entregas.md) (iteraciones 1 y 2) | David Jumbo | [f1-plan-entregas.png](evidencias/f1-plan-entregas.png) |
| Rotaciones | [`xp/reuniones.md`](../xp/reuniones.md) o `xp parejas` | Leonel Morales | [f1-rotaciones.png](evidencias/f1-rotaciones.png) |
| Reuniones | [`xp/reuniones.md`](../xp/reuniones.md) (actas de coordinación, S3 y S4) | Leonel Morales | [f1-reunion-coordinacion.png](evidencias/f1-reunion-coordinacion.png) |
| (Reglas XP del equipo) | [`framework-xp/docs/guia-xp.md`](../framework-xp/docs/guia-xp.md) | Sebastián Jibaja | [f1-reglas-xp.png](evidencias/f1-reglas-xp.png) |

## II. Diseño (S2 · capturas y diagramas)

| Sesión | Fase | Evidencia | Participantes | Fecha | Capturas | Video | Acta |
|---|---|---|---|---|---|---|---|
| S1 | I. Planificación | Capturas + acta de coordinación | Los 6 | | [`1-planificacion/`](evidencias/1-planificacion/) | — | [Acta de coordinación](../xp/reuniones.md#acta-de-coordinación--s1-y-s2-planificación-y-diseño) |
| S2 | II. Diseño | Capturas y diagramas (incluye el reciclaje: clase `Utility`) | Los 6 | | [`2-diseno/`](evidencias/2-diseno/) | — | [Acta de coordinación](../xp/reuniones.md#acta-de-coordinación--s1-y-s2-planificación-y-diseño) |
| S3 | III. Desarrollo | Video (2:40) | Karen Mosquera y Sebastián Jibaja | 18/11/2026 | [Capturas](evidencias/3-desarrollo/) | [Ver video](https://epnecuador-my.sharepoint.com/personal/velky_jibaja_epn_edu_ec/_layouts/15/stream.aspx?id=%2Fpersonal%2Fvelky%5Fjibaja%5Fepn%5Fedu%5Fec%2FDocuments%2FVideos%20MA%20Proyecto%201er%20Bim%2FCliente%2FVideo4%2DCliente%2Emp4&share=cQo%5FugZXmz5BQIrjGmUzrt8IEgUDAzuSWnqQtNA1XUi6R6FShg&referrer=StreamWebApp%2EWeb&referrerScenario=AddressBarCopied%2Eview%2Eebb673f0%2D55eb%2D4a17%2D83f9%2Da87b1fef11b2) | [Acta S3](../xp/reuniones.md#s3-fase-iii-desarrollo) |
| S4 | IV. Pruebas y retrospectiva | Video (~20 min) | Leonel Morales, Kened Lugmaña y Daniel Mera | | [`4-pruebas/`](evidencias/4-pruebas/) |https://epnecuador-my.sharepoint.com/personal/velky_jibaja_epn_edu_ec/_layouts/15/stream.aspx?id=%2Fpersonal%2Fvelky%5Fjibaja%5Fepn%5Fedu%5Fec%2FDocuments%2FVideos%20MA%20Proyecto%201er%20Bim%2FGestor%2FLlamada%20con%20DANIEL%20y%201%20persona%20m%C3%A1s%2D20261118%5F235106%2DGrabaci%C3%B3n%20de%20la%20reuni%C3%B3n%2Emp4&share=cQqALYi1YZZNQICysACVZDd0EgUD3OKVRv1w9Y4%5Fr0KTarNmRw&referrer=StreamWebApp%2EWeb&referrerScenario=AddressBarCopied%2Eview%2Ee9a66fe5%2Dc3df%2D4866%2D9f0a%2D8710233796fe | [Acta S4](../xp/reuniones.md#s4--fase-iv-pruebas-y-retrospectiva) |

**Diagramas obligatorios** (en [`docs/diagramas/`](diagramas/)):

### I. Planificación

| # | Práctica | Quién | Artefacto en el repositorio | Sesión · Evidencia | Captura |
|---:|---|---|---|---|---|
| 1 | Historias de usuario | Karen | [`xp/historias.csv`](../xp/historias.csv) | S1 · Captura | [`f1-historias.png`](evidencias/1-planificacion/f1-historias.png) |
| 2 | Plan de entregas | David | [`xp/plan-entregas.md`](../xp/plan-entregas.md) | S1 · Captura | [`f1-plan-entregas.png`](evidencias/1-planificacion/f1-plan-entregas.png) |
| 3 | Velocidad del proyecto | David | [`xp/plan-entregas.md`](../xp/plan-entregas.md) y `xp velocidad` | S1 · Captura | [`f1-velocidad.png`](evidencias/1-planificacion/f1-velocidad.png) |
| 4 | Iteraciones | David | [`xp/plan-entregas.md`](../xp/plan-entregas.md) (iteraciones 1 y 2) | S1 · Captura | [`f1-plan-entregas.png`](evidencias/1-planificacion/f1-plan-entregas.png) |
| 5 | Rotaciones | Leonel | [`xp/reuniones.md`](../xp/reuniones.md) (tabla de parejas por ronda) y `xp parejas` | S1 · Captura | [`f1-rotaciones.png`](evidencias/1-planificacion/f1-rotaciones.png) |
| 6 | Reuniones | Leonel | [`xp/reuniones.md`](../xp/reuniones.md) (acta de coordinación, S3 y S4) | S1 a S4 · Actas | [`f1-reunion-coordinacion.png`](evidencias/1-planificacion/f1-reunion-coordinacion.png) |

### II. Diseño

| # | Práctica | Quién | Artefacto en el repositorio | Sesión · Evidencia | Captura |
|---:|---|---|---|---|---|
| 7 | Metáfora del sistema | Daniel | [`xp/metafora.md`](../xp/metafora.md) | S2 · Captura | [`f2-metafora.png`](evidencias/2-diseno/f2-metafora.png) |
| 8 | Tarjetas CRC | Daniel | [`xp/tarjetas-crc.md`](../xp/tarjetas-crc.md) y `xp crc` | S2 · Captura | [`f2-crc.png`](evidencias/2-diseno/f2-crc.png) |
| 9 | Soluciones puntuales (spikes) | Sebas, Daniel, Leonel, Kened | [`xp/spikes.md`](../xp/spikes.md) | S2 · Captura | [`f2-spikes-1-5.png`](evidencias/2-diseno/f2-spikes-1-5.png), [`f2-spike2.png`](evidencias/2-diseno/f2-spike2.png), [`f2-spike3.png`](evidencias/2-diseno/f2-spike3.png), [`f2-spike4.png`](evidencias/2-diseno/f2-spike4.png) |
| 10 | Funcionalidad mínima | Karen | [`xp/funcionalidad-minima.md`](../xp/funcionalidad-minima.md) | S2 · Captura | [`f2-funcionalidad-minima.png`](evidencias/2-diseno/f2-funcionalidad-minima.png) |
| 11 | Reciclaje (refactorización) | Sebas + Daniel | [`Utility.java`](../antdron/src/ec/edu/epn/antdron/pkAntCiberDronContract/Utility.java) y las clases que la usan | S2 · Captura | [`f2-utility.png`](evidencias/2-diseno/f2-utility.png), [`f2-reciclaje-pruebas.png`](evidencias/2-diseno/f2-reciclaje-pruebas.png), [`f2-utility-crc.png`](evidencias/2-diseno/f2-utility-crc.png) |

### III. Desarrollo

| # | Práctica | Quién | Artefacto en el repositorio | Sesión · Evidencia | Captura | Video (minuto) | PR |
|---:|---|---|---|---|---|---|---|
| 12 | Disponibilidad del cliente | Karen | Criterios de HU-01 y aprobación del PR | S3 · Video | — | | |
| 13 | Unidad de pruebas | Kened | [`antdron/pruebas/`](../antdron/pruebas/) y [`framework-xp/pruebas/`](../framework-xp/pruebas/) | S3 · Captura (presentada en el video) | [`s3-pruebas-verde-hu01.png`](evidencias/3-desarrollo/s3-pruebas-verde-hu01.png) | | |
| 14 | Programación en parejas | Karen + Sebas | Live Share (HU-01) | S3 · Video | [`s3-live-share-karen-sebas.png`](evidencias/3-desarrollo/s3-live-share-karen-sebas.png) | | |
| 15 | Integración | Sebas | PR aprobado y fusionado a `main` | S3 · Video | [`s3-pr-fusionado.png`](evidencias/3-desarrollo/s3-pr-fusionado.png) | | [PR #10](https://github.com/amsipan/ProyectoAgiles1erBim/pull/10)|

### IV. Pruebas

| # | Práctica | Quién | Artefacto en el repositorio | Sesión · Evidencia | Captura | Video (minuto) | PR |
|---:|---|---|---|---|---|---|---|
| 16 | Implantación | Daniel | [`Dockerfile`](../Dockerfile) y [`docker-compose.yml`](../docker-compose.yml) | S4 · Video | [`s4-docker-funcionando.png`](evidencias/4-pruebas/s4-docker-funcionando.png) | | |
| 17 | Pruebas de aceptación | Kened + Karen | [`xp/pruebas-aceptacion.md`](../xp/pruebas-aceptacion.md) (21/21) y aceptación de Karen en GitHub | S4 · Video | [`s4-reporte-21-de-21.png`](evidencias/4-pruebas/s4-reporte-21-de-21.png), [`s4-aceptacion-karen.png`](evidencias/4-pruebas/s4-aceptacion-karen.png) | | |

## Diagramas obligatorios del enunciado

| Diagrama | Quién | Archivo | Sesión · Evidencia | Captura |
|---|---|---|---|---|
| Diagrama de clases | David | [`docs/diagramas/clases.md`](diagramas/clases.md) | S2 · Captura | [`f2-clases.png`](evidencias/2-diseno/f2-clases.png) |
| Diagrama de casos de uso | Karen | [`docs/diagramas/casos-uso.md`](diagramas/casos-uso.md) | S2 · Captura | [`f2-casos-uso.png`](evidencias/2-diseno/f2-casos-uso.png) |
| Grafo con gramática del autómata | Sebas | [`docs/diagramas/grafo-automata.md`](diagramas/grafo-automata.md) y [`datos/automata-grupo04.md`](../datos/automata-grupo04.md) | S2 · Captura | [`f2-grafo-automata.png`](evidencias/2-diseno/f2-grafo-automata.png) |
| Modelo entidad-relación | Leonel | [`docs/diagramas/modelo-er.md`](diagramas/modelo-er.md) | S2 · Captura | [`f2-modelo-er.png`](evidencias/2-diseno/f2-modelo-er.png) |
| Diagrama de arquitectura | Daniel | [`docs/diagramas/arquitectura.md`](diagramas/arquitectura.md) | S2 · Captura | [`f2-arquitectura.png`](evidencias/2-diseno/f2-arquitectura.png) |

## Entrega final

| Evidencia | Quién | Dónde |
|---|---|---|
| [Arquitectura](diagramas/arquitectura.md) | Daniel Mera | [f2-arquitectura.png](evidencias/f2-arquitectura.png) |
| [Casos de uso](diagramas/casos-uso.md) | Karen Mosquera | [f2-casos-uso.png](evidencias/f2-casos-uso.png) |
| [Clases](diagramas/clases.md) | David Jumbo | [f2-clases.png](evidencias/f2-clases.png) |
| [Grafo del autómata](diagramas/grafo-automata.md) | Sebastián Jibaja | [f2-grafo-automata.png](evidencias/f2-grafo-automata.png) |
| [Entidad-relación (SQLite)](diagramas/modelo-er.md) | Leonel Morales | [f2-modelo-er.png](evidencias/f2-modelo-er.png) |

## III. Desarrollo (S3 · video de ~12 min, Karen y Sebastián)

| Práctica | Artefacto | Quién | Evidencia |
|---|---|---|---|
| Programación por parejas | Live Share sobre HU-01 (`Autenticador`, `Claves`) | Karen Mosquera y Sebastián Jibaja | Video S3: _(enlace)_ · [f3-live-share.png](evidencias/f3-live-share.png) |
| Disponibilidad del cliente | Karen confirma los criterios de HU-01 y aprueba el PR | Karen Mosquera | Video S3: _(enlace)_ · PR: _(enlace)_ |
| Unidad de pruebas | [`antdron/pruebas/`](../antdron/pruebas/) | Kened Lugmaña | [f3-pruebas-hu01.png](evidencias/f3-pruebas-hu01.png) |
| Integración continua | PR aprobado y fusionado a `main` | Sebastián Jibaja | PR: _(enlace)_ |

## IV. Pruebas (S4 · video de ~20 min, Leonel, Kened y Daniel)

| Práctica | Artefacto | Quién | Evidencia |
|---|---|---|---|
| Implantación | [`Dockerfile`](../Dockerfile) y `docker compose up --build` | Daniel Mera | Video S4: _(enlace)_ · [f4-docker.png](evidencias/f4-docker.png) |
| Pruebas de aceptación | [`xp/pruebas-aceptacion.md`](../xp/pruebas-aceptacion.md) | Kened Lugmaña y Karen Mosquera | Video S4: _(enlace)_ · aceptación de Karen en GitHub: _(enlace)_ |
| (Estado y velocidad final) | `xp plan` y `xp velocidad` | David Jumbo | [f4-estado-velocidad.png](evidencias/f4-estado-velocidad.png) |
| (Versión final) | Release `v1.0` con la evaluación de XP | Sebastián Jibaja | Release: _(enlace)_ |
| (Retrospectiva) | Acta en [`xp/reuniones.md`](../xp/reuniones.md) | Leonel Morales | [f4-cierre.png](evidencias/f4-cierre.png) |
