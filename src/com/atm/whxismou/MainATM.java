package com.atm.whxismou;

import java.io.IOException;

/***
 * @author Whxismou
 * 
 * Clase dedicada a la ejecución principal del programa
 */

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

public class MainATM {
	// Se crea un scaner para preguntar al usuario
	private static Scanner sc = new Scanner(System.in);
	private static User userActual;
	
	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException {
		// Se crea una instancia de la clase de menus
		MenusATM menus = new MenusATM();
		
		//Se crea una instancia que nos permitirá registrarnos o logearnos
		LoginRegister loginRegister = new LoginRegister();
		
		//Se crea la instancia para realizar las operaciones
		Operations ops = new Operations();
		
		
		int option;
		do {
			// Se imprime menú principal
			menus.printMainMenu();

			//Se le pide al usuario una opcion y se guarda dicha opcion
			System.out.print("Introduce an option: ");
			option = sc.nextInt();
			
			//En funcion de la opcion hacemos una cosa u otra
			switch (option) {
				case 1:
					loginRegister.login();
					userActual = loginRegister.getUserActual();
					menus.printOpsMenu();
					ops.selectOption(userActual);
					break;
				case 2:
					loginRegister.register();
					break;
				case 0:
					System.out.println("Exiting...");
					System.exit(0);
					break;
				default:
					System.out.println("ERROR: Invalid option!");
			}
		} while (option != 0);
		

	
	}

}
