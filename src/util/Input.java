package util;

import java.util.Scanner;

/**
 * Une classe utilitaire pour la saisie de cha�nes ou d'entiers sur l'entr�e
 * standard.
 */

public class Input {
	private Scanner scanner;

	public Input() {
		this.scanner = new Scanner(System.in);
	}

	/**
	 * permet la saisie d'une chaîne sur l'entrée standard
	 * 
	 * @return la chaîne saisie
	 */
	public static String readString() {
		return new Input().localReadString();
	}

	private String localReadString() {
		return this.scanner.next();
	}

	/**
	 * permet la saisie d'un entier sur l'entrée standard
	 * 
	 * @return l'entier saisi
	 */
	public static int readInt() throws java.io.IOException {
		return new Input().localReadInt();
	}

	private int localReadInt() throws java.io.IOException {
		try {
			int choice = 0;

			if (this.scanner.hasNextInt()) {
				choice = this.scanner.nextInt();
			}
			return choice;
		} catch (Exception e) {
			e.printStackTrace();
			this.scanner.skip(".*");
			throw new java.io.IOException("entier attendu");
		}
	}

	// pour le test
	public static void main(String[] args) {
		try {
			System.out.print(" chaine : ? ");
			String chaineLue = Input.readString();
			System.out.println("lue  => " + chaineLue);
			System.out.print(" int : ? ");
			int intLu = Input.readInt();
			System.out.println("lue  => " + intLu);
		} catch (java.io.IOException e) {
		}
	}
} // Input
