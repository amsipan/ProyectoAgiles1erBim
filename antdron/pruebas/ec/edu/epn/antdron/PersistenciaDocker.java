package ec.edu.epn.antdron;

import java.util.List;

import ec.edu.epn.antdron.persistencia.HormigaRegistro;
import ec.edu.epn.antdron.persistencia.HormigueroServicio;

/** Prueba de integración ejecutada en dos contenedores sucesivos con el mismo volumen de datos. */
public final class PersistenciaDocker {

    private PersistenciaDocker() {
    }

    public static void main(String[] args) throws Exception {
        HormigueroServicio servicio = MotorAntdron.cargar(MotorAntdron.raizPorDefecto()).hormiguero();
        String usuario = "docker_prueba";
        String clave = "docker-prueba";
        switch (args[0]) {
            case "crear" -> {
                int id = servicio.registrar(usuario, clave);
                int hormiga = servicio.crearHormiga(id, "HEMBRA");
                servicio.alimentar(id, hormiga, "Néctar");
                servicio.alimentar(id, hormiga, servicio.getTipoDelGrupo().alimento().getNombre());
                System.out.println("Hormiga guardada en " + servicio.getArchivoBaseDatos());
            }
            case "reabrir" -> {
                int id = servicio.autenticar(usuario, clave).orElseThrow();
                List<HormigaRegistro> hormigas = servicio.listar(id);
                if (hormigas.size() != 1 || !hormigas.getFirst().tipo().equals(servicio.getTipoDelGrupo().getNombre())
                        || !hormigas.getFirst().estado().equals("VIVA")) {
                    throw new IllegalStateException("Los datos recuperados no coinciden: " + hormigas);
                }
                System.out.println("Hormiga recuperada: " + hormigas.getFirst());
            }
            default -> throw new IllegalArgumentException("Se esperaba crear o reabrir");
        }
    }
}
