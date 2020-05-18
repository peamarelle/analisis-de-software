package lospinares;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bdatoa {

	private static final String path = "Archivos\\";

	public static void ps(String x) {
		System.out.print(x);
	}

	public static int LeerEntero() {
		String línea = new String("");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			línea = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int ne = 0;
		try {
			ne = Integer.parseInt(línea);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ne);
	}

	public static String LeerCadena() {
		String línea = new String("");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			línea = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (línea);
	}

	public static void main(String args[]) throws Exception {

		String op = "";
		int sw = 0, sw1 = 0;
		// variables de selección usadas en los diferentes menús
		int op1, op2, op3;
		// variables usadas en el registro de datos
		String codpac = "", cp = "", nompac = "", codmed = "", cm = "", enfpac, nommed, espmed;
		// variables usadas en la lectura de datos
		String codp = "", codpa = "", nomp = "", nompa = "", codm = "", codme = "", enfp = "", nomm = "", espm = "";
		// variables auxiliares temporales
		String codtem;
		
		/*
		 * 
		 * 
		 */

		do {
			op1 = 0;
			mostrarTituloPrincipal();
			// ps("\n");
			op1 = LeerEntero();
			if (op1 < 1 || op1 > 3) {
				ps("Debe digitar una opcion del menu" + "\n");
			}

			if (op1 == 1) // seleción ingreso de pacientes
			{

				do {

					mostrarMenuIngresoPacientes();

					op2 = LeerEntero();

					if (op2 < 1 || op2 > 4) {
						ps("Debe digitar una opcion del menu" + "\n");
					}

					switch (op2) {
					case 1: // ingreso de datos, datos del paciente
						ingresarNuevoPaciente();
						break;
					case 2:// ingreso de datos, situacion del paciente
						ingresarSituacionPaciente();
						break;
					case 3:// ingreso de datos, medico
						ingresarNuevoMedico();
					}
				} while (op2 != 4);
			} else {
				if (op1 == 2) // seleción informes
				{

					do {
						mostrarControlPacientes();
						op2 = LeerEntero();
						if (op2 < 1 || op2 > 3) {
							ps("Seleccione una de las opciones del menu" + "\n");
						}

						switch (op2) {
						case 1:
							listadoDePacientesPorMedico();
							break;
						case 2:
							enfermedadesQueAtiendeCadaMedico();
							break;
						}

					} while (op2 != 3);

				}
			}
		} while (op1 != 3);

	}

	private static void enfermedadesQueAtiendeCadaMedico() {

		ps("Digite el codigo del medico que desea consultar: ");
		String codtem = LeerCadena();

		DataInputStream datomed = null;
		try {
			datomed = new DataInputStream(new FileInputStream(path + "datomed.txt"));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		int sw1 = 1;
		while (sw1 != 0) {
			try {
				String codm = datomed.readUTF();
				String nomm = datomed.readUTF();
				String espm = datomed.readUTF();

				if (codm.equals(codtem)) // compara el codigo digitado
											// con el codigo del medico de la
											// tabla "datomed"
				{
					ps("El medico " + nomm + " trata las siguientes enfermedades:" + "\n");

					DataInputStream situpac = null;
					situpac = new DataInputStream(new FileInputStream(path + "situpac.txt"));

					int sw = 1;
					while (sw != 0) {
						try {
							String codp = situpac.readUTF();
							String codme = situpac.readUTF();
							String enfp = situpac.readUTF();

							if (codtem.equals(codme)) // compara el codigo del medico
							// de la tabla "datomed"
							// con el codigo del medico en la
							// tabla "situpac"

							{
								ps(">>>> " + enfp + "\n");
							}
						} catch (IOException e) {
							sw = 0;
						}
					}
				}
			} catch (EOFException | FileNotFoundException e) {
				sw1 = 0;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private static void listadoDePacientesPorMedico() {
		try {

			ps("Digite el codigo del medico que desea consultar: ");
			String codtem = LeerCadena();

			DataInputStream datomed = null;
			datomed = new DataInputStream(new FileInputStream(path + "datomed.txt"));

			int sw = 1;
			while (sw != 0) {
				String codm = "";
				String nomm = "";
				try {
					codm = datomed.readUTF();
					nomm = datomed.readUTF();
					String espm = datomed.readUTF();

				} catch (EOFException e) {
					sw = 0;
				}

				if (codm.equals(codtem)) // compara el codigo extraido de la
											// tabla "datomed" con el codigo
											// digitado
				{
					ps("::: El medico " + nomm + " atiende a los siguientes pacientes: " + "\n");
					DataInputStream situpac = null;
					situpac = new DataInputStream(new FileInputStream(path + "situpac.txt"));

					sw = 1;
					while (sw != 0) {
						try {
							String codp = situpac.readUTF();
							String codme = situpac.readUTF();
							String enfp = situpac.readUTF();

							if (codme.equals(codtem)) // compara el codigo medico de la
														// tabla "datomed" con el de la
														// tabla "situpac"
							{
								DataInputStream datopac = null;
								datopac = new DataInputStream(new FileInputStream(path + "datopac.txt"));

								int sw1 = 1;
								while (sw1 != 0) {
									try {
										String codpa = datopac.readUTF();
										String nompa = datopac.readUTF();

										if (codpa.equals(codp)) // compara el codigo del
																// paciente de la tabla "situpac"
																// con el codigo del paciente de
																// la tabla "datopac"
										{
											ps("::: Paciente: " + nompa + "\n");
										}
									} catch (EOFException e) {
										sw1 = 0;
										e.printStackTrace();
									}
								}
							}
						} catch (EOFException e) {
							sw = 0;
							e.printStackTrace();
						}
					}
				}
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	private static void ingresarNuevoMedico() {

		DataOutputStream datomed = null;
		try {
			datomed = new DataOutputStream(new FileOutputStream(path + "datomed.txt"));
			String op;
			do {

				ps("   ....................................................." + "\n");
				ps("   :-:      - D A T O S   D E L   M E D I C O -      :-:" + "\n");
				ps("   :-:...............................................:-:" + "\n");

				ps("Digite el codigo del medico: ");
				String codmed = LeerCadena();
				datomed.writeUTF(codmed);

				ps("Digite el nombre del medico: ");
				String nommed = LeerCadena();
				datomed.writeUTF(nommed);

				ps("Digite la especializacion del medico: ");
				String espmed = LeerCadena();
				datomed.writeUTF(espmed);

				ps("Desea ingresar otro medico? S/N");
				ps("\n");

				op = LeerCadena();

				datomed.close();
			} while (op.equals("S") || op.equals("s"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	private static void ingresarSituacionPaciente() {

		DataOutputStream situpac = null;

		try {
			situpac = new DataOutputStream(new FileOutputStream(path + "situpac.txt"));
			String op;
			do {

				ps("   ....................................................." + "\n");
				ps("   :-: - S I T U A C I O N  D E L  P A C I E N T E - :-:" + "\n");
				ps("   :-:...............................................:-:" + "\n");

				ps("Digite el codigo del paciente: ");
				String codp = LeerCadena();
				situpac.writeUTF(codp);
				ps("Digite el codigo del medico: ");
				String codm = LeerCadena();
				situpac.writeUTF(codm);
				ps("Digite el diagnostico del medico: ");
				String enfpac = LeerCadena();
				situpac.writeUTF(enfpac);

				ps("Desea ingresar otro registro al historial? S/N");
				ps("\n");
				op = LeerCadena();

			} while (op.equals("S") || op.equals("s"));
			situpac.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private static void ingresarNuevoPaciente() {
		DataOutputStream datopac = null;
		try {
			datopac = new DataOutputStream(new FileOutputStream(path + "datopac.txt"));
			String op;
			do {
				ps("   ..............................................." + "\n");
				ps("   :-:  - D A T O S  D E L  P A C I E N T E -  :-:" + "\n");
				ps("   :-:.........................................:-:" + "\n");

				ps("Digite el codigo del paciente: ");
				String codpac = LeerCadena();
				datopac.writeUTF(codpac);
				ps("Digite el nombre del paciente: ");
				String nompac = LeerCadena();

				datopac.writeUTF(nompac);

				ps("Desea ingresar otro paciente? S/N" + "\n");

				op = LeerCadena();

			} while (op.equals("S") || op.equals("s"));
			datopac.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	private static void mostrarControlPacientes() {
		ps("   ..............................................." + "\n");
		ps("   :-:  C O N T R O L  D E  P A C I E N T E S  :-:" + "\n");
		ps("   ..............................................." + "\n");
		ps("   :-:           - I N F O R M E S -           :-:" + "\n");
		ps("   :-:.........................................:-:" + "\n");
		ps("   :-: 1. Listado de pacientes por medico      :-:" + "\n");
		ps("   :-: 2. Enfermedades que atiende cada medico :-:" + "\n");
		ps("   :-: 3. Anterior                             :-:" + "\n");
		ps("   ..............................................." + "\n");
		ps("   ....Elija la opcion deseada: ");

	}

	private static void mostrarMenuIngresoPacientes() {

		ps("   ..............................................." + "\n");
		ps("   :-: -I N G R E S O  D E  P A C I E N T E S- :-:" + "\n");
		ps("   :-:.........................................:-:" + "\n");
		ps("   :-: 1.  Datos del paciente                  :-:" + "\n");
		ps("   :-: 2.  Situacion del paciente              :-:" + "\n");
		ps("   :-: 3.  Datos del medico                    :-:" + "\n");
		ps("   :-: 4.  Volver                              :-:" + "\n");
		ps("   ..............................................." + "\n");
		ps("   ....Elija la opcion deseada: ");
	}

	private static void mostrarTituloPrincipal() {

		ps("   .............................................." + "\n");
		ps("   :-:         CENTRO ASISTENCIAL             :-:" + "\n");
		ps("   :-:   >>>> L O S  P I N A R E S   <<<<     :-:" + "\n");
		ps("   :-:  C O N T R O L  D E  P A C I E N T E S :-:" + "\n");
		ps("   :-:........................................:-:" + "\n");
		ps("   :-: 1.  Ingreso de datos                   :-:" + "\n");
		ps("   :-: 2.  Informes                           :-:" + "\n");
		ps("   :-: 3.  Salir                              :-:" + "\n");
		ps("   .............................................." + "\n");
		ps("   ....Elija la opcion deseada: ");

	}

}
