# ANTDRON · Proyecto XP (Metodologías Ágiles · EPN)

Proyecto en dos partes independientes, desarrollado con Extreme Programming:

| Parte | Carpeta | Descripción |
|---|---|---|
| 1. Framework XP | `framework-xp/` | Verifica las prácticas XP y muestra el trabajo del equipo, el backlog, las iteraciones y la aceptación en un tablero. |
| 2. Aplicación AntCiberDron | `antdron/` | Lo que pide el enunciado, con pantallas **JavaFX** y dockerizada. Funciona sin el framework. |

En Windows, VS Code compila el proyecto. JavaFX 21 y el controlador SQLite vienen en `lib/`.

## Requisitos (Windows)

1. **JDK 21 o superior**, por ejemplo [Temurin 21](https://adoptium.net). Con versiones nuevas (por ejemplo Java 27) la aplicación funciona igual; solo aparecen avisos `WARNING` de JavaFX en la consola, que no afectan.
2. **VS Code** con la extensión **Extension Pack for Java**. VS Code la sugiere al abrir la carpeta.
3. Para la versión dockerizada: **Docker Desktop**.

## Ejecutar en VS Code

1. **Archivo → Abrir carpeta…** y elija la carpeta raíz del repositorio, donde están `.vscode/` y `lib/`.
2. Espere a que VS Code termine de cargar el proyecto Java (abajo a la derecha). Compila solo cada vez que guarda.
3. Abra **Ejecutar y depurar** (Ctrl+Shift+D), elija una configuración y pulse **F5**:

| Configuración | Qué hace |
|---|---|
| **AntCiberDron (JavaFX)** | Abre la aplicación. |
| **AntCiberDron (consola)** | Versión de consola del enunciado, en la terminal. |
| **AntCiberDron (autómata de cédulas)** | Imprime el autómata, la gramática y el grafo de las cédulas que escriba. |
| **Pruebas + reporte de aceptación** | Ejecuta todas las pruebas y actualiza `xp/pruebas-aceptacion.md`. |
| **Framework XP (tablero del equipo)** | Genera `build/xp/tablero.html` y lo abre en el navegador. Ejecute primero las pruebas para que el tablero muestre la aceptación. |
| **Framework XP (comando)** | Pide un comando del framework: `verificar`, `backlog`, `plan`, `velocidad`, `parejas 2`, `crc`, `ayuda`... |

## Usuarios

| Usuario | Clave | Quién |
|---|---|---|
| `patmic` | `123` | Profesor |
| `sjibaja` | `1234` | Sebastián Jibaja · Entrenador |
| `kmosquera` | `1234` | Karen Mosquera · Cliente |
| `klugmana` | `1234` | Kened Lugmaña · Encargado de pruebas |
| `djumbo` | `1234` | David Jumbo · Encargado de seguimiento |
| `dmera` | `1234` | Daniel Mera · Consultor |
| `lmorales` | `1234` | Leonel Morales · Gestor |

Máximo 3 intentos; al tercer error se bloquea el ingreso.

## Configuración del equipo (Grupo 4 · Pragmatics)

El grupo está en `config/equipo.properties` (`grupo=4`, `nombre=Pragmatics`) y los integrantes en `config/integrantes.csv`, con su cédula en la **primera columna**:

```
cedula;nombre;usuario;clave;rol
1754265385;Sebastián Jibaja;sjibaja;1234;ENTRENADOR
1750094144;Karen Mosquera;kmosquera;1234;CLIENTE
...
```

Con los últimos dígitos de las cédulas (5, 4, 6, 7, 9 y 7) las coordenadas objetivo son Coord-04, 05, 06, 07 y 09, y la bomba reconoce **L = { abcdt+ , abcd* , ab* }**. La hormiga del grupo 4 es **HZángano** (omnívoro).

Si se cambia una cédula, pulse **«Recargar configuración»** en la aplicación (o vuelva a abrirla). El programa hace todo lo demás:

- genera `datos/Grupo##.csv` con las coordenadas de las cédulas;
- lo lee y lo muestra con su animación de carga;
- construye la bomba con el último dígito de cada cédula, su autómata, su gramática y su grafo;
- exporta `datos/automata-grupo##.md` (definición formal, gramática, Mermaid y Graphviz) para la documentación;
- prepara al AntCiberDron con la hormiga del grupo (1-2 HSoldado, 3-4 HZángano, 5 HRastreadora).

Si faltan cédulas, la aplicación no falla: usa las que haya y muestra un aviso. Edite los archivos solo con VS Code, el Bloc de notas o el editor web de GitHub; **no use Excel**, porque cambia el separador y la codificación.

## Ejecutar con Docker (aplicación dockerizada)

Con Docker Desktop abierto, en una terminal dentro de la carpeta del repositorio:

```powershell
docker compose up -d --build --wait
docker compose ps
docker compose run --rm consola    # versión de consola
docker compose run --rm antdron verificar   # cualquier comando del framework XP
docker compose run --rm antdron tablero --salida datos/tablero-xp.html
```

Abra [AntCiberDron en noVNC](http://localhost:6080/vnc.html?autoconnect=true&resize=scale) cuando `docker compose ps` muestre el servicio `antdron` como `healthy`. El arranque espera a que respondan noVNC y la ventana JavaFX. La imagen ejecuta todas las pruebas en la construcción.

Abra **Entrar al hormiguero virtual**, registre una cuenta y pulse **Crear HLarva**. El archivo SQLite se crea en `datos/hormiguero/hormiguero.db` y el montaje de `datos/` lo conserva cuando se recrea el contenedor. En la primera migración, los datos de los CSV se importan con sus identificadores. El CSV de coordenadas `datos/Grupo##.csv` sigue siendo un artefacto independiente.

El tablero exportado desde Docker queda en `datos/tablero-xp.html`. Ábralo con su navegador.

## Documentación

| Documento | Contenido |
|---|---|
| [docs/evidencias.md](docs/evidencias.md) | Índice para el profesor: cada práctica XP con su artefacto, video, captura, acta y PR |
| [docs/division-tareas.md](docs/division-tareas.md) | Roles, reparto de historias, contratos y plan de 2 días |
| [framework-xp/docs/guia-xp.md](framework-xp/docs/guia-xp.md) | Directrices XP del framework |
| [framework-xp/docs/estandar-codigo.md](framework-xp/docs/estandar-codigo.md) | Estándar de código |
| [docs/diagramas/clases.md](docs/diagramas/clases.md) | Diagrama de clases |
| [docs/diagramas/casos-uso.md](docs/diagramas/casos-uso.md) | Diagrama de casos de uso |
| [docs/diagramas/grafo-automata.md](docs/diagramas/grafo-automata.md) | Grafo con gramática del autómata |
| [docs/diagramas/modelo-er.md](docs/diagramas/modelo-er.md) | Modelo entidad-relación |
| [docs/diagramas/arquitectura.md](docs/diagramas/arquitectura.md) | Diagrama de arquitectura |
| [xp/](xp/) | Backlog, plan de entregas, reuniones y actas, metáfora, tarjetas CRC, spikes, funcionalidad mínima y reporte de aceptación |
| [Videos de evidendia](https://epnecuador-my.sharepoint.com/:f:/r/personal/velky_jibaja_epn_edu_ec/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fvelky%5Fjibaja%5Fepn%5Fedu%5Fec%2FDocuments%2FVideos%20MA%20Proyecto%201er%20Bim&share=cgrUtkIE7YrIQZ%2DL%2DrtcRnQREgUDKWnlfLpdcQsaUgJLh1w5qw) | Videos de evidencia de la aplicación de programación XP, seccionador por los roles de los integrantes |

## Estructura

```
.vscode/        carpetas de fuentes, JavaFX y configuraciones de ejecución para VS Code
lib/            JavaFX 21.0.5 para Windows y controlador SQLite JDBC
framework-xp/   Parte 1: src/, pruebas/, docs/
antdron/        Parte 2: src/, pruebas/
config/         equipo.properties e integrantes.csv (cédulas)
datos/          Grupo04.csv, automata-grupo04.md y hormiguero/hormiguero.db generado al abrirlo
docs/           diagramas/, evidencias.md (índice) y evidencias/ (capturas)
xp/             artefactos XP que revisa el framework
docker/, Dockerfile, docker-compose.yml
```
