package clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * CLASE TEMPORAL DE PRUEBA.
 * Genera clientes de ejemplo (25 por dia de la semana) con coordenadas random,
 * solo para poder probar la funcionalidad de "Administrar rutas" mientras el
 * modulo real de clientes todavia no esta implementado.
 *
 * Borrar esta clase y reemplazar por el modulo real de clientes cuando este listo.
 */
public class ClientesPrueba {

    private static final int CLIENTES_POR_DIA = 25;
    private static final long SEMILLA = 42; // fija para que los datos de prueba sean siempre los mismos

    public static List<Cliente> generar() {
        List<Cliente> clientes = new ArrayList<>();
        Random rnd = new Random(SEMILLA);

        int dniBase = 30000000;
        int contador = 0;

        for (Dia dia : Dia.values()) {
            for (int i = 0; i < CLIENTES_POR_DIA; i++) {
                int dni = dniBase + contador;
                String nombre = "Cliente_" + dia.name().substring(0, 3) + "_" + (i + 1);
                int x = rnd.nextInt(100); // coordenadas entre 0 y 99
                int y = rnd.nextInt(100);

                clientes.add(new Cliente(dni, nombre, x, y, dia));
                contador++;
            }
        }

        return clientes;
    }
}
