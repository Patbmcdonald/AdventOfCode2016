package com.patrickmcdonald.advent.day15;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import com.patrickmcdonald.util.Util;

/**
 * --- Day 15: Timing is Everything ---
 * 
 * The halls open into an interior plaza containing a large kinetic sculpture.
 * The sculpture is in a sealed enclosure and seems to involve a set of
 * identical spherical capsules that are carried to the top and allowed to
 * bounce through the maze of spinning pieces.
 * 
 * Part of the sculpture is even interactive! When a button is pressed, a
 * capsule is dropped and tries to fall through slots in a set of rotating discs
 * to finally go through a little hole at the bottom and come out of the
 * sculpture. If any of the slots aren't aligned with the capsule as it passes,
 * the capsule bounces off the disc and soars away. You feel compelled to get
 * one of those capsules.
 * 
 * The discs pause their motion each second and come in different sizes; they
 * seem to each have a fixed number of positions at which they stop. You decide
 * to call the position with the slot 0, and count up for each position it
 * reaches next.
 * 
 * Furthermore, the discs are spaced out so that after you push the button, one
 * second elapses before the first disc is reached, and one second elapses as
 * the capsule passes from one disk to the one below it. So, if you push the
 * button at time=100, then the capsule reaches the top disc at time=101, the
 * second disc at time=102, the third disc at time=103, and so on.
 * 
 * The button will only drop a capsule at an integer time - no fractional
 * seconds allowed.
 * 
 * For example, at time=0, suppose you see the following arrangement:
 * 
 * Disc #1 has 5 positions; at time=0, it is at position 4. Disc #2 has 2
 * positions; at time=0, it is at position 1.
 * 
 * If you press the button exactly at time=0, the capsule would start to fall;
 * it would reach the first disc at time=1. Since the first disc was at position
 * 4 at time=0, by time=1 it has ticked one position forward. As a five-position
 * disc, the next position is 0, and the capsule falls through the slot.
 * 
 * Then, at time=2, the capsule reaches the second disc. The second disc has
 * ticked forward two positions at this point: it started at position 1, then
 * continued to position 0, and finally ended up at position 1 again. Because
 * there's only a slot at position 0, the capsule bounces away.
 * 
 * If, however, you wait until time=5 to push the button, then when the capsule
 * reaches each disc, the first disc will have ticked forward 5+1 = 6 times (to
 * position 0), and the second disc will have ticked forward 5+2 = 7 times (also
 * to position 0). In this case, the capsule would fall through the discs and
 * come out of the machine.
 * 
 * However, your situation has more than two discs; you've noted their positions
 * in your puzzle input. What is the first time you can press the button to get
 * a capsule?
 * 
 * @author Patrick McDonald
 *
 */
public class Part1 {

	private static Disc[] _discs;

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		String input = Util.getStringFromFile(new File("input15.txt")).trim();
		String[] instructions = input.split("\n");

		_discs = new Disc[instructions.length];

		for (int i = 0; i < instructions.length; i++) {
			String instruction = instructions[i];
			String[] pieces = instruction.split("\\s+");
			_discs[i] = new Disc(Integer.parseInt(pieces[3]), Integer.parseInt(pieces[11].replace(".", "")));
		}

		boolean capsuleWillBounce = true;

		int currentTime = 0;
		
		while (capsuleWillBounce) {

			boolean finished = true;

			/** get the first disc **/
			int first = _discs[0].checkMove(currentTime);
		
			for (int i = 1; i < _discs.length; i++) {
				
				/** get the next disc **/
				int currentPos = _discs[i].checkMove(currentTime + i);

				if (finished) {
					/** is our first disc is not a 0 or first does not equal current POS **/
					if (first != 0 || first != currentPos)
						finished = false;
				}
			}
			
			if (finished)
				capsuleWillBounce = false;
			else
				currentTime++;
		}

		System.out.println("At Time [" + (currentTime - 1) + "] is when you should press the button.");
	}
}

class Disc {
	private int _currentPos;
	private int _maxPos;
	
	public Disc(int maxPos, int currentPos) {
		this._currentPos = currentPos;
		this._maxPos = maxPos;
	}

	public int checkMove(int time) {
		return (_currentPos + time) % _maxPos;
	}
	
}