package cuentas;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Movimientos implements Serializable{

	private int importe;
	private String concepto;
	private Date fecha;
	private String tipoOperacion;


	// Getters y Setters
	public int getImporte() {
		return importe;
	}

	public void setImporte(int importe) {
		this.importe = importe;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

}
