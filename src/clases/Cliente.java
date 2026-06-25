package clases;

public class Cliente {
	private final int dni;
	private String nombre;
	private int x;
	private int y;
	private Dia dia;
	private int cantidadProducto;
	
	public Cliente(int dni, String nombre, int x, int y, Dia dia, int cantidadProducto) {
		this.dni = dni;
		this.nombre = nombre;

		// Coordenadas
		this.x = x;
		this.y = y;

		this.dia = dia;
		this.cantidadProducto = cantidadProducto;
		
	}
	
	public int getDni() {
		return dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}
	
	public int getCantidadProducto() {
		return cantidadProducto;
	}
	public void setCantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}
	
	@Override
	public String toString() {
		return "DNI: " + dni + " | Nombre: " + nombre
				+ " | Coordenadas: (" + x + ", " + y + ")"
				+ " | Dia: " + dia
				+ " | Cantidad de producto: " + cantidadProducto;
	}
}
