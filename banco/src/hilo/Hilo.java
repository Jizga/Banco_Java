package hilo;

import java.util.concurrent.TimeUnit;

public class Hilo extends Thread {

	private String nombre;

	public Hilo(String nombre) {
		// super se usa para llamar a la versión del constructor de Thread
		super(nombre);
	}

	// Punto de entrada del hilo
	public void run() {

		try {
			for (int cont = 0; cont < 10; cont++) {

				//TimeUnit.MILLISECONDS.sleep(5000);
				Thread.sleep(5000);
			}
		} catch (InterruptedException exc) {

		}

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
