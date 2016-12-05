package com.patrickmcdonald.advent.day5;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import com.patrickmcdonald.util.Util;

/**
 * 
 * As the door slides open, you are presented with a second door that uses a
 * slightly more inspired security mechanism. Clearly unimpressed by the last
 * version (in what movie is the password decrypted in order?!), the Easter
 * Bunny engineers have worked out a better solution.
 * 
 * Instead of simply filling in the password from left to right, the hash now
 * also indicates the position within the password to fill. You still look for
 * hashes that begin with five zeroes; however, now, the sixth character
 * represents the position (0-7), and the seventh character is the character to
 * put in that position.
 * 
 * A hash result of 000001f means that f is the second character in the
 * password. Use only the first result for each position, and ignore invalid
 * positions.
 * 
 * For example, if the Door ID is abc:
 * 
 * The first interesting hash is from abc3231929, which produces 0000015...; so,
 * 5 goes in position 1: _5______. In the previous method, 5017308 produced an
 * interesting hash; however, it is ignored, because it specifies an invalid
 * position (8). The second interesting hash is at index 5357525, which produces
 * 000004e...; so, e goes in position 4: _5__e___.
 * 
 * You almost choke on your popcorn as the final character falls into place,
 * producing the password 05ace8e3.
 * 
 * Given the actual Door ID and this new method, what is the password? Be extra
 * proud of your solution if it uses a cinematic "decrypting" animation.
 * 
 * Your puzzle input is still cxdnnyjw.
 * 
 * @author Patrick McDonald
 *
 */
public class Part2 {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		String input = "cxdnnyjw";

		String[] decryptedPassword = new String[8];

		int currentIndex = 0;
		int foundIndex = 0;
		System.out.println("searching...");
		while (foundIndex < 8) {

			String hash = Util.getMD5Hash(input + Integer.toString(currentIndex));

			if (hash.startsWith("00000")) {
				
				/** Is this a valid pos? IE a digit? **/
				int pos = ((hash.substring(5, 6).matches("-?\\d+(\\.\\d+)?"))) ? Integer.parseInt(hash.substring(5, 6)) : -1;
				
				/** Character **/
				String chr = hash.substring(6, 7).toString();

				/** If valid Position and the position was not taken yet **/
				if (pos != -1 && pos > -1 && pos < decryptedPassword.length && decryptedPassword[pos] == null) {
					decryptedPassword[pos] = chr;
					foundIndex++;
					System.out.println("Found a letter in the hash: " + hash + "\n Pos: " + pos + " \n Character: " + chr);
				}
			}

			currentIndex++;
		}

		StringBuffer buff = new StringBuffer();
		
		for (String s : decryptedPassword)
			buff.append(s);

		System.out.println("Password: " + buff.toString());
		System.out.println("Last Index: " + currentIndex);
	}

}