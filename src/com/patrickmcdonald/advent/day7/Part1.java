package com.patrickmcdonald.advent.day7;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
--- Day 7: Internet Protocol Version 7 ---

While snooping around the local network of EBHQ, you compile a list of IP addresses (they're IPv7, of course; IPv6 is much too limited). You'd like to figure out which IPs support TLS (transport-layer snooping).

An IP supports TLS if it has an Autonomous Bridge Bypass Annotation, or ABBA. An ABBA is any four-character sequence which consists of a pair of two different characters followed by the reverse of that pair, such as xyyx or abba. However, the IP also must not have an ABBA within any hypernet sequences, which are contained by square brackets.

For example:

    abba[mnop]qrst supports TLS (abba outside square brackets).
    abcd[bddb]xyyx does not support TLS (bddb is within square brackets, even though xyyx is outside square brackets).
    aaaa[qwer]tyui does not support TLS (aaaa is invalid; the interior characters must be different).
    ioxxoj[asdfgh]zxcvbn supports TLS (oxxo is outside square brackets, even though it's within a larger string).

How many IPs in your puzzle input support TLS?

 * @author Patrick 
 *
 */
public class Part1 {

	private final static String BRACKETS_REGEX = "\\[(.*?)\\]";
		public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		String input = Util.getStringFromFile(new File("input7.txt"));
		String[] instructions = input.split("\n");
		Pattern pattern = Pattern.compile(BRACKETS_REGEX);
		
		int count = 0;
		
		for (int i = 0; i < instructions.length; i++) {
			
			String instruction = instructions[i];
			
			if(instruction.isEmpty())
				continue;
			
			/** Check out hypernet **/
			Matcher matcher = pattern.matcher(instruction);
			
			boolean skipRecord = false;
			
            while (matcher.find()) {
            	String tls = matcher.group();
                
              	/** rule 2 - can't have a valid consuctive characters **/
            	if(isValidinput(tls)){
            		skipRecord = true;
            		continue;
            	}
            	
            }
            
            if(skipRecord)
            	continue;
			
            if(isValidinput(instruction)){
            	System.out.println(instruction);
            	count++;
            }
		}

		
		System.out.println("Number of Valid IPs: " + count);

	}

		public static boolean isValidinput(String str){
			char[] letters = str.toCharArray();
			
			char prevPrevChar = '%';
			char prevChar = '%';
			
			for(int index = 0; index < letters.length; index++){
				char currentChar = letters[index];
				if(prevPrevChar != '%' && prevChar != '%' && index + 1 < letters.length){
					char nextChar = letters[index + 1];
					
					if(prevChar == currentChar && prevPrevChar == nextChar && prevChar != prevPrevChar)
						return true;
				}
				
				prevPrevChar = prevChar;
				prevChar = currentChar;	
			}
			
			return false;
		}
	
	
}