package com.patrickmcdonald.advent.day2;


import java.io.File;
import java.io.IOException;

import com.patrickmcdonald.util.Util;


/**
 * 
You finally arrive at the bathroom (it's a several minute walk from the lobby so visitors can behold the many fancy conference rooms and water coolers on this floor) and go to punch in the code. Much to your bladder's dismay, the keypad is not at all like you imagined it. Instead, you are confronted with the result of hundreds of man-hours of bathroom-keypad-design meetings:

    1
  2 3 4
5 6 7 8 9
  A B C
    D

You still start at "5" and stop when you're at an edge, but given the same instructions as above, the outcome is very different:

    You start at "5" and don't move at all (up and left are both edges), ending at 5.
    Continuing from "5", you move right twice and down three times (through "6", "7", "B", "D", "D"), ending at D.
    Then, from "D", you move five more times (through "D", "B", "C", "C", "B"), ending at B.
    Finally, after five more moves, you end at 3.

 * @author Patrick McDonald
 *
 */
public class Part2 {

	private static char[][] grid = 
			 new char[][]{{'X','X','1','X','X'}
						,{'X','2','3','4','X'},
						 {'5','6','7','8','9'},
						 {'X','A','B','C','X'},
						 {'X','X','D','X','X'}};

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

    	String input = Util.getStringFromFile(new File("input2.txt"));
        String[] instruction = input.split("\n");

    	int currentX = 2;
    	int currentY = 0;
    	
    	
        for (String each : instruction) {
            String temp = each.trim();
           
            if(temp.isEmpty())
            	continue;
            
            
            for(char chr : temp.toCharArray()){
            	
                Move currentMove = Move.valueOf(Character.toString(chr));
                
                /** out of bounds move continue **/
                if(((currentX + currentMove.dx) > grid.length - 1) || (currentX + currentMove.dx) < 0 || (currentY + currentMove.dy) < 0  || ((currentY + currentMove.dy) > grid.length - 1))
                	continue;
                
                /** Out of bounds move, continue **/
                if(grid[currentY + currentMove.dy][currentX + currentMove.dx] == 'X')
                	continue;
                
                currentX += currentMove.dx;
                currentY += currentMove.dy;
                
            }
            System.out.print(grid[currentY][currentX]);
        }
        System.out.println();
     
    }
}