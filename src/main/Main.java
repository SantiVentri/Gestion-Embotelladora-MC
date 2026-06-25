package main;

import java.util.ArrayList;
import java.util.List;

import clases.Cliente;
import clases.Dia;
import clases.GestorClientes;
import clases.GestorRutas;

import static utils.Utils.pedirOpcion;
import static utils.Utils.pedirTexto;
import static utils.Utils.pedirEntero;

public class Main {
	static List<Cliente> clientes = new ArrayList<>();
	static GestorRutas gestorRutas = new GestorRutas(clientes);
	static GestorClientes gestorClientes = new GestorClientes(clientes);

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
					crearCliente();
					
					break;
				case 2:
					System.out.println("\n------ MODIFICAR CLIENTES ------");
					modificarCliente();
					break;
				case 3:
					System.out.println("\n------ ELIMINAR CLIENTES ------");
					eliminarCliente();
					break;
				default:
					System.out.println("\n[!] Opción inválida. Intentá de nuevo.\n");
			        break;
			}
		}
		
		System.out.println("Volviendo atrás...");
	}
	private static void crearCliente() {
		int dni = pedirEntero("\nIngresá el DNI del cliente:");

		if (gestorClientes.buscarCliente(dni) != null) {
			System.out.println("\n[!] Ya existe un cliente cargado con ese DNI.");
			return;
		}

		String nombre = pedirTexto("Ingresá el nombre del cliente:");
		int x = pedirEntero("Ingresá la coordenada X:");
		int y = pedirEntero("Ingresá la coordenada Y:");

		Dia dia = pedirDia();
		if (dia == null) {
			System.out.println("\n[!] Creación cancelada.");
			return;
		}

		Cliente cliente = new Cliente(dni, nombre, x, y, dia);
		gestorClientes.crearCliente(cliente);

		System.out.println("\nCliente creado con éxito:");
		System.out.println(cliente);
	}
	
	private static void modificarCliente() {
		if (gestorClientes.getClientes().isEmpty()) {
			System.out.println("\n[!] No hay clientes cargados todavía.");
			return;
		}

		listarClientes();

		int dni = pedirEntero("\nIngresá el DNI del cliente a modificar (-1 para cancelar):");
		if (dni == -1) {
			return;
		}

		Cliente actual = gestorClientes.buscarCliente(dni);
		if (actual == null) {
			System.out.println("\n[!] No existe un cliente con ese DNI.");
			return;
		}

		System.out.println("\nDatos actuales -> " + actual);

		String nombre = pedirTexto("Nuevo nombre:");
		int x = pedirEntero("Nueva coordenada X:");
		int y = pedirEntero("Nueva coordenada Y:");

		Dia dia = pedirDia();
		if (dia == null) {
			System.out.println("\n[!] Modificación cancelada.");
			return;
		}

		gestorClientes.modificarCliente(dni, nombre, x, y, dia);
		System.out.println("\nCliente modificado con éxito:");
		System.out.println(gestorClientes.buscarCliente(dni));
	}
	
	private static void eliminarCliente() {
		if (gestorClientes.getClientes().isEmpty()) {
			System.out.println("\n[!] No hay clientes cargados todavía.");
			return;
		}

		listarClientes();

		int dni = pedirEntero("\nIngresá el DNI del cliente a eliminar (-1 para cancelar):");
		if (dni == -1) {
			return;
		}

		boolean eliminado = gestorClientes.eliminarCliente(dni);
		if (eliminado) {
			System.out.println("\nCliente eliminado con éxito.");
		} else {
			System.out.println("\n[!] No existe un cliente con ese DNI.");
		}
	}
	
	private static void listarClientes() {
		System.out.println("\nClientes cargados:");
		for (Cliente c : gestorClientes.getClientes()) {
			System.out.println("- " + c);
		}
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
}
