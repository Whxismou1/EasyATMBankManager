package com.atm.whxismou;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Operations {
	private Scanner sc = new Scanner(System.in);
	private ConexionBBDD bd = new ConexionBBDD();
	private List<String> transactionList = new ArrayList<>();

	public void selectOption(User user) throws SQLException {
		int option;

		do {
			System.out.println("Introduce an option");
			option = sc.nextInt();

			switch (option) {
			case 1:
				withdraw(user);
				break;
			case 2:
				deposit(user);
				break;
			case 3:
				checkBalance(user);
				break;
			case 4:
				checkTransactions(user);
				break;
			case 5:
				return;
			case 0:
				System.out.println("Exiting!");
				System.exit(0);
				break;
			default:

			}
		} while (option != 0);

	}

	private void checkTransactions(User user) throws SQLException {
		transactionList = bd.getTransactionList(user);
		System.out.println("Lista de transacciones: ");
		if(!transactionList.isEmpty()) {
			int i = 1;
			for (String elem : transactionList) {
				System.out.println(i + ". " + elem);
				i++;
			}
		}else {
			System.out.println("No hay transacciones!");
		}


	}

	private void deposit(User user) throws SQLException {
		// Se obtiene el saldo actual
		int balance = bd.checkBalance(user);

		// Se le pide al usuario cuanto va a depositar
		System.out.println("¿Cuánto dinero quieres depositar?: ");
		int cantidadADepositar = sc.nextInt();

		if (cantidadADepositar <= 0) {
			System.out.println("ERROR: La cantidad debe ser superior a 0!");
			return;
		}

		int nuevoBalance = balance + cantidadADepositar;

		bd.updateBalance(user, nuevoBalance);
		
		transactionList.add("Depósito de " + cantidadADepositar  + "€");
		bd.updateTransactions(user, transactionList);

	}

	private void withdraw(User user) throws SQLException {
		// TODO Auto-generated method stub

		int balance = bd.checkBalance(user);

		// Se le pide al usuario cuanto va a retirar
		System.out.println("¿Cuánto dinero quieres retirar?: ");
		int cantidadARetirar = sc.nextInt();
		
		int nuevoBalance = balance - cantidadARetirar;
		
		if(cantidadARetirar > balance || nuevoBalance < 0) {
			System.out.println("ERROR: No puedes sacar dicha cantidad!");
			return;
		}
		
		bd.updateBalance(user, nuevoBalance);
		
		transactionList.add("Retirada de " + cantidadARetirar  + "€");
		
		bd.updateTransactions(user, transactionList);

	}

	private void checkBalance(User user) throws SQLException {
		int balance = bd.checkBalance(user);
		System.out.println("Tu balance actual es de: " + balance + "€");
	}
}
