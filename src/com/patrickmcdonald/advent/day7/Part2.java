package com.patrickmcdonald.advent.day7;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.patrickmcdonald.util.Util;

/**
 * 
 * --- Part Two ---
 * 
 * You would also like to know which IPs support SSL (super-secret listening).
 * 
 * An IP supports SSL if it has an Area-Broadcast Accessor, or ABA, anywhere in
 * the supernet sequences (outside any square bracketed sections), and a
 * corresponding Byte Allocation Block, or BAB, anywhere in the hypernet
 * sequences. An ABA is any three-character sequence which consists of the same
 * character twice with a different character between them, such as xyx or aba.
 * A corresponding BAB is the same characters but in reversed positions: yxy and
 * bab, respectively.
 * 
 * For example:
 * 
 * aba[bab]xyz supports SSL (aba outside square brackets with corresponding bab
 * within square brackets). xyx[xyx]xyx does not support SSL (xyx, but no
 * corresponding yxy). aaa[kek]eke supports SSL (eke in supernet with
 * corresponding kek in hypernet; the aaa sequence is not related, because the
 * interior character must be different). zazbz[bzb]cdb supports SSL (zaz has no
 * corresponding aza, but zbz has a corresponding bzb, even though zaz and zbz
 * overlap).
 * 
 * How many IPs in your puzzle input support SSL?
 * 
 * @author Patrick
 *
 */
public class Part2 {

	private final static String BRACKETS_REGEX = "\\[(.*?)\\]";
	private final static String NONBRACKETS_REGEX = "\\[.*?\\]";

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		String input = Util.getStringFromFile(new File("input7.txt"));
		String[] instructions = input.split("\n");
		Pattern pattern = Pattern.compile(BRACKETS_REGEX);

		int count = 0;

		for (int i = 0; i < instructions.length; i++) {

			String instruction = instructions[i];

			if (instruction.isEmpty())
				continue;

			/** Check out hypernet **/
			String[] parts = instruction.split(NONBRACKETS_REGEX);

			Matcher matcher = pattern.matcher(instruction);

			System.out.println("Number:" + i);
			List<String> prevBABs = new LinkedList<String>();

			while (matcher.find()) {
				String bab = matcher.group();

				bab = bab.replace("[", "");
				bab = bab.replace("]", "");
				prevBABs.add(bab);
			}

			for (String ada : parts) {

				for (String prevBAB : prevBABs) {
					if (isValidinput(ada, prevBAB)) {
						count++;
					}
				}

			}

		}

		System.out.println("Number of Valid IPs: " + count);

	}

	/*
	 * 
	 * such as xyx or aba. A corresponding BAB is the same characters but in
	 * reversed positions: yxy and ba
	 * 
	 * @param str
	 * 
	 * @return
	 */
	public static boolean isValidinput(String ABA, String BAB) {
		char[] abaLetters = ABA.toCharArray();
		char[] babLetters = BAB.toCharArray();

		for (int index = 0; index < abaLetters.length; index++) {

			char aba_currentChar = abaLetters[index];

			if (index + 2 < abaLetters.length) {
				char aba_nextChar = abaLetters[index + 1];
				
				for (int bIndex = 0; bIndex + 2 < babLetters.length; bIndex += 2) {

					char bab_currentChar = babLetters[bIndex];

					char bab_nextChar = babLetters[bIndex + 1];
					char bab_nextNextChar = babLetters[bIndex + 2];

					String aba = Character.toString(aba_nextChar) + Character.toString(aba_currentChar)
							+ Character.toString(aba_nextChar);
					String bab = Character.toString(bab_currentChar) + Character.toString(bab_nextChar)
							+ Character.toString(bab_nextNextChar);
					if (aba.equals(bab)) {

						System.out.println("ABA: " + aba + " bab:" + bab);

						return true;
					}
				}
			}
		}
		return false;
	}

}