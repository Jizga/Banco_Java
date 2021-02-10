package cuentas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import exceptions.SaldoInsuficienteException;

@SuppressWarnings("serial")
public class CuentaCorriente implements Serializable {

	private int saldo;
	private String nombreCuenta;
	private String titular;
	private ArrayList<Movimientos> movimientosCuenta;

	// Constructor
	public CuentaCorriente() {
		// Array de movimientos de cada cuenta
		movimientosCuenta = new ArrayList<Movimientos>();
	}

// ------------------------  Método que añade el movimiento al arrayList MovimientosCuenta ---------------------
	public void addMovimiento(Movimientos movimiento) {
		movimientosCuenta.add(movimiento);
	}

	public void ingresarDinero(int dinero, String concepto, Date fecha, String tipoOperacion) {

		Movimientos ingresar = new Movimientos();
		ingresar.setImporte(dinero);
		ingresar.setConcepto(concepto);
		ingresar.setFecha(fecha);
		ingresar.setTipoOperacion(tipoOperacion);

		addMovimiento(ingresar);
	}

	public void sacarDinero(int dinero, String concepto, Date fecha, String tipoOperacion)
			throws SaldoInsuficienteException {

		if (this.getSaldo() >= dinero) {

			Movimientos sacar = new Movimientos();
			sacar.setImporte(-dinero);
			sacar.setConcepto(concepto);
			sacar.setFecha(fecha);
			sacar.setTipoOperacion(tipoOperacion);

			addMovimiento(sacar);

		} else {
			throw new SaldoInsuficienteException(" --- SALDO INSUFICIENTE --- ");
		}
	}

	public void transferirDinero(int dineroTransferido, CuentaCorriente cuentaDestino, String concepto, Date fecha,
			String tipoOperacion) throws SaldoInsuficienteException {

		if (dineroTransferido <= this.getSaldo()) {

			Movimientos salida = new Movimientos();
			salida.setConcepto(concepto);
			salida.setFecha(fecha);
			salida.setImporte(-dineroTransferido);
			salida.setTipoOperacion(tipoOperacion);

			addMovimiento(salida);

			Movimientos entrada = new Movimientos();
			entrada.setConcepto(concepto);
			entrada.setFecha(fecha);
			entrada.setImporte(dineroTransferido);
			entrada.setTipoOperacion(tipoOperacion);

			// pq es una entrada para la cuenta destino
			cuentaDestino.addMovimiento(entrada);

		} else {
			throw new SaldoInsuficienteException(" --- SALDO INSUFICIENTE --- ");
		}
	}

// ---------- Método que selecciona la CuentaCorriente con la que quiero operar por nombre de cuenta  -------------------

	public static CuentaCorriente cuentaSeleccionada(ArrayList<CuentaCorriente> listaCuentas,
			String cuentaSeleccionadaMenu) {
		CuentaCorriente cuenta = null;

		for (CuentaCorriente cuentaSeleccionada : listaCuentas) {
			if (cuentaSeleccionada.getNombreCuenta().equalsIgnoreCase(cuentaSeleccionadaMenu)) {
				cuenta = cuentaSeleccionada;
			}
		}

		return cuenta;
	}

	// Getters y Setters

	public int getSaldo() {

		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	public String getNombreCuenta() {
		return nombreCuenta;
	}

	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	// *********

	public ArrayList<Movimientos> getMovimientosCuenta() {
		return movimientosCuenta;
	}

	public void setMovimientosCuenta(ArrayList<Movimientos> movimientosCuenta) {
		this.movimientosCuenta = movimientosCuenta;
	}

}
