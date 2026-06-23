package clases;
import java.util.*;

/**
 * Programa de testeo de algoritmos de ruteo (TSP / VRP) para la embotelladora.
 * Compara: Backtracking, Vecino mas cercano + 2-opt (con agrupamiento Sweep), y Clarke-Wright (Savings).
 *
 * El nodo 0 es siempre la fabrica, ubicada en (0,0).
 * Los nodos 1..n son los clientes generados aleatoriamente.
 *
 * Si se ingresa una capacidad por camion menor a la cantidad de clientes, el problema
 * pasa a ser de varios camiones (VRP): Clarke-Wright respeta la capacidad al fusionar rutas,
 * y Vecino+2opt usa el algoritmo de Sweep (barrido angular) para agrupar antes de rutear.
 */
public class RouteTest {

    static double[][] dist; // matriz de distancias (n+1) x (n+1), incluye fabrica en indice 0
    static int n; // cantidad de clientes (sin contar la fabrica)
    static long semillaGlobal; // se reutiliza para que el clustering tambien sea reproducible
    static final int LIMITE_BACKTRACKING = 11; // mas que esto, el tiempo se vuelve poco practico

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Cantidad de clientes a generar: ");
        n = sc.nextInt();

        System.out.print("Semilla aleatoria (numero entero, o -1 para aleatoria real): ");
        long semilla = sc.nextLong();
        semillaGlobal = semilla;

        System.out.print("Capacidad maxima de clientes por camion (0, o un numero >= cantidad de clientes, para 1 solo camion con capacidad infinita): ");
        int capacidadInput = sc.nextInt();
        int capacidad = (capacidadInput <= 0 || capacidadInput >= n) ? n : capacidadInput;
        boolean unCamion = (capacidad >= n);

        double[][] coords = generarClientes(n, semilla);
        dist = calcularMatrizDistancias(coords);

        System.out.println("\n--- Clientes generados (id: x, y) ---");
        System.out.printf("Fabrica: (0.00, 0.00)%n");
        for (int i = 1; i <= n; i++) {
            System.out.printf("Cliente %d: (%.2f, %.2f)%n", i, coords[i][0], coords[i][1]);
        }

        System.out.println("\n========== RESULTADOS ==========");
        if (unCamion) {
            System.out.println("(Modo: 1 solo camion, capacidad infinita)");
        } else {
            System.out.println("(Modo: varios camiones, capacidad maxima por camion = " + capacidad + ")");
        }

        ejecutarBacktracking(coords, capacidad, unCamion);
        ejecutarVecinoCon2optYsweep(coords, capacidad, unCamion);
        ejecutarClarkeWrightCapacidad(capacidad, unCamion);

        sc.close();
    }

    // =========================================================
    // GENERACION DE CLIENTES Y MATRIZ DE DISTANCIAS
    // =========================================================

    static double[][] generarClientes(int n, long semilla) {
        Random rnd = (semilla == -1) ? new Random() : new Random(semilla);
        double[][] coords = new double[n + 1][2];
        coords[0][0] = 0.0;
        coords[0][1] = 0.0;
        for (int i = 1; i <= n; i++) {
            coords[i][0] = rnd.nextDouble() * 100;
            coords[i][1] = rnd.nextDouble() * 100;
        }
        return coords;
    }

    static double[][] calcularMatrizDistancias(double[][] coords) {
        int tam = coords.length;
        double[][] d = new double[tam][tam];
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                if (i == j) {
                    d[i][j] = 0;
                } else {
                    double dx = coords[i][0] - coords[j][0];
                    double dy = coords[i][1] - coords[j][1];
                    d[i][j] = Math.sqrt(dx * dx + dy * dy);
                }
            }
        }
        return d;
    }

    // =========================================================
    // AGRUPAMIENTO POR CLUSTERING REAL (K-MEANS + BALANCEO DE CAPACIDAD)
    // =========================================================

    /**
     * Agrupa a los clientes en grupos de hasta "capacidad" miembros, usando clustering
     * real por cercania geografica (k-means), y balanceando despues los grupos que
     * hayan quedado con mas clientes de los permitidos.
     */
    static List<List<Integer>> clusteringCapacitado(double[][] coords, int capacidad) {
        int k = (int) Math.ceil((double) n / capacidad);
        Random rnd = (semillaGlobal == -1) ? new Random() : new Random(semillaGlobal + 999);

        // 1) Inicializar centroides eligiendo k clientes al azar (sin repetir)
        List<Integer> idsDisponibles = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            idsDisponibles.add(i);
        }
        Collections.shuffle(idsDisponibles, rnd);

        double[][] centroides = new double[k][2];
        for (int c = 0; c < k; c++) {
            int id = idsDisponibles.get(c % idsDisponibles.size());
            centroides[c][0] = coords[id][0];
            centroides[c][1] = coords[id][1];
        }

        // 2) Iterar k-means: asignar cada cliente al centroide mas cercano, recalcular centroides
        List<List<Integer>> clusters = null;
        for (int iter = 0; iter < 100; iter++) {
            clusters = new ArrayList<>();
            for (int c = 0; c < k; c++) {
                clusters.add(new ArrayList<>());
            }
            for (int i = 1; i <= n; i++) {
                int mejorC = 0;
                double mejorD = Double.MAX_VALUE;
                for (int c = 0; c < k; c++) {
                    double dx = coords[i][0] - centroides[c][0];
                    double dy = coords[i][1] - centroides[c][1];
                    double dd = dx * dx + dy * dy;
                    if (dd < mejorD) {
                        mejorD = dd;
                        mejorC = c;
                    }
                }
                clusters.get(mejorC).add(i);
            }

            boolean cambio = false;
            for (int c = 0; c < k; c++) {
                List<Integer> miembros = clusters.get(c);
                if (miembros.isEmpty()) {
                    continue;
                }
                double sx = 0, sy = 0;
                for (int id : miembros) {
                    sx += coords[id][0];
                    sy += coords[id][1];
                }
                double nx = sx / miembros.size();
                double ny = sy / miembros.size();
                if (Math.abs(nx - centroides[c][0]) > 1e-6 || Math.abs(ny - centroides[c][1]) > 1e-6) {
                    cambio = true;
                }
                centroides[c][0] = nx;
                centroides[c][1] = ny;
            }
            if (!cambio) {
                break; // convergio
            }
        }

        // 3) Balancear capacidad: mover el cliente mas alejado del centroide hacia
        //    el cluster mas cercano que todavia tenga lugar, hasta que todos respeten el limite.
        balancearCapacidad(clusters, centroides, coords, capacidad);

        clusters.removeIf(List::isEmpty);
        return clusters;
    }

    static void balancearCapacidad(List<List<Integer>> clusters, double[][] centroides, double[][] coords, int capacidad) {
        boolean cambios = true;
        int seguridad = 0;
        while (cambios && seguridad < 1000) {
            cambios = false;
            seguridad++;
            for (int c = 0; c < clusters.size(); c++) {
                List<Integer> cluster = clusters.get(c);
                while (cluster.size() > capacidad) {
                    // buscar el cliente mas lejano al centroide de este cluster
                    int peor = -1;
                    double peorD = -1;
                    for (int id : cluster) {
                        double dx = coords[id][0] - centroides[c][0];
                        double dy = coords[id][1] - centroides[c][1];
                        double dd = dx * dx + dy * dy;
                        if (dd > peorD) {
                            peorD = dd;
                            peor = id;
                        }
                    }
                    // buscar el cluster mas cercano que todavia tenga espacio
                    int destino = -1;
                    double destD = Double.MAX_VALUE;
                    for (int c2 = 0; c2 < clusters.size(); c2++) {
                        if (c2 == c || clusters.get(c2).size() >= capacidad) {
                            continue;
                        }
                        double dx = coords[peor][0] - centroides[c2][0];
                        double dy = coords[peor][1] - centroides[c2][1];
                        double dd = dx * dx + dy * dy;
                        if (dd < destD) {
                            destD = dd;
                            destino = c2;
                        }
                    }
                    if (destino == -1) {
                        break; // no deberia pasar, pero por seguridad
                    }
                    cluster.remove(Integer.valueOf(peor));
                    clusters.get(destino).add(peor);
                    cambios = true;
                }
            }
        }
    }

    static List<List<Integer>> grupoUnico() {
        List<Integer> todos = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            todos.add(i);
        }
        List<List<Integer>> grupos = new ArrayList<>();
        grupos.add(todos);
        return grupos;
    }

    // =========================================================
    // 1) BACKTRACKING (solucion optima exacta), por grupo/camion
    // =========================================================

    static double mejorDistBT;
    static int[] mejorRutaBT;

    static void ejecutarBacktracking(double[][] coords, int capacidad, boolean unCamion) {
        System.out.println("\n--- Backtracking (optimo exacto) ---");

        List<List<Integer>> grupos = unCamion ? grupoUnico() : clusteringCapacitado(coords, capacidad);

        int maxGrupo = grupos.stream().mapToInt(List::size).max().orElse(0);
        if (maxGrupo > LIMITE_BACKTRACKING) {
            System.out.println("Omitido: al menos un camion tendria " + maxGrupo
                    + " clientes, superando el limite practico de " + LIMITE_BACKTRACKING
                    + " para backtracking (el tiempo crece factorialmente).");
            return;
        }

        double distTotal = 0;
        long tiempoTotal = 0;
        for (int idx = 0; idx < grupos.size(); idx++) {
            List<Integer> grupo = grupos.get(idx);
            long inicio = System.nanoTime();
            int[] ruta = backtrackGrupo(grupo);
            long fin = System.nanoTime();

            distTotal += mejorDistBT;
            tiempoTotal += (fin - inicio);
            mostrarCamion(idx + 1, ruta, mejorDistBT, grupos.size());
        }
        mostrarTotales(distTotal, tiempoTotal, grupos.size());
    }

    static int[] backtrackGrupo(List<Integer> clientes) {
        int m = clientes.size();
        mejorDistBT = Double.MAX_VALUE;
        mejorRutaBT = null;
        int[] ruta = new int[m + 1];
        boolean[] usado = new boolean[n + 1];
        ruta[0] = 0;
        backtrackRec(ruta, usado, 1, 0.0, clientes);
        return mejorRutaBT;
    }

    static void backtrackRec(int[] ruta, boolean[] usado, int pos, double distAcumulada, List<Integer> clientes) {
        int m = clientes.size();
        if (distAcumulada >= mejorDistBT) {
            return;
        }
        if (pos == m + 1) {
            double total = distAcumulada + dist[ruta[m]][0];
            if (total < mejorDistBT) {
                mejorDistBT = total;
                mejorRutaBT = ruta.clone();
            }
            return;
        }
        for (int candidato : clientes) {
            if (!usado[candidato]) {
                usado[candidato] = true;
                ruta[pos] = candidato;
                double nuevaDist = distAcumulada + dist[ruta[pos - 1]][candidato];
                backtrackRec(ruta, usado, pos + 1, nuevaDist, clientes);
                usado[candidato] = false;
            }
        }
    }

    // =========================================================
    // 2) VECINO MAS CERCANO + 2-OPT (con agrupamiento Sweep si hay varios camiones)
    // =========================================================

    static void ejecutarVecinoCon2optYsweep(double[][] coords, int capacidad, boolean unCamion) {
        System.out.println("\n--- Vecino mas cercano + 2-opt" + (unCamion ? "" : " (con clustering K-means)") + " ---");

        long inicioTotal = System.nanoTime(); // incluye el tiempo del clustering si aplica

        List<List<Integer>> grupos = unCamion ? grupoUnico() : clusteringCapacitado(coords, capacidad);

        double distTotal = 0;
        List<int[]> rutas = new ArrayList<>();
        for (List<Integer> grupo : grupos) {
            int[] ruta = vecinoMasCercanoGrupo(grupo);
            ruta = mejorar2opt(ruta);
            rutas.add(ruta);
            distTotal += calcularDistanciaRuta(ruta);
        }

        long finTotal = System.nanoTime(); // tiempo total: sweep + NN + 2opt de todos los camiones

        for (int idx = 0; idx < rutas.size(); idx++) {
            mostrarCamion(idx + 1, rutas.get(idx), calcularDistanciaRuta(rutas.get(idx)), grupos.size());
        }
        mostrarTotales(distTotal, finTotal - inicioTotal, grupos.size());
    }

    static int[] vecinoMasCercanoGrupo(List<Integer> clientes) {
        int m = clientes.size();
        int[] ruta = new int[m + 1];
        ruta[0] = 0;
        Set<Integer> restantes = new HashSet<>(clientes);

        int actual = 0;
        for (int pos = 1; pos <= m; pos++) {
            int masCercano = -1;
            double mejor = Double.MAX_VALUE;
            for (int cand : restantes) {
                if (dist[actual][cand] < mejor) {
                    mejor = dist[actual][cand];
                    masCercano = cand;
                }
            }
            ruta[pos] = masCercano;
            restantes.remove(masCercano);
            actual = masCercano;
        }
        return ruta;
    }

    static int[] mejorar2opt(int[] rutaInicial) {
        int m = rutaInicial.length - 1;
        int[] ruta = rutaInicial.clone();
        boolean mejoro = true;

        while (mejoro) {
            mejoro = false;
            for (int i = 1; i < m; i++) {
                for (int j = i + 1; j <= m; j++) {
                    int[] nuevaRuta = swap2opt(ruta, i, j);
                    if (calcularDistanciaRuta(nuevaRuta) < calcularDistanciaRuta(ruta)) {
                        ruta = nuevaRuta;
                        mejoro = true;
                    }
                }
            }
        }
        return ruta;
    }

    static int[] swap2opt(int[] ruta, int i, int j) {
        int[] nueva = ruta.clone();
        while (i < j) {
            int tmp = nueva[i];
            nueva[i] = nueva[j];
            nueva[j] = tmp;
            i++;
            j--;
        }
        return nueva;
    }

    // =========================================================
    // 3) CLARKE-WRIGHT (SAVINGS) con restriccion de capacidad
    // =========================================================

    static void ejecutarClarkeWrightCapacidad(int capacidad, boolean unCamion) {
        System.out.println("\n--- Clarke-Wright (Savings)" + (unCamion ? "" : " con capacidad maxima " + capacidad) + " ---");

        long inicio = System.nanoTime();
        List<List<Integer>> rutasClientes = clarkeWrightCapacidad(capacidad);
        long fin = System.nanoTime();

        double distTotal = 0;
        List<int[]> rutas = new ArrayList<>();
        for (List<Integer> grupo : rutasClientes) {
            int[] ruta = new int[grupo.size() + 1];
            ruta[0] = 0;
            for (int k = 0; k < grupo.size(); k++) {
                ruta[k + 1] = grupo.get(k);
            }
            rutas.add(ruta);
            distTotal += calcularDistanciaRuta(ruta);
        }

        for (int idx = 0; idx < rutas.size(); idx++) {
            mostrarCamion(idx + 1, rutas.get(idx), calcularDistanciaRuta(rutas.get(idx)), rutas.size());
        }
        mostrarTotales(distTotal, fin - inicio, rutas.size());
    }

    /**
     * Clarke-Wright con restriccion de capacidad: no fusiona dos rutas si la
     * suma de clientes de ambas supera la capacidad maxima de un camion.
     */
    static List<List<Integer>> clarkeWrightCapacidad(int capacidad) {
        List<LinkedList<Integer>> rutas = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            LinkedList<Integer> r = new LinkedList<>();
            r.add(i);
            rutas.add(r);
        }

        List<double[]> ahorros = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                double s = dist[0][i] + dist[0][j] - dist[i][j];
                ahorros.add(new double[]{s, i, j});
            }
        }
        ahorros.sort((a, b) -> Double.compare(b[0], a[0]));

        Map<Integer, LinkedList<Integer>> rutaDeCliente = new HashMap<>();
        for (LinkedList<Integer> r : rutas) {
            rutaDeCliente.put(r.getFirst(), r);
        }

        for (double[] ah : ahorros) {
            int i = (int) ah[1];
            int j = (int) ah[2];

            LinkedList<Integer> rutaI = rutaDeCliente.get(i);
            LinkedList<Integer> rutaJ = rutaDeCliente.get(j);

            if (rutaI == null || rutaJ == null || rutaI == rutaJ) {
                continue;
            }

            boolean iEsExtremo = (rutaI.getFirst() == i || rutaI.getLast() == i);
            boolean jEsExtremo = (rutaJ.getFirst() == j || rutaJ.getLast() == j);
            if (!iEsExtremo || !jEsExtremo) {
                continue;
            }

            // Restriccion de capacidad: no fusionar si se supera el limite por camion
            if (rutaI.size() + rutaJ.size() > capacidad) {
                continue;
            }

            LinkedList<Integer> nueva = new LinkedList<>();
            if (rutaI.getLast() != i) {
                Collections.reverse(rutaI);
            }
            if (rutaJ.getFirst() != j) {
                Collections.reverse(rutaJ);
            }
            nueva.addAll(rutaI);
            nueva.addAll(rutaJ);

            for (int c : nueva) {
                rutaDeCliente.put(c, nueva);
            }
        }

        Set<LinkedList<Integer>> rutasFinales = new LinkedHashSet<>(rutaDeCliente.values());
        List<List<Integer>> resultado = new ArrayList<>();
        for (LinkedList<Integer> r : rutasFinales) {
            resultado.add(new ArrayList<>(r));
        }
        return resultado;
    }

    // =========================================================
    // UTILIDADES COMUNES
    // =========================================================

    static double calcularDistanciaRuta(int[] ruta) {
        double total = 0;
        for (int i = 0; i < ruta.length - 1; i++) {
            total += dist[ruta[i]][ruta[i + 1]];
        }
        total += dist[ruta[ruta.length - 1]][0];
        return total;
    }

    static void mostrarCamion(int numCamion, int[] ruta, double distancia, int totalCamiones) {
        String etiqueta = totalCamiones > 1 ? "Camion " + numCamion + ": " : "Ruta: ";
        StringBuilder sb = new StringBuilder(etiqueta + "Fabrica");
        for (int i = 1; i < ruta.length; i++) {
            sb.append(" -> C").append(ruta[i]);
        }
        sb.append(" -> Fabrica");
        System.out.println(sb.toString());
        System.out.printf("   Distancia: %.2f km%n", distancia);
    }

    static void mostrarTotales(double distTotal, long tiempoNanos, int cantCamiones) {
        String prefijo = cantCamiones > 1 ? "Total (" + cantCamiones + " camiones): " : "Total: ";
        System.out.printf(prefijo + "%.2f km%n", distTotal);
        System.out.printf("Tiempo total de calculo: %.3f ms (%d ns)%n", tiempoNanos / 1_000_000.0, tiempoNanos);
    }
}