package com.patrickmcdonald.advent.day11;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.patrickmcdonald.util.Queue;
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

	
	
	static final String[] DIGITS = { "first", "second", "third", "forth" };
	static Map<String, Integer> _headers = new Hashtable<String, Integer>();
	static int _headerIndex = 0;
	static String[][] _floors = new String[4][4]; // 4 floors, 4 columns
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		String input = Util.getStringFromFile(new File("input11.txt"));
		String[] instructions = input.split("\n");

		for (int i = 0; i < _floors.length; i++) {
			for (int j = 0; j < _floors[i].length; j++) {
				_floors[i][j] = "**";
			}
		}

		/**
		 * The first floor contains a hydrogen-compatible microchip and a
		 * lithium-compatible microchip. The second floor contains a hydrogen
		 * generator. The third floor contains a lithium generator. The fourth
		 * floor contains nothing relevant.
		 */
		for (String instruction : instructions) {
			if (instruction.isEmpty())
				continue;

		//	parseInstruction(instruction);
		}
				
		_floors = new String[][]{{"**","**","**","**","**","**","**","**","**","**"},
								{"**","**","**","**","**","**","ZG","ZM","RG","RM"},
								{"**","**","PM","**","**","SM","**","**","**","**"},
								{"TG","TM","**","PG","SG","**","**","**","**","**"}};

		movePieces(_floors);
	}
	private static void movePieces(String[][] floor){
			Queue<State> queue = new Queue<State>();
			
			String[] currentFloor = floor[0];
			
			Set<State> seen = new HashSet<State>();
			queue.enqueue(new State(0, 0, currentFloor));
			
			seen.add(queue.peek());
			    
		    int prevSteps = 0;
		    
		    while (queue.size() > 0)
		    {
		        State curState = queue.dequeue();
		 
		        for (int idir = 0; idir < 2; idir++)
		        {
		            int dir = idir == 0 ? 1 : -1;
		            
		            if (curState.getFloor() == 0 && dir == -1)
		                continue;
		            
		            if (curState.getFloor() == 3 && dir == 1)
		                continue;
		 
		            for (int i = 0; i < currentFloor.length - 1; i++)
		            {
		                for (int j = i + 1; j < currentFloor.length; j++)
		                {
		                    State newState = curState.move(dir, floor, i, j);
		                    
		                    if (newState != null && seen.add(newState))
		                    {
		                    	if(newState.getFloor() == 3 && onTopFloor(newState.getFloorItems())){
		                    	       System.out.println(newState.getMoves());
		                    		return;
		                    	}
		                        if (newState.getMoves() > prevSteps)
		                        {
		                            prevSteps = newState.getMoves();
		                            System.out.println(prevSteps);
		                        }
		                        
		                        queue.enqueue(newState);
		                    }
		                }
		            }
		        }
		    }
	}
	
	private static void parseInstruction(String instruction) {
		String[] words = instruction.split("\\s+");

		int floor = getNumber(words[1]);

		String type = words[4];

		if (type.equals("a")) {

			String[] pieces = instruction.split(" a ");

			/** 2 on this floor **/
			if (pieces.length > 2) {
				String[] piece = pieces[1].replace("-compatible", "").replace("and", "").replace(".", "").trim()
						.split(" ");
				String[] piece2 = pieces[2].replace("-compatible", "").replace("and", "").replace(".", "").trim()
						.split(" ");
				String heading = piece[0].substring(0, 1).toUpperCase() + "" + piece[1].substring(0, 1).toUpperCase();
				_floors[floor][_headerIndex] = heading;
				_headers.put(heading, _headerIndex++);
				String heading2 = piece2[0].substring(0, 1).toUpperCase() + ""
						+ piece2[1].substring(0, 1).toUpperCase();
				_floors[floor][_headerIndex+2] = heading2;
				_headers.put(heading2, _headerIndex+2);
			} else {

				String[] piece = pieces[1].replace("-compatible", "").replace("and", "").replace(".", "").trim()
						.split(" ");
				String heading = piece[0].substring(0, 1).toUpperCase() + "" + piece[1].substring(0, 1).toUpperCase();
				_floors[floor][_headerIndex] = heading;
				_headers.put(heading, _headerIndex++);
				
				
			}

		}

	}
	public static boolean onTopFloor(List<String> list){
		String[] topFloor = _floors[_floors.length-1];

		for(String col : topFloor){
			if(col.equals("**"))
				return false;
		}
		
		return true;
	}
	public static void displayfloors() {
		
		List<Map.Entry<String, Integer>> headers = getSortedMap(_headers);
		System.out.print("F# | ");
		for (Entry<String, Integer> heading : headers) {
			System.out.print(heading.getKey() + "  ");
		}

		System.out.println();
		for (int i = 0; i < 24; i++) {
			System.out.print("-");
		}
		System.out.println();
		for (int i =  _floors.length - 1; i > -1; i--) {
			System.out.print("F"+(i+1)+" | ");
			for (int j = 0; j < _floors[i].length; j++) {
				System.out.print(_floors[i][j]);
				System.out.print("  ");

			}
			System.out.println();
		}
	}


	public static List<Map.Entry<String, Integer>> getSortedMap(Map<String, Integer> _headers2) {
		ArrayList<Map.Entry<String, Integer>> l = new ArrayList(_headers2.entrySet());
		Collections.sort(l, new Comparator<Map.Entry<String, Integer>>() {

			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});

		return l;
	}

	public static int getNumber(String word) {
		for (int k = 0; k < DIGITS.length; k++) {
			if (word.equals(DIGITS[k])) {
				return k;
			}
		}
		return -1;
	}
}

class State {
    private int _floor;
    private int _moves;
    private List<String> _items;
    
    
   public int getFloor(){
	   return _floor;
   }
   
   public int getMoves(){
	   return _moves;
   }
   
   public List<String> getFloorItems(){
	   return _items;
   }
   
   public State(int floor, int moves, String[] items) {
       this._floor = floor;
       this._moves = moves;
       this._items = Arrays.asList(items);
    }
    
    public State move(int dir, String[][] floors, int itemIndex, int itemIndex2){

    	List<String> newFloorItems = new LinkedList<String>();
    	
    	int floor = this._floor + dir;
    	
    	String item1 = floors[this._floor][itemIndex];
    	String item2 = floors[this._floor][itemIndex2];
    	
    	// One is microchip, other is generator, and they are not of same kind
    	if(item1.charAt(0) != item2.charAt(0) && item1.charAt(1) != item2.charAt(1))
    			return null;
    	
    	
    	String[] nextFloorItems = floors[floor];
    	
    	boolean found = false;
    	//Items not on floor
    	for(int i = 0; i < nextFloorItems.length; i++){
    		String nextFloorItem = nextFloorItems[i];
    		
    		/** Matched a Generator/Chip to the same type **/
    		if(nextFloorItem.charAt(0) == item1.charAt(0) || nextFloorItem.charAt(1) != item1.charAt(1)){
    			newFloorItems.add(item1);
    			found = true;
    		}else 
    			newFloorItems.add(nextFloorItem);
    	}

    	/** Can't Move **/
    	if(!found)
    		return null;
    
    	return new State(floor, _moves +1, newFloorItems.toArray(new String[newFloorItems.size()]));
    }
}
