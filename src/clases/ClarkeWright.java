package clases;

import java.util.*;

public class ClarkeWright {

    private static final int CAPACIDAD_CAMION = 10; 

    public static List<Camion> generarCamiones(List<Cliente> clientes) {
        Grafo grafo = new Grafo(clientes); 

        int cantCamiones = (int) Math.ceil((double) clientes.size() / CAPACIDAD_CAMION);

        int capacidadEfectiva = (cantCamiones <= 1) ? clientes.size() : CAPACIDAD_CAMION;

        List<List<Cliente>> rutasClientes = clarkeWright(clientes, grafo, capacidadEfectiva);

        List<Camion> camiones = new ArrayList<>();
        for (List<Cliente> ruta : rutasClientes) {
            double distancia = calcularDistanciaRuta(ruta, grafo);
            camiones.add(new Camion(ruta, distancia));
        }
        return camiones;
    }

    private static List<List<Cliente>> clarkeWright(List<Cliente> clientes, Grafo grafo, int capacidad) {
        
    	List<LinkedList<Cliente>> rutas = new ArrayList<>();
        for (Cliente c : clientes) {
            LinkedList<Cliente> r = new LinkedList<>();
            r.add(c);
            rutas.add(r);
        }

        List<Object[]> ahorros = new ArrayList<>(); 
        for (int i = 0; i < clientes.size(); i++) {
            for (int j = i + 1; j < clientes.size(); j++) {
                Cliente ci = clientes.get(i);
                Cliente cj = clientes.get(j);
                double ahorro = grafo.obtenerDistanciaFabrica(ci) + grafo.obtenerDistanciaFabrica(cj)
                        - grafo.obtenerDistancia(ci, cj);
                ahorros.add(new Object[]{ahorro, ci, cj});
            }
        }
        ahorros.sort((a, b) -> Double.compare((double) b[0], (double) a[0]));

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
                continue; 
            }

            boolean iEsExtremo = (rutaI.getFirst() == i || rutaI.getLast() == i);
            boolean jEsExtremo = (rutaJ.getFirst() == j || rutaJ.getLast() == j);
            if (!iEsExtremo || !jEsExtremo) {
                continue; 
            }

            if (rutaI.size() + rutaJ.size() > capacidad) {
                continue; 
            }

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

        Set<LinkedList<Cliente>> rutasFinales = new LinkedHashSet<>(rutaDeCliente.values());
        List<List<Cliente>> resultado = new ArrayList<>();
        for (LinkedList<Cliente> r : rutasFinales) {
            resultado.add(new ArrayList<>(r));
        }
        return resultado;
    }

    private static double calcularDistanciaRuta(List<Cliente> ruta, Grafo grafo) {
        if (ruta.isEmpty()) {
            return 0;
        }
        double total = grafo.obtenerDistanciaFabrica(ruta.get(0)); 
        for (int i = 0; i < ruta.size() - 1; i++) {
            total += grafo.obtenerDistancia(ruta.get(i), ruta.get(i + 1));
        }
        total += grafo.obtenerDistanciaFabrica(ruta.get(ruta.size() - 1)); 
        return total;
    }
}

