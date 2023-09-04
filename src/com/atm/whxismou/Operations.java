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

	private void checkTransactions(User user) {
		// TODO Auto-generated method stub
		
	}

	private void deposit(User user) {
		// TODO Auto-generated method stub
		
	}

	private void withdraw(User user) {
		// TODO Auto-generated method stub
		
	}

	private void checkBalance(User user) throws SQLException {
		int balance = bd.checkBalance(user);
		System.out.println("Tu balance actual es de: " + balance + "€");
	}
}
