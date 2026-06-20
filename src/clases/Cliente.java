package clases;

public class Cliente {
	private final int dni;
	private String nombre;
	private int x;
	private int y;
	
	public Cliente(int dni, String nombre, int x, int y) {
		this.dni = dni;
		this.nombre = nombre;

		// Coordenadas
		this.x = x;
		this.y = y;
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
}
