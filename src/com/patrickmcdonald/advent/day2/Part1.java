package com.patrickmcdonald.advent.day2;


import java.io.File;
import java.io.IOException;

import com.patrickmcdonald.util.Util;

/**
--- Day 2: Bathroom Security ---

You arrive at Easter Bunny Headquarters under cover of darkness. However, you left in such a rush that you forgot to use the bathroom! Fancy office buildings like this one usually have keypad locks on their bathrooms, so you search the front desk for the code.

"In order to improve security," the document you find says, "bathroom codes will no longer be written down. Instead, please memorize and follow the procedure below to access the bathrooms."

The document goes on to explain that each button to be pressed can be found by starting on the previous button and moving to adjacent buttons on the keypad: U moves up, D moves down, L moves left, and R moves right. Each line of instructions corresponds to one button, starting at the previous button (or, for the first line, the "5" button); press whatever button you're on at the end of each line. If a move doesn't lead to a button, ignore it.

You can't hold it much longer, so you decide to figure out the code as you walk to the bathroom. You picture a keypad like this:

1 2 3
4 5 6
7 8 9

Suppose your instructions are:

ULL
RRDDD
LURDL
UUUUD

    You start at "5" and move up (to "2"), left (to "1"), and left (you can't, and stay on "1"), so the first button is 1.
    Starting from the previous button ("1"), you move right twice (to "3") and then down three times (stopping at "9" after two moves and ignoring the third), ending up with 9.
    Continuing from "9", you move left, up, right, down, and left, ending with 8.
    Finally, you move up four times (stopping at "2"), then down once, ending with 5.


 * @author Patrick McDonald
 *
 */
public class Part1 {

    enum Move {
        U(0,-1), D(0,1), L(-1,0), R(1,0);

        int dx;
        int dy;

        Move(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    public static void main(String[] args) throws IOException {

    	int[][] grid = new int[3][3];
    	
    	int current = 1; 
    	
    	/** generate grid **/
    	for(int i = 0; i < grid.length; i++){
    		for( int k = 0; k < grid[i].length; k++){
    				grid[i][k] = current;
    				current++;
    		}
    	}

       
    	int currentX = 1;
    	int currentY = 1;
    	
    	String input = Util.getStringFromFile(new File("input2.txt"));
        String[] instruction = input.split("\n");
        
        for (String each : instruction) {
            String temp = each.trim();
           
            if(temp.isEmpty())
            	continue;
            
            for(char chr : temp.toCharArray()){
                Move currentMove = Move.valueOf(Character.toString(chr));
                
                if(((currentX + currentMove.dx) > grid.length - 1) || (currentX + currentMove.dx) < 0 || (currentY + currentMove.dy) < 0  || ((currentY + currentMove.dy) > grid.length - 1))
                	continue;
                
                currentX += currentMove.dx;
                currentY += currentMove.dy;
                
            }
            
            System.out.print(grid[currentY][currentX]);
        }
        
    }
}