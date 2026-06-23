package main;

import java.util.List;
import java.util.Scanner;

import clases.Cliente;
import clases.ClientesPrueba;
import clases.Dia;
import clases.GestorRutas;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	static List<Cliente> clientes = ClientesPrueba.generar(); // TODO: reemplazar por el modulo real de clientes
	static GestorRutas gestorRutas = new GestorRutas(clientes);

	public static void main(String[] args) {
		int opcion = 0;
		
		while (opcion != -1) {
			System.out.println("------ Gestion Embotelladora MC ------\n");
			
			System.out.println("1. Administrar rutas");
			System.out.println("2. Administrar clientes");
			System.out.println("3. Ver informes");
			System.out.println();
			System.out.println("(Ingresá -1 para salir)");
			
			opcion = pedirOpcion();
			
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
				default:
			        System.out.println("\n[!] Opción inválida. Intentá de nuevo.\n");
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
			
			opcion = pedirOpcion();
			
			switch (opcion) {
				case -1:
					// Ir atrás
					break;
				case 1:
					System.out.println("\n------ CREAR RUTAS ------\n");
					crearRuta();
					break;
				case 2:
					System.out.println("\n------ MODIFICAR RUTAS ------\n");
					System.out.println("[!] Funcionalidad aun no disponible.");
					break;
				case 3:
					System.out.println("\n------ ELIMINAR RUTAS ------\n");
					eliminarRuta();
					break;
				default:
					System.out.println("\n[!] Opción inválida. Intentá de nuevo.\n");
			        break;
			}
		}
		
		System.out.println("Volviendo atrás...");
	}

	private static void crearRuta() {
		Dia dia = pedirDia();
		if (dia == null) {
			return;
		}
		String resultado = gestorRutas.crearRuta(dia);
		System.out.println("\n" + resultado);
	}

	private static void eliminarRuta() {
		Dia dia = pedirDia();
		if (dia == null) {
			return;
		}
		String resultado = gestorRutas.eliminarRuta(dia);
		System.out.println("\n" + resultado);
	}

	private static Dia pedirDia() {
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
	
	public static void menuClientes() {
		int opcion = 0;
		
		while (opcion != -1) {
			System.out.println("\n------ ADMINISTRAR CLIENTES ------\n");
			
			System.out.println("1. Crear clientes");
			System.out.println("2. Modificar clientes");
			System.out.println("3. Eliminar clientes");
			System.out.println();
			System.out.println("(Ingresá -1 para ir atrás)");
			
			opcion = pedirOpcion();
			
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
				default:
					System.out.println("\n[!] Opción inválida. Intentá de nuevo.\n");
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
			
			opcion = pedirOpcion(); 
			
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
				default:
					System.out.println("\n[!] Opción inválida. Intentá de nuevo.\n");
			        break;
			}
		}
		
		System.out.println("Volviendo atrás...");
	}
	
	private static int pedirOpcion() {
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
}
