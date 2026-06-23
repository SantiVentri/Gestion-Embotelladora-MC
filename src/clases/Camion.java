package clases;

import java.util.List;

public class Camion {
    private List<Cliente> clientes; // orden en el que el camion visita a los clientes
    private double distanciaTotal; // distancia total del recorrido (fabrica -> clientes -> fabrica)

    public Camion(List<Cliente> clientes, double distanciaTotal) {
        this.clientes = clientes;
        this.distanciaTotal = distanciaTotal;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Fabrica");
        for (Cliente c : clientes) {
            sb.append(" -> ").append(c.getNombre());
        }
        sb.append(" -> Fabrica");
        sb.append(String.format(" (Distancia: %.2f km)", distanciaTotal));
        return sb.toString();
    }
}
