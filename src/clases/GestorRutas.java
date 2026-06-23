package clases;

import java.util.*;

public class GestorRutas {

    private static final int LIMITE_CLIENTES_POR_DIA = 30;

    private Map<Dia, Ruta> rutasPorDia = new HashMap<>();
    private List<Cliente> clientes; // referencia a todos los clientes cargados en el sistema

    public GestorRutas(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String crearRuta(Dia dia) {
        if (rutasPorDia.containsKey(dia)) {
            return "[!] Ya existe una ruta para el dia " + dia + ". Eliminala primero si querés volver a crearla.";
        }

        List<Cliente> clientesDelDia = obtenerClientesPorDia(dia);

        if (clientesDelDia.isEmpty()) {
            return "[!] No hay clientes cargados para el dia " + dia + ".";
        }

        if (clientesDelDia.size() > LIMITE_CLIENTES_POR_DIA) {
            return "[!] Hay " + clientesDelDia.size() + " clientes para el dia " + dia
                    + ", superando el limite de " + LIMITE_CLIENTES_POR_DIA
                    + ". Modificá el dia de algunos clientes para repartirlos en otro dia.";
        }

        List<Camion> camiones = ClarkeWright.generarCamiones(clientesDelDia);
        Ruta ruta = new Ruta(dia, camiones);
        rutasPorDia.put(dia, ruta);

        return "Ruta creada con exito para el dia " + dia + ".\n" + ruta;
    }

    public String eliminarRuta(Dia dia) {
        if (!rutasPorDia.containsKey(dia)) {
            return "[!] No existe una ruta para el dia " + dia + ".";
        }
        rutasPorDia.remove(dia);
        return "Ruta del dia " + dia + " eliminada con exito.";
    }

    public Ruta getRuta(Dia dia) {
        return rutasPorDia.get(dia);
    }

    public Collection<Ruta> getTodasLasRutas() {
        return rutasPorDia.values();
    }

    private List<Cliente> obtenerClientesPorDia(Dia dia) {
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente c : clientes) {
            if (c.getDia() == dia) {
                resultado.add(c);
            }
        }
        return resultado;
    }
}
