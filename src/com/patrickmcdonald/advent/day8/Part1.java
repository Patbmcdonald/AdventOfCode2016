package com.patrickmcdonald.advent.day8;

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
 * --- Day 8: Two-Factor Authentication ---
 * 
 * You come across a door implementing what you can only assume is an
 * implementation of two-factor authentication after a long game of requirements
 * telephone.
 * 
 * To get past the door, you first swipe a keycard (no problem; there was one on
 * a nearby desk). Then, it displays a code on a little screen, and you type
 * that code on a keypad. Then, presumably, the door unlocks.
 * 
 * As you can see, this display technology is extremely powerful, and will soon
 * dominate the tiny-code-displaying-screen market. That's what the
 * advertisement on the back of the display tries to convince you, anyway.
 * 
 * There seems to be an intermediate check of the voltage used by the display:
 * after you swipe your card, if the screen did work, how many pixels should be
 * lit?
 * 
 * 
 * @author Patrick
 *
 */
public class Part1 {

	private static char[][] _screen = new char[6][50];

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		String input = Util.getStringFromFile(new File("input8.txt"));
		String[] instructions = input.split("\n");

		/** Set the screen to off **/
		for (int i = 0; i < _screen.length; i++) {
			for (int j = 0; j < _screen[i].length; j++) {
				_screen[i][j] = ' ';
			}
		}
		
		
		for (int i = 0; i < instructions.length; i++) {

			String instruction = instructions[i];

			if (instruction.isEmpty())
				continue;

			if (instruction.startsWith("rect")) {
				drawRect(instruction);
			} else if (instruction.startsWith("rotate")) {
				rotateRect(instruction);
			}
		}
		
		/** Part 1 **/
		System.out.println("Number of Valid Pixels: " + getOnCount());
		
		/** Part 2 **/
		printScreen();
	}
	
	/**
	 * Rotate Rectangle 
	 * @param input
	 */
	private static void rotateRect(String input) {
		String[] pieces = input.split("\\s+");

		String type = pieces[1];
		int starting = Integer.parseInt(pieces[2].split("=")[1]);
		int ending = Integer.parseInt(pieces[4]);

		if (type.equals("row")) {

			for (int i = 0; i < ending; i++) {
				
				 char thisValue = _screen[starting][_screen[starting].length-1];
				 
				for (int j = _screen[starting].length - 1; j > 0; j--) {
					_screen[starting][j] = _screen[starting][j - 1];
				}
				
				_screen[starting][0] = thisValue;
			}

		} else {
			
			for (int i = 0; i < ending; i++) {
				char thisValue = _screen[_screen.length - 1][starting];
				
				for (int j = _screen.length - 1; j > 0; j--) {
					_screen[j][starting] = _screen[j - 1][starting];
				}
				_screen[0][starting] = thisValue;
			}

		}

	}

	/**
	 * Draw Rect
	 * @param input
	 */
	private static void drawRect(String input) {
		String[] pieces = input.split("\\s+");

		String[] types = pieces[1].split("x");

		int x = Integer.parseInt(types[0]);
		int y = Integer.parseInt(types[1]);

		for (int i = 0; i < x; i++) {
			for (int k = 0; k < y; k++) {
				_screen[k][i] = '*';
			}
		}

	}

	/** 
	 *  Get On Count 
	 * @return
	 */
	public static int getOnCount() {
		int count = 0;
		
		for (int i = 0; i < _screen.length; i++) {
			for (int j = 0; j < _screen[i].length; j++) {
				
				if(_screen[i][j] == '*')
					count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Print Screen 
	 * 
	 */
	public static void printScreen() {
		for (int i = 0; i < _screen.length; i++) {
			for (int j = 0; j < _screen[i].length; j++) {
				System.out.print(_screen[i][j]);
			}
			System.out.println();
		}
	}
}