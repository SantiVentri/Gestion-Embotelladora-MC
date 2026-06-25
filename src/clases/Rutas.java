package clases;

import static utils.Utils.pedirDia;

public class Rutas {
	public static void crearRuta(GestorRutas gestorRutas) {
		Dia dia = pedirDia();
		if (dia == null) {
			return;
		}
		String resultado = gestorRutas.crearRuta(dia);
		System.out.println("\n" + resultado);
	}

	public static void eliminarRuta(GestorRutas gestorRutas) {
		Dia dia = pedirDia();
		if (dia == null) {
			return;
		}
		String resultado = gestorRutas.eliminarRuta(dia);
		System.out.println("\n" + resultado);
	}
}
