# Acta · S3 · Validación del Cliente de HU-01 (Autenticación)

**Grupo 4 Pragmatics · ANTDRON** · Fase III de XP: Desarrollo · Prácticas: **disponibilidad del cliente** y **programación en parejas**

> Completar solo los datos marcados con ✏️ al terminar la sesión. Lo demás ya refleja lo que se revisó.

| Dato | Valor |
|---|---|
| Fecha | ✏️ ____ / ____ / 2026 |
| Hora de inicio y fin | ✏️ ____ : ____ a ____ : ____ |
| Duración | ✏️ ____ minutos |
| Modalidad | Sesión remota con **Visual Studio Live Share** (anfitrión: Sebas) |
| Lugar del trabajo | Rama `sebas/fase-3-hu01-autenticacion` → pull request de HU-01 |

## Asistentes

| Nombre | Rol XP | Función en la sesión |
|---|---|---|
| Karen Mosquera | Cliente | Conductora (primera mitad) y responsable de aceptar la historia |
| Sebastián Jibaja | Entrenador (Coach) | Navegante (primera mitad), conductor (segunda mitad) y anfitrión de Live Share |

## 1. Historia revisada

**HU-01 Autenticación** — «Como integrante o profesor, quiero ingresar con usuario y contraseña para usar el sistema.»

## 2. Problema encontrado por el Cliente

Al probar la pantalla, Karen encontró que después de **3 claves incorrectas** bastaba con entrar al **hormiguero virtual** y pulsar **«Volver al inicio»** para recibir **3 intentos nuevos**. Esto incumple el criterio «máximo 3 intentos».

## 3. Trabajo en pareja (Live Share)

| Turno | Conductor/a | Navegante | Archivo | Qué se hizo |
|---|---|---|---|---|
| 1 | Karen | Sebas | `antdron/pruebas/.../auth/AutenticadorTest.java` | **Prueba primero:** el bloqueo debe conservarse al volver a la pantalla de ingreso. |
| 1 | Karen | Sebas | `antdron/src/.../auth/Autenticador.java` | Nuevo `conEquipo(...)`: conserva los intentos fallidos al recargar el equipo. |
| 2 | Sebas | Karen | `antdron/src/.../ui/AntdronFx.java` | Un solo autenticador por sesión de ingreso; se reinicia solo tras un ingreso correcto. |
| 2 | Sebas | Karen | `antdron/src/.../ui/VistaIngreso.java` | Muestra el bloqueo apenas se abre la pantalla si ya no quedan intentos. |

Evidencia: `docs/evidencias/3-desarrollo/s3-liveshare-karen-*.png` (turno 1) y `s3-liveshare-sebas-*.png` (turno 2).

## 4. Verificación de los criterios de aceptación (Karen, como Cliente)

| # | Criterio de HU-01 | Cómo se comprobó | Resultado |
|---|---|---|---|
| 1 | Ingresan los integrantes de `config/integrantes.csv` y el profesor `patmic` / `123`. | Ingreso con `patmic`/`123` y con `kmosquera`/`1234`, en pantalla y en consola. | ✅ Cumple |
| 2 | Máximo 3 intentos. | Tres claves incorrectas → «Acceso denegado». Volver del hormiguero → **sigue en 0 intentos**. | ✅ Cumple |
| 3 | Funciona en consola y en la pantalla. | Consola: «Intentos restantes: 2… 1… Acceso denegado». Pantalla: mismo resultado. | ✅ Cumple |
| — | No debe castigar a otros usuarios. | Tras un ingreso correcto y «Cerrar sesión», el siguiente usuario tiene 3 intentos. | ✅ Correcto |
| — | Pruebas automáticas. | «Pruebas + reporte de aceptación»: 100/100 en verde; HU-01 con 7 pruebas, ACEPTADA. | ✅ Correcto |

Evidencia: `docs/evidencias/3-desarrollo/s3-criterios-hu01-karen.png`, `s3-hu01-antes-despues.png` y `s3-pruebas-hu01-sebas.png`.

## 5. Decisión del Cliente

**Karen Mosquera (Cliente) acepta HU-01.** La aprobación queda registrada en GitHub como *Approve* del pull request de HU-01; la integración, como el *merge* de ese PR a `main` hecho por Sebas. **El historial del repositorio es la evidencia de ambos pasos** (no se agregan capturas del merge).

## 6. Acuerdos

1. Karen aprueba el PR de HU-01 en GitHub y Sebas lo fusiona a `main`.
2. Karen sube esta acta, su captura de criterios y sus capturas de Live Share (rama `karen/fase-3-criterios-hu01`).
3. Sebas sube sus capturas de Live Share y las de antes/después y pruebas (rama `sebas/fase-3-evidencias-hu01`).
4. Kened Lugmaña (Tester) sube su captura de las 100 pruebas en verde; Leonel Morales (Gestor) enlaza esta acta en `xp/reuniones.md` y `docs/evidencias.md`.

| Firma | Rol |
|---|---|
| Karen Mosquera | Cliente |
| Sebastián Jibaja | Entrenador (Coach) |
