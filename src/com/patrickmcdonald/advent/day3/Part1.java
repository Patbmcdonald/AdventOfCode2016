package com.patrickmcdonald.advent.day3;


import java.io.File;
import java.io.IOException;

import com.patrickmcdonald.util.Trie;
import com.patrickmcdonald.util.Util;

/**
--- Day 3: Squares With Three Sides ---

Now that you can think clearly, you move deeper into the labyrinth of hallways and office furniture that makes up this part of Easter Bunny HQ. This must be a graphic design department; the walls are covered in specifications for triangles.

Or are they?

The design document gives the side lengths of each triangle it describes, but... 5 10 25? Some of these aren't triangles. You can't help but mark the impossible ones.

In a valid triangle, the sum of any two sides must be larger than the remaining side. For example, the "triangle" given above is impossible, because 5 + 10 is not larger than 25.

In your puzzle input, how many of the listed triangles are possible?


 * @author Patrick McDonald
 *
 */
public class Part1 {

 
    public static void main(String[] args) throws IOException {

    	String input = Util.getStringFromFile(new File("input3.txt"));
        String[] instructions = input.split("\n");
        
        int count = 0;
        
        for(int i = 0; i < instructions.length; i++){
        	String instruction = instructions[i];
        	
        	if(instruction.isEmpty())
        		continue;

        	String[] parts = instruction.split("\\s+");

        	if(isValidTriangle(new Trie<Integer,Integer,Integer>(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]), Integer.parseInt(parts[3])))){
        		count++;
        	}
        	
        }
        System.out.println("Valid amount of triangles: " + count);
    }
    
	public static boolean isValidTriangle(Trie<Integer, Integer, Integer> p){
		
		if((p.getFirst() + p.getSecond()) > p.getThird() && 
		   (p.getThird() + p.getSecond()) > p.getFirst() &&
		   (p.getThird() + p.getFirst()) > p.getSecond())
		   return true;
				   
		return false;
	}

}