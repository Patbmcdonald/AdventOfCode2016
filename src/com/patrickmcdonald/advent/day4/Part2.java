package com.patrickmcdonald.advent.day4;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.patrickmcdonald.util.Util;

/**
 * 
 -- Part Two ---

With all the decoy data out of the way, it's time to decrypt this list and get moving.

The room names are encrypted by a state-of-the-art shift cipher, which is nearly unbreakable without the right software. However, the information kiosk designers at Easter Bunny HQ were not expecting to deal with a master cryptographer like yourself.

To decrypt a room name, rotate each letter forward through the alphabet a number of times equal to the room's sector ID. A becomes B, B becomes C, Z becomes A, and so on. Dashes become spaces.

For example, the real name for qzmt-zixmtkozy-ivhz-343 is very encrypted name.

What is the sector ID of the room where North Pole objects are stored?
 * @author Patrick McDonald
 *
 */
public class Part2 {

	
	public static void main(String[] args) throws IOException {

		String input = Util.getStringFromFile(new File("input4.txt"));
		String[] instructions = input.split("\n");
		Map<String, Integer> rooms = new HashMap<String, Integer>();

		for (int i = 0; i < instructions.length; i++) {
			String instruction = instructions[i];

			if (instruction.isEmpty())
				continue;

			String[] parts = instruction.split("-");

			Map<Character, Integer> _seenCharacterPos = new HashMap<Character, Integer>();

			for (String part : parts) {

				/** Room Number / Check Sum **/
				if (part.matches(".*\\d+.*")) {
					
					int roomNum = Integer.parseInt(part.substring(0, part.indexOf('[')));
					char[] hashCode = part.substring((part.indexOf('[') + 1), part.indexOf(']')).toCharArray();

					if (isValidCheckSum(_seenCharacterPos, hashCode))
						rooms.put(getRoomName(instruction, roomNum), roomNum);
				
				} else {
					parseLettersForChkSum(_seenCharacterPos, part);
				}
			}

		}
		
		for(String key : rooms.keySet()){
			if(key.contains("north"))
				System.out.println(key + "- " + rooms.get(key));
		}
	
	}

	/**
	 * Get Room Name
	 * @param encryptedRoom
	 * @return
	 */
	private static String getRoomName(String encryptedRoom, int roomNumber) {

		String[] parts = encryptedRoom.split("-");
		String roomName = "";
		for (String part : parts) {

			/** ignore Room Number / Check Sum **/
			if (!part.matches(".*\\d+.*")){
				
				char[] letters = part.toCharArray();
				
				/** Convert to Characters then shift... mod by 26 to get the correct shift **/
				for(int i = 0; i < letters.length; i++){			
					char letter = letters[i];
					
					char newLetter = (char) (letter + (roomNumber % 26));
					
				     if (newLetter > 'z')
				    	 roomName += (char)(newLetter - 26);
				     else
				    	 roomName += newLetter;
					
				}
				roomName+= " ";
			}
		}
		
		
		return roomName;
		
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

			if (!occurrences.containsKey(character) && occurrences.size() >= hashCode.length)
				return false;
			

			if (i > 0) {
				
				Character previousCharacter = hashCode[i - 1];

				if (occurrences.get(previousCharacter) < occurrences.get(character))
					return false;
				
				else if (occurrences.get(previousCharacter) == occurrences.get(character)) {
					
					if (previousCharacter.compareTo(character) > 0)
						return false;

				}
			}
		}

		return true;
	}
}