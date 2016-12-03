package com.patrickmcdonald.advent.day1;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.patrickmcdonald.util.Pair;
import com.patrickmcdonald.util.Util;

/**
 * - Part Two ---

Then, you notice the instructions continue on the back of the Recruiting Document. Easter Bunny HQ is actually at the first location you visit twice.

For example, if your instructions are R8, R4, R4, R8, the first location you visit twice is 4 blocks away, due East.

How many blocks away is the first location you visit twice?

 * @author Patrick McDonald
 *
 */
public class Part2 {

	enum Move {
		NORTH(0,-1,"WEST","EAST"), EAST(1,0,"NORTH","SOUTH"), SOUTH(0,1,"EAST","WEST"), WEST(-1,0,"SOUTH","NORTH");

		private int _dx;
		private int _dy;
		private String _left; 
		private String _right;


		Move (int dx, int dy, String left, String right) {
			this._dx = dx;
			this._dy = dy;
			this._left = left;
			this._right = right;
		}
		
		public Move getLeftMove(){
			return Move.valueOf(_left);
		}
		
		public Move getRightMove(){
			return Move.valueOf(_right);
		}

		public int getX() {
			return _dx;
		}

		public int getY() {
			return _dy;
		}
	}


	public static void main(String[] args) throws IOException {

		Set<Pair<Integer, Integer>> visited = new HashSet<Pair<Integer, Integer>>();

		/** Starting Point **/
		Move current = Move.NORTH;
		int currentX = 0;
		int currentY = 0;
		String input = Util.getStringFromFile(new File("input1.txt"));

		String[] instructions = input.split(",");

		visited.add(new Pair<Integer, Integer>(currentX, currentY));
		
		boolean foundFlag = false;
		
		for(int i = 0; i < instructions.length; i++){

			String instruction = instructions[i].trim();

			if(instruction.isEmpty())
				continue;

			if (instruction.startsWith("R")) 
				current = current.getRightMove();
			else 
				current = current.getLeftMove();


			int dist = Integer.parseInt(instruction.substring(1));

			for (int j = 0; j < dist; j++) {

				currentX += current.getX();
				currentY += current.getY();
				
				Pair<Integer, Integer> pair = new Pair<Integer, Integer>(currentX, currentY);
				
				if(visited.contains(pair)){
					foundFlag = true;
					break;
				} else {
					visited.add(pair);
				}
			}
			
			if(foundFlag)
				break;
		}

		System.out.println("Distance: " + Util.getDistance(0, 0, currentX, currentY));
	}
}