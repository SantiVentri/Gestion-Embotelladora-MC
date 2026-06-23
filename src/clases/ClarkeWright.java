package clases;

import java.util.*;

/**
 * Implementacion del algoritmo de Clarke-Wright (Savings) para generar la ruta
 * mas eficiente que recorra a un grupo de clientes, partiendo y volviendo a la
 * fabrica (ubicada en las coordenadas (0,0)).
 *
 * Si el grupo de clientes supera la capacidad maxima de un camion (CAPACIDAD_CAMION),
 * el algoritmo respeta esa restriccion y devuelve varios camiones en vez de uno solo.
 */
public class ClarkeWright {

    private static final int CAPACIDAD_CAMION = 10; // limite hardcodeado de clientes por camion

    /**
     * Genera la lista de camiones (cada uno con su recorrido ya ordenado) necesarios
     * para atender a todos los clientes recibidos.
     */
    public static List<Camion> generarCamiones(List<Cliente> clientes) {
        int cantCamiones = (int) Math.ceil((double) clientes.size() / CAPACIDAD_CAMION);

        // Capacidad efectiva por camion: si hay pocos clientes, un solo camion con todos.
        int capacidadEfectiva = (cantCamiones <= 1) ? clientes.size() : CAPACIDAD_CAMION;

        List<List<Cliente>> rutasClientes = clarkeWright(clientes, capacidadEfectiva);

        List<Camion> camiones = new ArrayList<>();
        for (List<Cliente> ruta : rutasClientes) {
            double distancia = calcularDistanciaRuta(ruta);
            camiones.add(new Camion(ruta, distancia));
        }
        return camiones;
    }

    /**
     * Clarke-Wright (Savings) con restriccion de capacidad: arranca con un viaje
     * individual fabrica-cliente-fabrica para cada cliente, y va fusionando rutas
     * de a pares segun el ahorro de distancia que generan, sin nunca superar la
     * capacidad maxima por camion.
     */
    private static List<List<Cliente>> clarkeWright(List<Cliente> clientes, int capacidad) {
        // Cada cliente arranca en su propia ruta
        List<LinkedList<Cliente>> rutas = new ArrayList<>();
        for (Cliente c : clientes) {
            LinkedList<Cliente> r = new LinkedList<>();
            r.add(c);
            rutas.add(r);
        }

        // Calcular el ahorro de fusionar cada par de clientes:
        // ahorro(i,j) = distancia(Fabrica,i) + distancia(Fabrica,j) - distancia(i,j)
        List<Object[]> ahorros = new ArrayList<>(); // {ahorro, clienteI, clienteJ}
        for (int i = 0; i < clientes.size(); i++) {
            for (int j = i + 1; j < clientes.size(); j++) {
                Cliente ci = clientes.get(i);
                Cliente cj = clientes.get(j);
                double ahorro = distanciaFabrica(ci) + distanciaFabrica(cj) - distancia(ci, cj);
                ahorros.add(new Object[]{ahorro, ci, cj});
            }
        }
        ahorros.sort((a, b) -> Double.compare((double) b[0], (double) a[0]));

        // Mapa: cliente -> ruta a la que pertenece actualmente
        Map<Cliente, LinkedList<Cliente>> rutaDeCliente = new HashMap<>();
        for (LinkedList<Cliente> r : rutas) {
            rutaDeCliente.put(r.getFirst(), r);
        }

        for (Object[] ah : ahorros) {
            Cliente i = (Cliente) ah[1];
            Cliente j = (Cliente) ah[2];

            LinkedList<Cliente> rutaI = rutaDeCliente.get(i);
            LinkedList<Cliente> rutaJ = rutaDeCliente.get(j);

            if (rutaI == null || rutaJ == null || rutaI == rutaJ) {
                continue; // ya estan en la misma ruta, o ya no son referencias validas
            }

            boolean iEsExtremo = (rutaI.getFirst() == i || rutaI.getLast() == i);
            boolean jEsExtremo = (rutaJ.getFirst() == j || rutaJ.getLast() == j);
            if (!iEsExtremo || !jEsExtremo) {
                continue; // solo se puede fusionar por los extremos de cada ruta
            }

            if (rutaI.size() + rutaJ.size() > capacidad) {
                continue; // respetar la capacidad maxima del camion
            }

            // Fusionar: dejamos a i al final de rutaI y a j al principio de rutaJ
            LinkedList<Cliente> nueva = new LinkedList<>();
            if (rutaI.getLast() != i) {
                Collections.reverse(rutaI);
            }
            if (rutaJ.getFirst() != j) {
                Collections.reverse(rutaJ);
            }
            nueva.addAll(rutaI);
            nueva.addAll(rutaJ);

            for (Cliente c : nueva) {
                rutaDeCliente.put(c, nueva);
            }
        }

        // Recolectar las rutas finales (puede haber quedado mas de una si la capacidad asi lo exigio)
        Set<LinkedList<Cliente>> rutasFinales = new LinkedHashSet<>(rutaDeCliente.values());
        List<List<Cliente>> resultado = new ArrayList<>();
        for (LinkedList<Cliente> r : rutasFinales) {
            resultado.add(new ArrayList<>(r));
        }
        return resultado;
    }

    // =========================================================
    // UTILIDADES DE DISTANCIA
    // =========================================================

    private static double distancia(Cliente a, Cliente b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static double distanciaFabrica(Cliente c) {
        // La fabrica esta en (0,0)
        return Math.sqrt((double) c.getX() * c.getX() + (double) c.getY() * c.getY());
    }

    private static double calcularDistanciaRuta(List<Cliente> ruta) {
        if (ruta.isEmpty()) {
            return 0;
        }
        double total = distanciaFabrica(ruta.get(0)); // fabrica -> primer cliente
        for (int i = 0; i < ruta.size() - 1; i++) {
            total += distancia(ruta.get(i), ruta.get(i + 1));
        }
        total += distanciaFabrica(ruta.get(ruta.size() - 1)); // ultimo cliente -> fabrica
        return total;
    }
}
