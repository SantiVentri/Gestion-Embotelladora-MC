package clases;

import java.util.ArrayList;
import java.util.List;

public class GestorClientes {

    private List<Cliente> clientes;

    public GestorClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    /**
     * Crea un cliente nuevo, siempre y cuando no exista ya
     * otro cliente cargado con el mismo DNI.
     *
     * @return true si se creo con exito, false si ya existia ese DNI.
     */
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

    public boolean modificarCliente(int dni, String nombre,
                                    int x, int y, Dia dia) {

        Cliente c = buscarCliente(dni);

        if (c == null) {
            return false;
        }

        c.setNombre(nombre);
        c.setX(x);
        c.setY(y);
        c.setDia(dia);

        return true;
    }
}