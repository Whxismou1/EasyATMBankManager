package com.atm.whxismou;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***
 * @author Whxismou
 * 
 *Clase encargada de encriptar la contraseña en SHA-256 antes de
 *almacenarla en la BBDD
 * 
 */

public class Encoder {
	
	
	/***
	 * 
	 * @param userPass: Contraseña introducida por el usuario la cual hay que encriptar 
	 * @return resultHashed: Contraseña encriptada para guardarla en la BBDD 
	 * @throws NoSuchAlgorithmException
	 */
	public String encode(String userPass) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		byte[] bytes = userPass.getBytes();

		byte[] hash = md.digest(bytes);

		StringBuilder resultHashed = new StringBuilder();
		for (byte e : hash) {
			resultHashed.append(String.format("%02x", e));
		}

		return resultHashed.toString();

	}

}
