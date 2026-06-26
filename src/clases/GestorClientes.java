package clases;

import java.util.ArrayList;
import java.util.List;

public class GestorClientes {
    private List<Cliente> clientes;

    public GestorClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean crearCliente(Cliente cliente) {
        if (buscarCliente(cliente.getDni()) != null) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public boolean eliminarCliente(int dni) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDni() == dni) {
                clientes.remove(i);
                return true;
            }
        }
        return false;
    }

    public Cliente buscarCliente(int dni) {
        for (Cliente c : clientes) {
            if (c.getDni() == dni) {
                return c;
            }
        }
        return null;
    }

    public boolean modificarCliente(int dni, String nombre, int x, int y, Dia dia, int cantidadProducto) {
        Cliente c = buscarCliente(dni);

        if (c == null) {
            return false;
        }

        c.setNombre(nombre);
        c.setX(x);
        c.setY(y);
        c.setDia(dia);
        c.setCantidadProducto(cantidadProducto);

        return true;
    }
    
    public void cargarClientes() {
    	String[] nombres = {"Juan", "Maria", "Pedro", "Ana", "Luis", "Elena", "Carlos", "Sofia"};
    	String[] apellidos = {"Perez", "Gomez", "Rodriguez", "Fernandez", "Lopez", "Diaz", "Torres", "Ruiz"};
    	
    	for (int i = 0; i < 30; i++) {
    		
    		// Crear un DNI unico entre 30.000.000 y 40.000.000
    		int dni;
    		do {
    			dni = (int) (Math.random() * (40000000 - 30000000 + 1) + 30000000);
    		} while (this.buscarCliente(dni) != null);
    		
    		// Crear un nombre aleatorio
            String nombreAleatorio = nombres[(int) (Math.random() * nombres.length)];
            String apellidoAleatorio = apellidos[(int) (Math.random() * apellidos.length)];
            String nombreCompleto = nombreAleatorio + " " + apellidoAleatorio;
            
            // Crear coordenadas aleatorias
            int x = (int) (Math.random() * 100) + 1;
            int y = (int) (Math.random() * 100) + 1;
            
            // Crear dia aleatorio            
            Dia dia = Dia.values()[(int) (Math.random() * Dia.values().length)];
            
            // Crear cantidad de productos aleatorios
            int cantidadProductos = (int) (Math.random() * 10) + 1;
            
            Cliente nuevoCliente = new Cliente(dni, nombreCompleto, x, y, dia, cantidadProductos);
            this.clientes.add(nuevoCliente);
    		
    	}
    }
}