package com.patrickmcdonald.advent.day6;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.patrickmcdonald.util.Util;

/**
 * 
Of course, that would be the message - if you hadn't agreed to use a modified repetition code instead.

In this modified code, the sender instead transmits what looks like random data, but for each character, the character they actually want to send is slightly less likely than the others. Even after signal-jamming noise, you can look at the letter distributions in each column and choose the least common letter to reconstruct the original message.

In the above example, the least common character in the first column is a; in the second, d, and so on. Repeating this process for the remaining characters produces the original message, advent.

Given the recording in your puzzle input and this new decoding methodology, what is the original message that Santa is trying to send?
 * @author Patrick McDonald
 *
 */
public class Part2 {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		String input = Util.getStringFromFile(new File("input6.txt"));
		String[] instructions = input.split("\n");

		Map<Integer, List<Character>> characters = new HashMap<Integer, List<Character>>();
		
		for (int i = 0; i < instructions.length; i++) {
			
			String instruction = instructions[i];
			
			if(instruction.isEmpty())
				continue;
			
			char[] letters = instruction.toCharArray();
			
			
				
			for(int j = 0; j < letters.length; j++){
				
				List<Character> listOfCharacters = characters.get(j);
				
				if(listOfCharacters == null)
					listOfCharacters = new LinkedList<Character>();
				
				listOfCharacters.add(letters[j]);
				
				
				characters.put(j, listOfCharacters);
			}
			
		}
		
		/** Get most common element out of list **/
		for(int pos : characters.keySet()){
			System.out.print(getLeastCommonElement(characters.get(pos)));
		}
		
		System.out.println();

	}
	
	public static Character getLeastCommonElement(List<Character> list) {

		Map<Character, Integer> map = Util.getListAsMap(list);
		
	    Entry<Character, Integer> min = null;

	    for (Entry<Character, Integer> e : map.entrySet()) {
	        if (min == null || e.getValue() < min.getValue())
	        	min = e;
	    }

	    return min.getKey();
	}
	

}