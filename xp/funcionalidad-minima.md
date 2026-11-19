# Funcionalidad mínima

**Responsable:** Karen Mosquera (Cliente) · **Práctica XP:** Funcionalidad mínima · **Fase:** II. Diseño

> Se programa solo lo que piden las historias de la iteración actual; nada «por si acaso».

El Cliente decide qué es lo mínimo que la aplicación debe hacer para cumplir el enunciado. Esas historias tienen prioridad **ALTA** en `xp/historias.csv` y se programan primero. Lo de prioridad **MEDIA** o **BAJA** se hace solo cuando lo mínimo ya funciona y tiene sus pruebas en verde. Los datos (prioridad, puntos e iteración) salen del backlog: `xp backlog`.

## 1. Lo mínimo de la aplicación (prioridad ALTA)

Sin cualquiera de estas historias la aplicación no cumple el enunciado.

| Historia | Pts | Iter. | Responsable | Por qué es mínima |
|---|---:|---:|---|---|
| HU-01 Autenticación | 3 | 1 | Karen Mosquera | El enunciado exige pedir usuario y contraseña (integrantes y profesor `patmic`/`123`) con máximo 3 intentos antes de todo lo demás. |
| HU-02 Presentar equipo | 1 | 2 | David Jumbo | Después del ingreso se debe presentar el equipo y los alumnos (cédula y nombre). |
| HU-03 Generar `Grupo##.csv` | 3 | 1 | Kened Lugmaña | El enunciado pide crear el archivo con las coordenadas de las cédulas del equipo y una palabra `w` por coordenada. |
| HU-04 Leer coordenadas con loading | 3 | 2 | Kened Lugmaña | Hay que leer y presentar `Grupo##.csv` con el loading `\|/-` hasta 100 % por línea. |
| HU-05 Hormigas y alimentos | 3 | 1 | David Jumbo | El `TIPO_HORMIGA` del grupo (HZángano, omnívoro) nace como HLarva y se transforma al comer. |
| HU-06 Autómata | 3 | 1 | Sebastián Jibaja | La bomba se basa en el autómata `A = (Q, Σ, δ, q0, F)` de los últimos dígitos de las cédulas. |
| HU-07 IA buscar arsenal | 3 | 2 | Daniel Mera | La IA `buscar` devuelve `true` o `false` y presenta la coordenada donde explota la bomba. |
| HU-11 Pantallas del hormiguero virtual | 3 | 2 | Leonel Morales | El enunciado pide pantallas para que los usuarios KGD tengan su hormiguero y creen sus hormigas. |
| HU-13 Aplicación dockerizada | 2 | 1 | Daniel Mera | El cuestionario exige una aplicación dockerizada. |
| HU-14 Bomba | 2 | 2 | Sebastián Jibaja | Debe destruir **exclusivamente** el arsenal definido en el autómata. |
| HU-15 Configuración por archivo | 2 | 2 | Karen Mosquera | Las cédulas se escriben en un archivo y el programa genera lo demás sin tocar código. |

## 2. Se agrega cuando lo mínimo funciona (prioridad MEDIA y BAJA)

| Historia | Prioridad | Pts | Iter. | Responsable | Por qué puede esperar |
|---|---|---:|---:|---|---|
| HU-08 Armas en patas delanteras | MEDIA | 2 | 1 | Daniel Mera | Completa al AntCiberDron, pero la misión (buscar y explotar) funciona sin armas. |
| HU-09 Vuelo y fuente de poder | MEDIA | 2 | 2 | Karen Mosquera | Igual que las armas: amplía al dron sin afectar la bomba. |
| HU-12 Persistencia | MEDIA | 3 | 1 | Leonel Morales | Guarda el hormiguero entre sesiones. Se adelantó a la iteración 1 porque HU-11 (ALTA) la necesita. |
| HU-10 Idiomas | BAJA | 2 | 2 | David Jumbo | Es la última del enunciado y la primera que se mueve si falta tiempo (ver `xp/plan-entregas.md`). |

## 3. Framework XP

Las historias del framework (XP-01 a XP-06) son mínimas para **trabajar** con XP, no para la aplicación. Todas son ALTA excepto XP-04 (diseño: metáfora y CRC), que es MEDIA.

## 4. Cómo aplicamos la práctica

1. El Cliente ordena el backlog por prioridad y elige qué entra en cada iteración (`xp/plan-entregas.md`).
2. Cada pareja programa solo los criterios de aceptación de su historia (columna `criterios` de `xp/historias.csv`); lo que no está en un criterio no se programa.
3. Una historia termina cuando sus pruebas pasan y aparece **ACEPTADA** en `xp/pruebas-aceptacion.md`.
4. Si algo nuevo aparece durante el trabajo, se anota como historia nueva y el Cliente decide su prioridad; no se agrega «por si acaso».
