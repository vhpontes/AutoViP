package org.mcforge.vhpontes.utils;

import java.util.Random;

public class CodeUtils {

	private final char[] LETTERS = { 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D',
			'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k',
			'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q',
			'r', 'R', 's', 'S', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x',
			'X', 'y', 'Y', 'z', 'Z' };
	private final char[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };

	public String[] codegen(int amount, int codelenght) {
		Random r = new Random();
		String[] s = new String[amount];
		for (int i = 0; i < amount; i++) {
			String a = "";
			for (int j = 0; j < codelenght; j++) {
				if (r.nextInt(10) < 4) {
					a += NUMBERS[r.nextInt(NUMBERS.length)];
				} else {
					a += LETTERS[r.nextInt(LETTERS.length)];
				}
			}
			s[i] = a;
		}
		return s;
	}
}
