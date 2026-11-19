# Acta · S4 · Aceptación final del Cliente

**Grupo 4 Pragmatics · ANTDRON** · Fase IV de XP: Pruebas · Práctica: **pruebas de aceptación** (validación del Cliente)

> Completar solo los datos marcados con ✏️ al terminar la revisión. Lo demás ya refleja lo que se revisó.

| Dato | Valor |
|---|---|
| Fecha | ✏️ ____ / ____ / 2026 |
| Hora de inicio y fin | ✏️ ____ : ____ a ____ : ____ |
| Duración | ✏️ ____ minutos |
| Versión revisada | `main` con todos los PR de las fases 1 a 3 fusionados |

## Asistentes

| Nombre | Rol XP | Función |
|---|---|---|
| Karen Mosquera | Cliente | Recorre la aplicación y decide la aceptación |
| Sebastián Jibaja | Entrenador (Coach) | Acompaña la revisión y registra los acuerdos |

## 1. Recorrido de la aplicación

Karen usó la aplicación como la usaría el profesor. Evidencia: `docs/evidencias/4-pruebas/s4-recorrido-cliente-karen.png`.

| Pantalla | Historias | Resultado |
|---|---|---|
| ① Ingreso (máximo 3 intentos) | HU-01 | ✅ |
| ② Equipo: TEAM [4] Pragmatics y sus 6 alumnos | HU-02 | ✅ |
| ③ Coordenadas de `Grupo04.csv` con loading por línea | HU-03, HU-04 | ✅ |
| ④ Bomba y autómata: L = { abcdt+ , abcd* , ab* }, grafo y gramática | HU-06, HU-14 | ✅ |
| ⑤ AntCiberDron: IA destruye solo Coord-04, 05, 06, 07 y 09; metralleta, láser, vuelo, fuente de poder e idiomas | HU-07 a HU-10 | ✅ |
| ⑥ Hormiguero virtual: HLarva → HZángano al comer Omnívoro | HU-05, HU-11, HU-12 | ✅ |
| Configuración por archivo y aplicación dockerizada | HU-13, HU-15 | ✅ (Docker lo demuestra Daniel en S4) |

## 2. Reporte de aceptación

`xp aceptacion` y `xp/pruebas-aceptacion.md`: **21/21 historias ACEPTADAS** (15 de la aplicación y 6 del framework), con **100/100 pruebas** en verde.

## 3. Decisión del Cliente

**Karen Mosquera, como Cliente del Grupo 4 Pragmatics, acepta las 21 historias del backlog.**

## 4. Frases para la retrospectiva (las lee Leonel en S4)

- **Karen (Cliente):** «Funcionó tener los criterios de aceptación escritos desde el inicio: al probar HU-01 como Cliente encontré el error del bloqueo. Mejoraría probar la aplicación como Cliente al final de cada iteración, no solo al cierre.»
- **Sebas (Coach):** «Funcionó que el framework verificara las 17 prácticas y que Docker ejecute las pruebas al construir. Mejoraría que cada pull request tenga la revisión de otra persona antes de fusionarse, desde el primer día.»

## 5. Acuerdos

1. Karen sube esta acta y su recorrido (rama `karen/fase-4-validacion-cliente`).
2. Sebas sube las notas de la versión y publica la versión **v1.0** en GitHub (Releases). La versión publicada y el historial de PR son la evidencia; no se agregan capturas del merge ni de la versión.
3. Kened Lugmaña presenta esta acta junto con el reporte 21/21 en el video de S4; Leonel Morales la enlaza en `xp/reuniones.md` y `docs/evidencias.md`.

| Firma | Rol |
|---|---|
| Karen Mosquera | Cliente |
| Sebastián Jibaja | Entrenador (Coach) |
