package com.atm.whxismou;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

/***
 * 
 * @author Whxismou
 * 
 *         Clase encargada de ejercer las funciones de login y registrar
 *
 */
public class LoginRegister {
	Scanner sc = new Scanner(System.in);
	ConexionBBDD bd = new ConexionBBDD();
	Encoder encrypter = new Encoder();
	User userActual;

	public void login() throws SQLException, IOException, NoSuchAlgorithmException {
		System.out.println("\n\n***** Login MENU *****");

		System.out.print("Introduce the username: ");
		String userIntroduced = sc.next();

		if (!bd.checkUserExist(userIntroduced)) {
			System.out.println("ERROR: El usuario no existe!");
		} else {
			//En caso de que exista pedimos la contraseña
			System.out.println("Introduce the password: ");
			String passwordIntroduced = sc.next();
			
			//obtenemos el useractual
			userActual = bd.getUserDB(userIntroduced);
			
			if(!encrypter.encode(passwordIntroduced).equals(userActual.getPassword())) {
				System.out.println("ERROR: Usuario/Paswword incorrecto!");
			}else {
				System.out.println("Login succesfully!");
				return;
			}
			
			
		}

	}

	public void register() throws SQLException, NoSuchAlgorithmException, IOException {

		System.out.println("\n\n***** REGISTER MENU *****");
		// Se le pide al usuario el username
		System.out.print("Introduce the username: ");
		String userIntroduced = sc.next();

		// Se comprueba que no haya 2 usernames iguales
		if (bd.checkUserExist(userIntroduced)) {
			System.out.println("ERROR: The user alredy exists!!");
		} else {
			// en el caso de no haberlo, le pedimos la contraseña
			String passwordIntroduced = sc.next();

			// Se procede a encriptarla
			String passEncrypted = encrypter.encode(passwordIntroduced);

			// Se introduce el usuario en la base de datos y obtenemos el id que le asigna
			// la BBDD
			int id = bd.introduce(userIntroduced, passEncrypted);
			User user = new User(id, userIntroduced, passEncrypted);
			System.out.println("User registration completed\n\n\n");
		}

	}
	
	public User getUserActual() {
		return userActual;
	}
	
	
	
}