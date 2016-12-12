package com.patrickmcdonald.advent.day10;

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
 * --- Day 9: Explosives in Cyberspace ---
 * 
 * Wandering around a secure area, you come across a datalink port to a new part
 * of the network. After briefly scanning it for interesting files, you find one
 * file in particular that catches your attention. It's compressed with an
 * experimental format, but fortunately, the documentation for the format is
 * nearby.
 * 
 * The format compresses a sequence of characters. Whitespace is ignored. To
 * indicate that some sequence should be repeated, a marker is added to the
 * file, like (10x2). To decompress this marker, take the subsequent 10
 * characters and repeat them 2 times. Then, continue reading the file after the
 * repeated data. The marker itself is not included in the decompressed output.
 * 
 * If parentheses or other characters appear within the data referenced by a
 * marker, that's okay - treat it like normal data, not a marker, and then
 * resume looking for markers after the decompressed section.
 * 
 * For example:
 * 
 * ADVENT contains no markers and decompresses to itself with no changes,
 * resulting in a decompressed length of 6. A(1x5)BC repeats only the B a total
 * of 5 times, becoming ABBBBBC for a decompressed length of 7. (3x3)XYZ becomes
 * XYZXYZXYZ for a decompressed length of 9. A(2x2)BCD(2x2)EFG doubles the BC
 * and EF, becoming ABCBCDEFEFG for a decompressed length of 11. (6x1)(1x3)A
 * simply becomes (1x3)A - the (1x3) looks like a marker, but because it's
 * within a data section of another marker, it is not treated any differently
 * from the A that comes after it. It has a decompressed length of 6.
 * X(8x2)(3x3)ABCY becomes X(3x3)ABC(3x3)ABCY (for a decompressed length of 18),
 * because the decompressed data from the (8x2) marker (the (3x3)ABC) is skipped
 * and not processed further.
 * 
 * What is the decompressed length of the file (your puzzle input)? Don't count
 * whitespace.
 * 
 * Your puzzle answer was 99145.
 * 
 * @author Patrick
 *
 */
public class Part1 {

	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		String input = Util.getStringFromFile(new File("input10.txt"));
		String[] instructions = input.split("\n");
		List<String> _valueInstructions = new ArrayList<String>();
		List<String> _movementInstructions = new ArrayList<String>();
		Bot[] bots = new Bot[300];
		int[]  outputs = new int[300];
		
		for(int i = 0; i < bots.length; i++){
			bots[i] = new Bot(i);
			outputs[i] = -1;
		}
		
		for (String instruction : instructions) {
			if (instruction.isEmpty())
				continue;

			if(instruction.startsWith("value"))
				_valueInstructions.add(instruction);
			else
				_movementInstructions.add(instruction);
		}

		processValueInstructions(_valueInstructions, bots);

		processMovementInstructions(_movementInstructions, bots, outputs);
		
		
		for(Bot b : bots){
			
			int high = b.getHigh() ;
			int low = b.getLow() ;
			if(high != -1 || low != -1){
				//System.out.println("B: "+ b.getNumber() + " high: "+ high + " low: "+ low);
			}
		}
		
		
		
		for(int i = 0; i < outputs.length; i++){
			int output = outputs[i];
			
			if(output != -1 ){
				System.out.println("#: "+ i + " output: "+ output );
			}
		}
		
	}

	private static void processMovementInstructions(List<String> _movementInstructions, Bot[] bots, int[] output) {
		// bot 2 gives low to bot 1 and high to bot 0
		for(String line : _movementInstructions){
			String[] pieces = line.split("\\s+");
			int botNumber = Integer.parseInt(pieces[1]);
			
			Bot bot = bots[botNumber];
	           
			if(bot.getSize() >= 2){
				int high = bot.getHigh();
				int low = bot.getLow();

				if(high == 61 || low == 61) //puzzle input here
                    if(low == 17 || high == 17) //puzzle input here
                        System.out.println(bot.getNumber()); //print its id
				
				String giveType = pieces[3];
				String giveOutputType = pieces[5];
				int giveOutputNumber = Integer.parseInt(pieces[6]);
				
				if(giveOutputType.equals("bot"))
					bots[giveOutputNumber].addValue((giveType.equals("low") ? low : high));
				else 
					output[giveOutputNumber] = giveType.equals("low") ? low : high;
				
				
				String giveType2 = pieces[8];
				String giveOutputType2 = pieces[10];
				int giveOutputNumber2 = Integer.parseInt(pieces[11]);

				if(giveOutputType2.equals("bot"))
					bots[giveOutputNumber2].addValue((giveType2.equals("low") ? low : high));
				else 
					output[giveOutputNumber2] = giveType2.equals("low") ? low : high;
				}
			
		}
		
	}

	private static void processValueInstructions(List<String> _valueInstructions, Bot[] bots) {
		// value 5 goes to bot 2
		
		for(String line : _valueInstructions){
			String[] pieces = line.split("\\s+");
			int value = Integer.parseInt(pieces[1]);
			int botNumber = Integer.parseInt(pieces[5]);
			bots[botNumber].addValue(value);
		}
	}
	
}

class Bot {
	private int _number = -1;
	private List<Integer> _values;
	public Bot(int number){
		this._number = number;
		this. _values = new ArrayList<Integer>();
	}
	
	public void addValue(int number){
		_values.add(number);
	}
	
	public int getHigh(){
		return getNumber(false);
	}
	
	public int getLow(){
		return getNumber(true);
	}
		
	public int getSize(){
		return _values.size();
	}
	private int getNumber(boolean low){
		int currentValue = (low ? 99999 : -1);
		
		int currentIndex = -1;
		
		for(int index = 0; index < _values.size(); index++){
			int value = _values.get(index);
			if(low){
				if(value < currentValue){
					currentValue = value;
					currentIndex = index;
				}
			}else 
				if(value > currentValue){
					currentValue = value;
					currentIndex = index;
				}
		}
		
		if(currentIndex != -1)
			_values.remove(currentIndex);
		
		return currentValue != 99999 ? currentValue : -1;
	}
	public int getNumber(){
		return _number;
	}
	
	public boolean isConfiguration(int low, int high){
		return (getLow() == low && getHigh() == high);
	}
}