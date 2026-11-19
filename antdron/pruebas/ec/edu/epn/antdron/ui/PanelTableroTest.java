package ec.edu.epn.antdron.ui;

import ec.edu.epn.xp.pruebas.Afirmar;
import ec.edu.epn.xp.pruebas.Historia;
import ec.edu.epn.xp.pruebas.Prueba;

/** Textos legibles del tablero XP (no necesita abrir ventanas). */
@Historia("XP-03")
class PanelTableroTest {

    @Prueba
    void losNombresDeLasPruebasSeLeenComoFrases() {
        Afirmar.igual("Ciclo de vida de la hormiga se persiste", PanelTablero.humanizar("cicloDeVidaDeLaHormigaSePersiste"));
        Afirmar.igual("Ejemplo del enunciado · coordenadas 5y9", PanelTablero.humanizar("ejemploDelEnunciado_coordenadas5y9"));
        Afirmar.igual("Aceptación", PanelTablero.humanizar("aceptación"));
        Afirmar.igual("Compose monta config y datos", PanelTablero.humanizar("composeMontaConfigYDatos"));
    }

    @Prueba
    void losRolesSeMuestranConSuNombreXp() {
        Afirmar.igual("Entrenador (Coach)", Ui.rolLegible("ENTRENADOR"));
        Afirmar.igual("Encargado de seguimiento (Tracker)", Ui.rolLegible("ENCARGADO_SEGUIMIENTO"));
        Afirmar.igual("Sin rol", Ui.rolLegible(""));
        Afirmar.igual("PROFESOR", Ui.rolLegible("PROFESOR"));
    }

    @Prueba
    void lasInicialesIgnoranSimbolos() {
        Afirmar.igual("P", Ui.iniciales("Profesor (patmic)"));
        Afirmar.igual("KL", Ui.iniciales("Kened Lugmaña"));
        Afirmar.igual("?", Ui.iniciales("  "));
    }
}
