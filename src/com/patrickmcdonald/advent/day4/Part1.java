package com.patrickmcdonald.advent.day4;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import java.util.Map;

import com.patrickmcdonald.util.Util;

/**
 * 
 * --- Day 4: Security Through Obscurity ---
 * 
 * Finally, you come across an information kiosk with a list of rooms. Of
 * course, the list is encrypted and full of decoy data, but the instructions to
 * decode the list are barely hidden nearby. Better remove the decoy data first.
 * 
 * Each room consists of an encrypted name (lowercase letters separated by
 * dashes) followed by a dash, a sector ID, and a checksum in square brackets.
 * 
 * A room is real (not a decoy) if the checksum is the five most common letters
 * in the encrypted name, in order, with ties broken by alphabetization. For
 * example:
 * 
 * aaaaa-bbb-z-y-x-123[abxyz] is a real room because the most common letters are
 * a (5), b (3), and then a tie between x, y, and z, which are listed
 * alphabetically. a-b-c-d-e-f-g-h-987[abcde] is a real room because although
 * the letters are all tied (1 of each), the first five are listed
 * alphabetically. not-a-real-room-404[oarel] is a real room.
 * totally-real-room-200[decoy] is not.
 * 
 * Of the real rooms from the list above, the sum of their sector IDs is 1514.
 * 
 * What is the sum of the sector IDs of the real rooms?
 * 
 * 
 * @author Patrick McDonald
 *
 */
public class Part1 {

	public static void main(String[] args) throws IOException {

		String input = Util.getStringFromFile(new File("input4.txt"));
		String[] instructions = input.split("\n");

		int count = 0;

		for (int i = 0; i < instructions.length; i++) {
			
			String instruction = instructions[i];

			if (instruction.isEmpty())
				continue;

			String[] parts = instruction.split("-");

			/** Store characters we've seen and the number of occurances **/
			Map<Character, Integer> _seenCharacterPos = new HashMap<Character, Integer>();

			for (String part : parts) {

				/** Room Number / Check Sum **/
				if (part.matches(".*\\d+.*")) {
					
					int roomNum = Integer.parseInt(part.substring(0, part.indexOf('['))); // room number is the before [ 
					char[] hashCode = part.substring((part.indexOf('[') + 1), part.indexOf(']')).toCharArray(); // hashCode is inbetween [ ]

					/** Valid Check Sum **/
					if (isValidCheckSum(_seenCharacterPos, hashCode))
						count += roomNum;

				} else {
					parseLettersForChkSum(_seenCharacterPos, part);
				}
			}

		}
		System.out.println("Valid amount of rooms: " + count);
	}

	/**
	 * Parse letters to create checkSum
	 * 
	 * @param _seenPos
	 * @param part
	 */
	private static void parseLettersForChkSum(Map<Character, Integer> _seenPos, String part) {
		for (char letter : part.toCharArray()) {
			if (_seenPos.get(letter) == null)
				_seenPos.put(letter, 1);
			else {
				int count = _seenPos.get(letter) + 1;
				_seenPos.put(letter, count);
			}
		}
	}

	/**
	 * Valid Check Sum
	 * @param occurrences
	 * @param hashCode
	 * @return
	 */
	private static boolean isValidCheckSum(Map<Character, Integer> occurrences, char[] hashCode) {
		for (int i = 0; i < hashCode.length; i++) {
			char character = hashCode[i];

			/** We do not contain this key **/
			if (!occurrences.containsKey(character) && occurrences.size() >= hashCode.length)
				return false;
			
			if (i > 0) {
				
				Character previousCharacter = hashCode[i - 1];

				/** is the number of occuences less ?**/
				if (occurrences.get(previousCharacter) < occurrences.get(character))
					return false;
				else if (occurrences.get(previousCharacter) == occurrences.get(character)) { /** if its the same, break the tie by alpha **/
					if (previousCharacter.compareTo(character) > 0)
						return false;

				}
			}
		}

		return true;
	}

}