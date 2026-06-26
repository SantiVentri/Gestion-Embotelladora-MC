package clases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {

    private final double[][] matrizAdyacencia; 
    private final Map<Cliente, Integer> indiceDeCliente; 
    private final List<Cliente> clientes;

    public Grafo(List<Cliente> clientes) {
        this.clientes = clientes;
        int n = clientes.size();

        this.indiceDeCliente = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indiceDeCliente.put(clientes.get(i), i + 1); 
        }

        this.matrizAdyacencia = new double[n + 1][n + 1];
        construirMatriz();
    }

    private void construirMatriz() {
        int n = clientes.size();

        for (int i = 0; i < n; i++) {
            double d = distanciaEuclidiana(0, 0, clientes.get(i).getX(), clientes.get(i).getY());
            matrizAdyacencia[0][i + 1] = d;
            matrizAdyacencia[i + 1][0] = d;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Cliente a = clientes.get(i);
                Cliente b = clientes.get(j);
                double d = distanciaEuclidiana(a.getX(), a.getY(), b.getX(), b.getY());
                matrizAdyacencia[i + 1][j + 1] = d;
                matrizAdyacencia[j + 1][i + 1] = d;
            }
        }
    }

    private double distanciaEuclidiana(int x1, int y1, int x2, int y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double obtenerDistancia(Cliente a, Cliente b) {
        int i = indiceDeCliente.get(a);
        int j = indiceDeCliente.get(b);
        return matrizAdyacencia[i][j];
    }

    public double obtenerDistanciaFabrica(Cliente c) {
        int i = indiceDeCliente.get(c);
        return matrizAdyacencia[0][i];
    }

    public int getCantidadNodos() {
        return matrizAdyacencia.length; // incluye a la fabrica
    }
}
