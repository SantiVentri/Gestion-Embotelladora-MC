package main;

import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		int opcion = 0;
		
		while (opcion != -1) {
			System.out.println("------ Gestion Embotelladora MC ------\n");
			
			System.out.println("1. Administrar rutas");
			System.out.println("2. Administrar clientes");
			System.out.println("3. Ver informes");
			System.out.println();
			System.out.println("(Ingresá -1 para salir)");
			
			System.out.print("\n>> ");
			opcion = scanner.nextInt();
			
			switch (opcion) {
				case -1:
					// Salir
					break;
				case 1:
					// Administrar rutas
					menuRutas();
					break;
				case 2:
					// Administrar clientes
					menuClientes();
					break;
				case 3:
					// Ver informes
					menuInformes();
					break;
			}
		}
		
		System.out.println("\nCerrando programa...");
	}
	
	public static void menuRutas() {
		int opcion = 0;
		
		while (opcion != -1) {
			System.out.println();
			System.out.println("------ ADMINISTRAR RUTAS ------\n");
			System.out.println("1. Crear rutas");
			System.out.println("2. Modificar rutas");
			System.out.println("3. Eliminar rutas");
			System.out.println();
			System.out.println("(Ingresá -1 para ir atrás)");
			
			System.out.print("\n>> ");
			opcion = scanner.nextInt();
			
			switch (opcion) {
				case -1:
					// Ir atrás
					break;
				case 1:
					System.out.println("\n------ CREAR RUTAS ------\n");
					break;
				case 2:
					System.out.println("\n------ MODIFICAR RUTAS ------\n");
					break;
				case 3:
					System.out.println("\n------ ELIMINAR RUTAS ------\n");
					break;
			}
		}
		
		System.out.println("Volviendo atrás...");
	}
	
	public static void menuClientes() {
		int opcion = 0;
		
		while (opcion != -1) {
			System.out.println("\n------ ADMINISTRAR CLIENTES ------\n");
			
			System.out.println("1. Crear clientes");
			System.out.println("2. Modificar clientes");
			System.out.println("3. Eliminar clientes");
			System.out.println();
			System.out.println("(Ingresá -1 para ir atrás)");
			
			System.out.print("\n>> ");
			opcion = scanner.nextInt();
			
			switch (opcion) {
				case -1:
					// Ir atrás
					break;
				case 1:
					System.out.println("\n------ CREAR CLIENTES ------");
					break;
				case 2:
					System.out.println("\n------ MODIFICAR CLIENTES ------");
					break;
				case 3:
					System.out.println("\n------ ELIMINAR CLIENTES ------");
					break;
			}
		}
		
		System.out.println("Volviendo atrás...");
	}
	
	public static void menuInformes() {
		int opcion = 0;
		
		while (opcion != -1) {
			System.out.println("------ VER INFORMES ------\n");
			
			System.out.println("1. Informe rutas");
			System.out.println("2. Informe clientes");
			System.out.println("3. Ver estadísticas semanales");
			System.out.println();
			System.out.println("(Ingresá -1 para ir atrás)");
			
			System.out.print("\n>> ");
			opcion = scanner.nextInt();
			
			switch (opcion) {
				case -1:
					// Ir atrás
					break;
				case 1:
					System.out.println("\n------ INFORME RUTAS ------");
					break;
				case 2:
					System.out.println("\n------ INFORME CLIENTES ------");
					break;
				case 3:
					System.out.println("\n------ ESTADÍSTICAS SEMANALES ------");
					break;
			}
		}
		
		System.out.println("Volviendo atrás...");
	}
}
