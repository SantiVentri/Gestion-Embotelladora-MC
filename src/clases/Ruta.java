package clases;

import java.util.List;

public class Ruta {
    private Dia dia;
    private List<Camion> camiones;

    public Ruta(Dia dia, List<Camion> camiones) {
        this.dia = dia;
        this.camiones = camiones;
    }

    public Dia getDia() {
        return dia;
    }

    public List<Camion> getCamiones() {
        return camiones;
    }

    public double getDistanciaTotal() {
        double total = 0;
        for (Camion c : camiones) {
            total += c.getDistanciaTotal();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ruta del dia ").append(dia).append(":\n");
        for (int i = 0; i < camiones.size(); i++) {
            sb.append("  Camion ").append(i + 1).append(": ").append(camiones.get(i)).append("\n");
        }
        sb.append(String.format("  Distancia total: %.2f km", getDistanciaTotal()));
        return sb.toString();
    }
}
