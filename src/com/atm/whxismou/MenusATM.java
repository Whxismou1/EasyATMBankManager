package com.atm.whxismou;

/***
 * 
 * @author Whxismou
 * 
 * Calse dedicada a la impresion de ambos menus
 *
 */


public class MenusATM {
	/***
	 *Método encargado de imprimir el menu principal
	 */

	public void printMainMenu() {
		System.out.println("******************************");
		System.out.println("* 1. Login                   *");
		System.out.println("* 2. Register                *");
		System.out.println("* 0. Exit                    *");
		System.out.println("******************************");

	}

	/***
	 *Método encargado de imprimir el menu de operaciones
	 */
	public void printOpsMenu() {
		System.out.println("******************************");
		System.out.println("* 1. Withdraw money          *");
		System.out.println("* 2. Deposit money           *");
		System.out.println("* 3. Check balance           *");
		System.out.println("* 4. Check transactions      *");
		System.out.println("* 5. Return to main menu     *");
		System.out.println("* 0. Exit                    *");
		System.out.println("******************************");

	}

}
