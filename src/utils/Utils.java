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
	
	public static int pedirDni() {
	    while (true) {
	        int dni = pedirEntero("\nIngresá el DNI del cliente (30M - 50M) o -1 para cancelar:");

	        if (dni == -1) return -1; // Opción de salida

	        // Validaciones
	        if (String.valueOf(dni).length() == 8 && dni >= 30000000 && dni <= 50000000) {
	            return dni;
	        } else {
	            System.out.println("[!] DNI inválido. Debe tener 8 dígitos y estar entre 30M y 50M.");
	        }
	    }
	}
	
	public static int pedirCantidad() {
	    while (true) {
	        int cantidad = pedirEntero("\nIngresá la cantidad de productos del cliente (1 a 10) o -1 para cancelar:");

	        if (cantidad == -1) return -1; // Opción de salida

	        // Validaciones
	        if (cantidad >= 1 && cantidad <= 10) {
	            return cantidad;
	        } else {
	            System.out.println("[!] Cantidad inválida. Debe estar entre 1 y 10");
	        }
	    }
	}
}
