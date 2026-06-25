package clases;

import static utils.Utils.pedirDia;
import static utils.Utils.pedirEntero;
import static utils.Utils.pedirTexto;

public class Clientes {
	
	public static void crearCliente(GestorClientes gestorClientes) {
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
	
	public static void modificarCliente(GestorClientes gestorClientes) {
		if (gestorClientes.getClientes().isEmpty()) {
			System.out.println("\n[!] No hay clientes cargados todavía.");
			return;
		}

		listarClientes(gestorClientes);

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
	
	public static void eliminarCliente(GestorClientes gestorClientes) {
		if (gestorClientes.getClientes().isEmpty()) {
			System.out.println("\n[!] No hay clientes cargados todavía.");
			return;
		}

		listarClientes(gestorClientes);

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
	
	public static void listarClientes(GestorClientes gestorClientes) {
		System.out.println("\nClientes cargados:");
		for (Cliente c : gestorClientes.getClientes()) {
			System.out.println("- " + c);
		}
	}
}
