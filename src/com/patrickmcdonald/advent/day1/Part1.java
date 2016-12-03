package com.patrickmcdonald.advent.day1;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.patrickmcdonald.util.Pair;
import com.patrickmcdonald.util.Util;

/**
 * The Document indicates that you should start at the given coordinates (where you just landed) and face North. Then, follow the provided sequence: either turn left (L) or right (R) 90 degrees, then walk forward the given number of blocks, ending at a new intersection.

There's no time to follow such ridiculous instructions on foot, though, so you take a moment and work out the destination. Given that you can only walk on the street grid of the city, how far is the shortest path to the destination?

For example:

    Following R2, L3 leaves you 2 blocks East and 3 blocks North, or 5 blocks away.
    R2, R2, R2 leaves you 2 blocks due South of your starting position, which is 2 blocks away.
    R5, L5, R5, R3 leaves you 12 blocks away
    
 * @author Patrick McDonald
 *
 */
public class Part1 {

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

				visited.add(new Pair<Integer, Integer>(currentX, currentY));
			}

		}

		System.out.println("Distance: " + Util.getDistance(0, 0, currentX, currentY));
	}
}