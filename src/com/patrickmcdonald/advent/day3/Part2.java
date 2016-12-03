package com.patrickmcdonald.advent.day3;


import java.io.File;
import java.io.IOException;

import com.patrickmcdonald.util.Trie;
import com.patrickmcdonald.util.Util;

/**


--- Part Two ---

Now that you've helpfully marked up their design documents, it occurs to you that triangles are specified in groups of three vertically. Each set of three numbers in a column specifies a triangle. Rows are unrelated.

For example, given the following specification, numbers with the same hundreds digit would be part of the same triangle:

101 301 501
102 302 502
103 303 503
201 401 601
202 402 602
203 403 603

In your puzzle input, and instead reading by columns, how many of the listed triangles are possible?


 * @author Patrick McDonald
 *
 */
public class Part2 {


    public static void main(String[] args) throws IOException {

    	String input = Util.getStringFromFile(new File("input3.txt"));
        String[] instructions = input.split("\n");
        
        int[][] pieces = new int[3][3];
        int currentInsertionCount = 0;
        int count =0;
        
    
        for(int i = 0; i < instructions.length; i++){
        
        	String instruction = instructions[i].trim();
        	if(instruction.isEmpty())
        		continue;
        	
        	String[] parts =instruction.trim().split("\\s+");
        	
          	currentInsertionCount++;
          	
          	/** pivot data and convert to int **/
        	for(int k = 0; k < 3; k++)
        		pieces[k][currentInsertionCount - 1] = Integer.parseInt(parts[k]);

      
        	/** Every 3rd row, calculate the rows **/
        	if(i>0 && i % 3 == 0){

        		currentInsertionCount = 0;
    	
        		for(int j = 0; j < 3; j++){
        		    StringBuffer output = new StringBuffer();
        			output.append("Transformed Input: ").append(pieces[j][0]).append(" ").append(pieces[j][1]).append(" ").append(pieces[j][2]);
            		
        			System.out.println(output.toString());
            		
        			if(isValidTriangle(new Trie<Integer,Integer,Integer>(pieces[j][0], pieces[j][1], pieces[j][2]))){
        				count++;
        			}
        			
        		}
    
        		currentInsertionCount = 0;
        	}
        		
        }
        
        System.out.println("Valid amount of triangles: " + count);
    }
    
    
   	/**
   	 * Is a valid triangle?
   	 * 
   	 * @param p
   	 * @return
   	 */
   	public static boolean isValidTriangle(Trie<Integer, Integer, Integer> p){
   		if((p.getFirst() + p.getSecond()) > p.getThird() && 
   		   (p.getThird() + p.getSecond()) > p.getFirst() &&
   		   (p.getThird() + p.getFirst()) > p.getSecond())
   		   return true;
   				   
   		return false;
   	}

}