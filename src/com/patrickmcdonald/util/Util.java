package com.patrickmcdonald.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
	public static String getStringFromFile(File f) throws IOException {
		String result = "";

		if (f.exists()) {
			StringBuffer sb2 = new StringBuffer(5000);
			FileReader reader = new FileReader(f);
			BufferedReader buffer = new BufferedReader(reader);

			String st = buffer.readLine();
			while (st != null) {
				sb2.append("\n" + st);
				st = buffer.readLine();
			}

			buffer.close();
			reader.close();

			result = sb2.toString();
		}

		return result;
	}

	public static int getLen(int n) {
		return (int) (Math.log10(n) + 1);
	}

	public static char getDigit(int n, int digit) {
		String number = String.valueOf(n);

		char[] digits = number.toCharArray();

		return digits[digits.length - 1];
	}

	public static int getDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	/**
	 * Parse letters to create checkSum
	 * 
	 * @param _seenPos
	 * @param part
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5Hash(String part) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(part.getBytes());

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();

	}

	public static boolean isDigit(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); 
	}

}
