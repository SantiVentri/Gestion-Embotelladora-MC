package utils;

import java.util.Scanner;

import clases.Dia;

public class Utils {
	static Scanner scanner = new Scanner(System.in);
	
	public static int pedirOpcion() {
	    while (true) {
	        try {
	            System.out.print("\n>> ");
	            int opcion = scanner.nextInt();
	            scanner.nextLine();
	            return opcion;
	        } catch (Exception e) {
	            System.out.println("\n[!] Error: Por favor ingresá un número válido.");
	            scanner.nextLine();
	        }
	    }
	}
	
	public static String pedirTexto(String mensaje) {
		System.out.println(mensaje);
		System.out.print("\n>> ");
		return scanner.nextLine();
	}
	
	public static int pedirEntero(String mensaje) {
		System.out.println(mensaje);
		return pedirOpcion();
	}
	
	public static Dia pedirDia() {
		Dia[] dias = Dia.values();
		System.out.println("Elegí el dia:");
		for (int i = 0; i < dias.length; i++) {
			System.out.println((i + 1) + ". " + dias[i]);
		}
		System.out.println();
		System.out.println("(Ingresá -1 para cancelar)");

		int opcion = pedirOpcion();
		if (opcion == -1) {
			return null;
		}
		if (opcion < 1 || opcion > dias.length) {
			System.out.println("\n[!] Opción inválida.");
			return null;
		}
		return dias[opcion - 1];
	}
}
