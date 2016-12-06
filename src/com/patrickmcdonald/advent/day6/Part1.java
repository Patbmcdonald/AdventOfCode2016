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
 --- Day 6: Signals and Noise ---

Something is jamming your communications with Santa. Fortunately, your signal is only partially jammed, and protocol in situations like this is to switch to a simple repetition code to get the message through.

In this model, the same message is sent repeatedly. You've recorded the repeating message signal (your puzzle input), but the data seems quite corrupted - almost too badly to recover. Almost.

All you need to do is figure out which character is most frequent for each position. For example, suppose you had recorded the following messages:

 * @author Patrick McDonald
 *
 */
public class Part1 {

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
			System.out.print(getMostCommonElement(characters.get(pos)));
		}
		
		System.out.println();

	}

	public static Character getMostCommonElement(List<Character> list) {

		Map<Character, Integer> map = Util.getListAsMap(list);
		
	    Entry<Character, Integer> max = null;

	    for (Entry<Character, Integer> e : map.entrySet()) {
	        if (max == null || e.getValue() > max.getValue())
	        	max = e;
	    }

	    return max.getKey();
	}
	
	
}