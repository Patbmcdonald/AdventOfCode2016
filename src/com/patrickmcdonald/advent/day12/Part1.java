package com.patrickmcdonald.advent.day12;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import com.patrickmcdonald.util.Util;

public class Part1 {

	private static int[] register = new int[4];

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		String input = Util.getStringFromFile(new File("input12.txt"));
		String[] instructions = input.split("\n");

		int currentInstructionIndex = 0;

		for (currentInstructionIndex = 0; currentInstructionIndex < instructions.length;) {
			String currentInstruction = instructions[currentInstructionIndex];

			if (currentInstruction.isEmpty()) {
				currentInstructionIndex++;
				continue;
			}
			currentInstructionIndex = parseInstruction(currentInstruction, currentInstructionIndex);
		}

		System.out.println("A: " + register[getRegister('a')]);

	}

	private static int parseInstruction(String currentInstruction, int currentIndex) {

		StringTokenizer stringTokenizer = new StringTokenizer(currentInstruction, " ");

		String opCode = stringTokenizer.nextToken();
		String x = stringTokenizer.nextToken();

		switch (opCode) {
		case "cpy":

			String y = stringTokenizer.nextToken();

			int yRegisterIndex = getRegister(y.charAt(0));

			if (Util.isDigit(x))
				register[yRegisterIndex] = Integer.parseInt(x);
			else
				register[yRegisterIndex] = register[getRegister(x.charAt(0))];

			break;
		case "inc":
			register[getRegister(x.charAt(0))] += 1;
			break;
		case "dec":
			register[getRegister(x.charAt(0))] -= 1;
			break;
		case "jnz":
			String toSpot = stringTokenizer.nextToken();

			int value = -1;

			if (Util.isDigit(x))
				value = Integer.parseInt(x);
			else
				value = register[getRegister(x.charAt(0))];

			if (value != 0)
				return currentIndex + Integer.parseInt(toSpot);

			break;
		}

		return currentIndex + 1;
	}

	public static int getRegister(char chr) {
		return Character.getNumericValue(chr) - 10;
	}
}
