package main;

import java.util.ArrayList;
import java.util.List;

import clases.*;

import static utils.Utils.pedirOpcion;

public class Main {
	static List<Cliente> clientes = new ArrayList<>();
	static GestorRutas gestorRutas = new GestorRutas(clientes);
	static GestorClientes gestorClientes = new GestorClientes(clientes);

	public static void main(String[] args) {
		gestorClientes.cargarClientes();
		
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
					Rutas.crearRuta(gestorRutas);
					break;
				case 2:
					System.out.println("\n------ MODIFICAR RUTAS ------\n");
					System.out.println("[!] Funcionalidad aun no disponible.");
					break;
				case 3:
					System.out.println("\n------ ELIMINAR RUTAS ------\n");
					Rutas.eliminarRuta(gestorRutas);
					break;
				default:
					System.out.println("\n[!] Opción inválida. Intentá de nuevo.\n");
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
			
			opcion = pedirOpcion();
			
			switch (opcion) {
				case -1:
					// Ir atrás
					break;
				case 1:
					System.out.println("\n------ CREAR CLIENTES ------");
					Clientes.crearCliente(gestorClientes);
					
					break;
				case 2:
					System.out.println("\n------ MODIFICAR CLIENTES ------");
					Clientes.modificarCliente(gestorClientes);
					break;
				case 3:
					System.out.println("\n------ ELIMINAR CLIENTES ------");
					Clientes.eliminarCliente(gestorClientes);
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
					Informes.mostrarInformeRutas(gestorRutas);
					break;
				case 2:
					System.out.println("\n------ INFORME CLIENTES ------");
					Informes.mostrarInformeClientes(gestorClientes);
					break;
				case 3:
					System.out.println("\n------ ESTADÍSTICAS SEMANALES ------");
					Informes.mostrarEstadisticasSemanales(gestorRutas, gestorClientes);
					break;
				default:
					System.out.println("\n[!] Opción inválida. Intentá de nuevo.\n");
			        break;
			}
		}
		
		System.out.println("Volviendo atrás...");
	}
}
