package banco;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import cuentas.CuentaCorriente;
import cuentas.Movimientos;
import exceptions.SaldoInsuficienteException;
import hilo.Hilo;
import utilidades.Utilidades;




public class Banco {

	public static void main(String[] args) {
		
		// ------------ CREACIÓN DE CUENTAS --------------

		CuentaCorriente cuenta1 = new CuentaCorriente();
		cuenta1.setNombreCuenta("Cuenta1");
		cuenta1.setSaldo(1000);
		cuenta1.setTitular("Maria Sanchez");

		CuentaCorriente cuenta2 = new CuentaCorriente();
		cuenta2.setNombreCuenta("Cuenta2");
		cuenta2.setSaldo(1000);
		cuenta2.setTitular("Raul Gomez");

		// ------ Array de cuentas existentes --------

		ArrayList<CuentaCorriente> listaCuentas = new ArrayList<CuentaCorriente>();
		listaCuentas.add(cuenta1);
		listaCuentas.add(cuenta2);
		
		
		// ------- OPERACIONES DENTRO DE SWITCH ----------

		while (true) {

			Banco.pintarMenu();

			String opcion = Utilidades.getTeclado();

			switch (opcion) {

			case "1":

				System.out.println("Introduce el nombre de tu cuenta");
				// -------------- Seleccionar cuenta mediante nombreCuenta -------

				String elegirCuentaMenu1 = Utilidades.getTeclado();
				CuentaCorriente cuentaSeleccionadaIngreso = CuentaCorriente.cuentaSeleccionada(listaCuentas,
						elegirCuentaMenu1);

				System.out.println("Ingresa una cantidad a" + " " + cuentaSeleccionadaIngreso.getNombreCuenta());
				int importe1 = Integer.parseInt(Utilidades.getTeclado());

				System.out.println("Concepto" + " " + cuentaSeleccionadaIngreso.getNombreCuenta());
				String concepto1 = Utilidades.getTeclado();

				cuentaSeleccionadaIngreso.ingresarDinero(importe1, concepto1, new Date(), "Ingreso");

				// Para actualizar el saldo tras el movimiento

				int saldo1 = cuentaSeleccionadaIngreso.getSaldo();

				ArrayList<Movimientos> guardarMovimientoIngreso = cuentaSeleccionadaIngreso.getMovimientosCuenta();

				// Añadir al saldo solo el último movimiento, y que no repita mov pasados
				saldo1 += ((Movimientos) guardarMovimientoIngreso.get(guardarMovimientoIngreso.size() - 1))
						.getImporte();

				// Actualización saldo tras el movimiento ****
				cuentaSeleccionadaIngreso.setSaldo(saldo1);
				cuentaSeleccionadaIngreso.setMovimientosCuenta(guardarMovimientoIngreso);

				System.out.println(" --- INGRESO REALIZADO --- ");

				break;

			case "2":

				System.out.println("Introduce el nombre de tu cuenta");
				// -------------- Seleccionar cuenta mediante nombreCuenta -------

				String elegirCuentaMenu2 = Utilidades.getTeclado();
				CuentaCorriente cuentaSeleccionadaRetirada = CuentaCorriente.cuentaSeleccionada(listaCuentas,
						elegirCuentaMenu2);

				System.out.println("Cantidad a retirar de" + " " + cuentaSeleccionadaRetirada.getNombreCuenta());
				int importe2 = Integer.parseInt(Utilidades.getTeclado());
				System.out.println("Concepto" + " " + cuentaSeleccionadaRetirada.getNombreCuenta());
				String concepto2 = Utilidades.getTeclado();

				try {
					cuentaSeleccionadaRetirada.sacarDinero(importe2, concepto2, new Date(), "Retirada");

					// Para actualizar el saldo tras el movimiento
					int saldo2 = cuentaSeleccionadaRetirada.getSaldo();

					ArrayList<Movimientos> guardarMovimientoSacar = cuentaSeleccionadaRetirada.getMovimientosCuenta();

					// Añadir al saldo solo el último movimiento, y que no repita mov pasados
					saldo2 += ((Movimientos) guardarMovimientoSacar.get(guardarMovimientoSacar.size() - 1))
							.getImporte();

					// Actualización saldo tras el movimiento ****
					cuentaSeleccionadaRetirada.setSaldo(saldo2);
					cuentaSeleccionadaRetirada.setMovimientosCuenta(guardarMovimientoSacar);

					System.out.println(" --- RETIRADA REALIZADA --- ");

				} catch (SaldoInsuficienteException e) {
					System.err.println(e.getMessage());
				}

				break;

			case "3":

				System.out.println("Introduce la cuenta emisora");
				// -------------- Seleccionar cuenta mediante nombreCuenta -------

				String elegirCuentaMenu3_1 = Utilidades.getTeclado();
				CuentaCorriente cuentaSeleccionadaTransferenciaSalida = CuentaCorriente.cuentaSeleccionada(listaCuentas,
						elegirCuentaMenu3_1);

				System.out.println("Cantidad a transferir desde la" + " "
						+ cuentaSeleccionadaTransferenciaSalida.getNombreCuenta());
				int importe3 = Integer.parseInt(Utilidades.getTeclado());

				System.out.println("Concepto" + " " + cuentaSeleccionadaTransferenciaSalida.getNombreCuenta());
				String concepto3 = Utilidades.getTeclado();

				System.out.println("Introduce la cuenta de destino");
				// -------------- Seleccionar cuenta mediante nombreCuenta -------

				String elegirCuentaMenu3_2 = Utilidades.getTeclado();
				CuentaCorriente cuentaSeleccionadaTransferenciaEntrada = CuentaCorriente
						.cuentaSeleccionada(listaCuentas, elegirCuentaMenu3_2);

				try {

					cuentaSeleccionadaTransferenciaSalida.transferirDinero(importe3,
							cuentaSeleccionadaTransferenciaEntrada, concepto3, new Date(), "Transferencia");

					// Para actualizar el saldo tras el movimiento salida en cuenta1
					int saldo3 = cuentaSeleccionadaTransferenciaSalida.getSaldo();

					ArrayList<Movimientos> guardarMovTransferirSalida = cuentaSeleccionadaTransferenciaSalida
							.getMovimientosCuenta();

					// Añadir al saldo solo el último movimiento, y que no repita mov pasados
					saldo3 += ((Movimientos) guardarMovTransferirSalida.get(guardarMovTransferirSalida.size() - 1))
							.getImporte();

					// Actualización saldo tras el movimiento Salida ****
					cuentaSeleccionadaTransferenciaSalida.setSaldo(saldo3);
					cuentaSeleccionadaTransferenciaSalida.setMovimientosCuenta(guardarMovTransferirSalida);

					// **** HILO hace que la retirada de dinero con la transferencia tarde un rato
					// ******

					Hilo hiloTransferenciaSalida = new Hilo("Transferencia Salida");
					hiloTransferenciaSalida.start();
					System.out.println();
					System.out.println("Emitiendo transferencia");
					// El "for" es simplemente para que pinte los puntos poco a poco
					for (int j = 0; j < 50; j++) {
						System.out.print(".");
						try {
							// TimeUnit.MILLISECONDS.sleep(100);
							Thread.sleep(100);
						} catch (InterruptedException exc) {
							System.out.println("Hilo principal interrumpido");
						}
					}

					// Final del Hilo **********

					System.out.println();
					System.out.println(" --- TRANSFERENCIA EMITIDA --- ");
					System.out.println();

					// ----------------- Cuenta de destino --------------------------
					// Para actualizar el saldo tras movimiento de entrada en la cuenta destino

					int saldo4 = cuentaSeleccionadaTransferenciaEntrada.getSaldo();
					ArrayList<Movimientos> guardarMovTransferirEntrada = cuentaSeleccionadaTransferenciaEntrada
							.getMovimientosCuenta();

					// Añadir al saldo solo el último movimiento, y que no repita mov pasados
					saldo4 += ((Movimientos) guardarMovTransferirEntrada.get(guardarMovTransferirEntrada.size() - 1))
							.getImporte();

					// Actualización saldo tras el movimiento entrada ****
					cuentaSeleccionadaTransferenciaEntrada.setSaldo(saldo4);
					cuentaSeleccionadaTransferenciaEntrada.setMovimientosCuenta(guardarMovTransferirEntrada);

					// **** HILO hacer que la entrada de dinero con la transferencia tarde un rato

					Hilo hiloTransferenciaEntrada = new Hilo("Transferencia Entrada");
					hiloTransferenciaEntrada.start();
					System.out.println();
					System.out.println("Recibiendo transferencia");
					for (int j = 0; j < 50; j++) {
						System.out.print(".");
						try {
							// TimeUnit.MILLISECONDS.sleep(100);
							Thread.sleep(100);
						} catch (InterruptedException exc) {
							System.out.println("Hilo principal interrumpido");
						}
					}

					// Final del Hilo

					System.out.println();
					System.out.println(" --- TRANSFERENCIA RECIBIDA --- ");

				} catch (SaldoInsuficienteException e) {
					System.err.println(e.getMessage());
				}

				break;

			case "4":
				System.out.println("Introduce el nombre de tu cuenta");
				// -------------- Seleccionar cuenta mediante nombreCuenta -------

				String elegirCuentaMenu4 = Utilidades.getTeclado();
				CuentaCorriente cuentaSeleccionadaVerMov = CuentaCorriente.cuentaSeleccionada(listaCuentas,
						elegirCuentaMenu4);

				// Para acceder a los datos del "Array Movimientos" de los movimientos hechos
				System.out.println();
				System.out.println("    ---------    MOVIMIENTOS DE SALDO EN "
						+ cuentaSeleccionadaVerMov.getNombreCuenta() + "    ---------    ");

				for (int i = 0; i < cuentaSeleccionadaVerMov.getMovimientosCuenta().size(); i++) {
					String tipoOperacion = cuentaSeleccionadaVerMov.getMovimientosCuenta().get(i).getTipoOperacion();
					String conceptoMov = cuentaSeleccionadaVerMov.getMovimientosCuenta().get(i).getConcepto();
					int importeMov = cuentaSeleccionadaVerMov.getMovimientosCuenta().get(i).getImporte();
					Date fechaMov = cuentaSeleccionadaVerMov.getMovimientosCuenta().get(i).getFecha();

					System.out.println();
					System.out.println("Movimiento " + (i + 1) + " : " + tipoOperacion + " | " + conceptoMov + " | "
							+ importeMov + " €" + " | " + fechaMov);
				}

				break;

			case "5":

				System.out.println("Introduce el nombre de tu cuenta");
				// Seleccionar cuenta mediante nombreCuenta

				String elegirCuentaMenu5 = Utilidades.getTeclado();
				CuentaCorriente cuentaSeleccionadaVerSaldo = CuentaCorriente.cuentaSeleccionada(listaCuentas,
						elegirCuentaMenu5);

				System.out.println();

				System.out.println("\t SALDO " + cuentaSeleccionadaVerSaldo.getNombreCuenta() + ": "
						+ cuentaSeleccionadaVerSaldo.getSaldo() + "€");

				break;

			case "6":

				System.out.println("Introduce el nombre de la nueva cuenta");

				CuentaCorriente cuentaNueva = new CuentaCorriente();

				String nombreNuevaCuenta = Utilidades.getTeclado();
				cuentaNueva.setNombreCuenta(nombreNuevaCuenta);

				System.out.println();
				System.out.println("Introduce el titular de la cuenta");
				String titutarNuevaCuenta = Utilidades.getTeclado();

				cuentaNueva.setTitular(titutarNuevaCuenta);

				System.out.println();
				cuentaNueva.setSaldo(0);

				// Añadir la nueva cuenta a la lista de cuentas
				listaCuentas.add(cuentaNueva);

				System.out.println();
				System.out.println(" --- CUENTA CREADA --- ");

				break;

			case "7":

				// Sacar la lista de las cuentas del Objeto CuentaCorriente
				// ObjectOutputStream --> usa el objeto en local
				try (ObjectOutputStream cuentasOutputStream = new ObjectOutputStream(
						new FileOutputStream("ListaCuentas.txt"));) {
					// Coger el objeto
					cuentasOutputStream.writeObject((ArrayList<CuentaCorriente>) listaCuentas);
					cuentasOutputStream.flush();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Usar el Objeto listaCuentas
				try (ObjectInputStream cuentasInputStream = new ObjectInputStream(
						new FileInputStream("ListaCuentas.txt"));) {

					// Para sacal la lista por consola
					for (int i = 0; i < listaCuentas.size(); i++) {
						System.out.println(" \t ********************* " + "\n\t Nombre cuenta:\t "
								+ ((CuentaCorriente) listaCuentas.get(i)).getNombreCuenta() + "\n\t Titular:\t"
								+ ((CuentaCorriente) listaCuentas.get(i)).getTitular() + "\n\t Saldo: \t"
								+ ((CuentaCorriente) listaCuentas.get(i)).getSaldo() + "€\n");
					}

					// -------------------- Para sacar la lista en el archivo txt
					// "FileOutputStream" para poder escribir en un fichero ****
					@SuppressWarnings("resource")
					FileOutputStream cuentasOutputStream2 = new FileOutputStream("ListaCuentas.txt", true);

					for (int i = 0; i < listaCuentas.size(); i++) {
						String leerListaCuentas = " \t ********************* " + "\n\t Nombre cuenta:\t "
								+ ((CuentaCorriente) listaCuentas.get(i)).getNombreCuenta() + "\n\t Titular:\t"
								+ ((CuentaCorriente) listaCuentas.get(i)).getTitular() + "\n\t Saldo: \t"
								+ ((CuentaCorriente) listaCuentas.get(i)).getSaldo() + "€\n";

						byte[] pintarListaCuentas = leerListaCuentas.getBytes();
						cuentasOutputStream2.write(pintarListaCuentas);
						cuentasOutputStream2.flush();

					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case "8":
				System.out.println("Introduce el nombre de tu cuenta");
				// -------------- Seleccionar cuenta mediante nombreCuenta -------

				String elegirCuentaMenu8 = Utilidades.getTeclado();
				CuentaCorriente cuentaSeleccionadaExtractoMovs = CuentaCorriente.cuentaSeleccionada(listaCuentas,
						elegirCuentaMenu8);

				// Sacar la lista de las cuentas del Objeto CuentaCorriente
				// ObjectOutputStream --> usa el objeto en local
				try (ObjectOutputStream extractoOutputStream = new ObjectOutputStream(
						new FileOutputStream("ExtractoMovimientos.txt"));) {
					// Coger el objeto
					extractoOutputStream.writeObject(cuentaSeleccionadaExtractoMovs);
					extractoOutputStream.flush();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Usar el Objeto listaCuentas
				try (ObjectInputStream extractoInputStream = new ObjectInputStream(
						new FileInputStream("ExtractoMovimientos.txt"));) {

					@SuppressWarnings("resource")
					FileOutputStream extractoOutputStream2 = new FileOutputStream(
							cuentaSeleccionadaExtractoMovs.getNombreCuenta() + "ExtractoMovimientos.txt", true);

					for (int i = 0; i < cuentaSeleccionadaExtractoMovs.getMovimientosCuenta().size(); i++) {

						String leerMovCuenta = " \t ********************* " + "\n\t Movimiento" + (i + 1) + " : \n"
								+ "\t Tipo Operacion: \t "
								+ cuentaSeleccionadaExtractoMovs.getMovimientosCuenta().get(i).getTipoOperacion()
								+ "\n\t Concepto: \t "
								+ cuentaSeleccionadaExtractoMovs.getMovimientosCuenta().get(i).getConcepto()
								+ "\n\t Importe: \t "
								+ cuentaSeleccionadaExtractoMovs.getMovimientosCuenta().get(i).getImporte() + " €"
								+ "\n\t Fecha: \t "
								+ cuentaSeleccionadaExtractoMovs.getMovimientosCuenta().get(i).getFecha()
								+ "\n\t ********************* ";

						byte[] sacarExtracto = leerMovCuenta.getBytes();
						extractoOutputStream2.write(sacarExtracto);
						extractoOutputStream2.flush();

					}

					System.out.println();
					System.out.println("    ---------  " + "SE HA GENERADO EL ESTRACTO DE MOVIMIENTOS DE LA "
							+ cuentaSeleccionadaExtractoMovs.getNombreCuenta() + "    ---------    ");


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case "0":
				System.exit(0);

			}
		}

	}

	private static void pintarMenu() {
		System.out.println();
		System.out.println("\t==========================");
		System.out.println("\t MENÚ DE OPERACIONES");
		System.out.println("\t==========================");
		System.out.println("\t 1.Ingresar");
		System.out.println("\t 2.Sacar");
		System.out.println("\t 3.Hacer transferencia");
		System.out.println("\t 4.Ver movimientos de cuenta");
		System.out.println("\t 5.Ver saldo cuenta");
		System.out.println("\t 6.Crear nueva cuenta");
		System.out.println("\t 7.Sacar listado de cuentas");
		System.out.println("\t 8.Sacar extracto movimientos cuenta");
		System.out.println("\t 9.Eliminar cuenta");
		System.out.println();
		System.out.println("\t 0.No hacer más operaciones");
		System.out.println();
		System.out.println("\t Elige una opción");
	}

	
	
}
