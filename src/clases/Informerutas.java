package clases;

import java.util.Scanner;

public class Informerutas {
    static final double PRECIO_NAFTA_POR_KM = 5000.0; // pesos por km
    static final double PRECIO_POR_UNIDAD   = 7000.0; // ganancia por cliente visitado
	 
    public static void mostrarInformeRutas(GestorRutas gestorRutas, Scanner scanner) {
        System.out.println("\n------ INFORME DE RUTAS POR DIA ------\n");
        System.out.println("Selecciona un dia:");
	 
        Dia[] dias = Dia.values();
        for (int i = 0; i < dias.length; i++) {
            System.out.println((i + 1) + ". " + dias[i]);
        }
        System.out.print("\n>> ");
 
        int opcion = scanner.nextInt();
 
        if (opcion < 1 || opcion > dias.length) {
            System.out.println("Opcion invalida.");
            return;
        }
	 
        Dia dia   = dias[opcion - 1];
        Ruta ruta = gestorRutas.getRuta(dia);	 
	        
        System.out.println("  DIA: " + dia);
        
        if (ruta == null) {
            System.out.println("\n  No hay ruta generada para el " + dia + ".");
            System.out.println("  Primero crea la ruta desde 'Administrar rutas'.");
            return;
        }
	 
        int totalClientes = 0;
        int numeroCamion  = 1;
	 
        for (Camion camion : ruta.getCamiones()) {
            System.out.println("\n  --- Camion " + numeroCamion + " ---");
            System.out.println("  Recorrido: " + camion);
            System.out.println("  Clientes:");
            for (Cliente c : camion.getClientes()) {
                System.out.println("    - " + c);
                totalClientes++;
            }
            double kmCamion    = camion.getDistanciaTotal();
            double costoCamion = kmCamion * PRECIO_NAFTA_POR_KM;
            System.out.printf("  Distancia:     %.2f km%n", kmCamion);
            System.out.printf("  Costo nafta:   $%.2f%n", costoCamion);
            numeroCamion++;
        }
	 
        double kmTotal = ruta.getDistanciaTotal();
        double costoNafta = kmTotal * PRECIO_NAFTA_POR_KM;
        double ingresoTotal = totalClientes * PRECIO_POR_UNIDAD;
        double ganancia = ingresoTotal - costoNafta;
	 
        System.out.println("\n  ====================================");
        System.out.printf("  Distancia total:    %.2f km%n", kmTotal);
        System.out.printf("  Costo total nafta:  $%.2f%n", costoNafta);
        System.out.printf("  Clientes visitados: %d%n", totalClientes);
        System.out.printf("  Ingreso bruto:      $%.2f%n", ingresoTotal);
        System.out.println("  ------------------------------------");
        System.out.printf("  GANANCIA DEL DIA:   $%.2f%n", ganancia);
        System.out.println("======================================\n");
    }
}