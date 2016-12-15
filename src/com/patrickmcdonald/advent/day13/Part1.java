package com.patrickmcdonald.advent.day13;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Part1 {

	private static final int SALT = 10;
	private static int gridWidth = 10;
	private static int gridHeight = 7;
	private static int[][] validMoves = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

	private static Node[][] _grid = new Node[gridHeight][gridWidth];

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		for (int y = 0; y < _grid.length; y++) {
			for (int x = 0; x < _grid[y].length; x++) {
				_grid[y][x] = new Node(x, y, (isWall(x, y) ? '#' : '.'));
			}
		}


		System.out.println("Shortest Path Steps: " + computeAStarPath( getNode(1, 1),  getNode(7, 4)).size());
		System.out.println("Shortest Path Steps: " + computeShortestBFSPath( getNode(1, 1), getNode(7, 4)).size());
	}

	/**
	 * BFS
	 * 
	 * @param source
	 * @param end
	 * @return
	 */
	public static List<Node> computeShortestBFSPath(Node source, Node target) {

		source.setChar('X');
		target.setChar('X');

		PriorityQueue<Node> queue = new PriorityQueue<Node>();

		Set<Node> visted = new HashSet<Node>();

		queue.add(source);
		visted.add(source);

		while (!queue.isEmpty()) {

			Node current = queue.poll();

			for (Node neighbour : current.getNeighbours()) {

				if (neighbour.isWalkable() && !visted.contains(neighbour)) {
					if (!queue.contains(neighbour)) {
						queue.add(neighbour);
						visted.add(neighbour);
						neighbour.setParent(current);
					}
				}
			}

		}

		Node node = target;

		/** calculate shortest path **/
		List<Node> path = new ArrayList<Node>();

		while (node.getParent() != null) {

			node.setChar('A');
			
			path.add(node);
			node = node.getParent();

			drawMaze();
			

		}

		return path;
	}

	public static List<Node> computeAStarPath(Node source, Node target) {

		source.setChar('X');
		target.setChar('X');

		/** Calculate Distance.... G and H **/

		for (int y = 0; y < _grid.length; y++) {
			for (int x = 0; x < _grid[y].length; x++) {

				Node currentNode = getNode(x, y);

				/** Distance from Current to End **/
				int h = (Math.abs(target.getX() - currentNode.getX()) + Math.abs(target.getY() - currentNode.getY()));

				/** Distance from (Current to Start) **/
				int g = (Math.abs(source.getX() - currentNode.getX()) + Math.abs(source.getY() - currentNode.getY()));

				currentNode.setG(g);
				currentNode.setH(h);

				/** calculate F **/
				currentNode.setF(g + h);

				for (int[] validDir : validMoves) {
					if (insideMaze(x + validDir[0], y + validDir[1])) {

						Node n = getNode(x + validDir[0], y + validDir[1]);

						currentNode.addNeighbor(n);
					}
				}
			}
		}

		List<Node> queue = new ArrayList<Node>();
		List<Node> visted = new ArrayList<Node>();

		queue.add(source);

		do {

			Node current = findClosestNode(queue);

			// System.out.println(current.getF());
			visted.add(current);
			queue.remove(current);

			if (current.equals(target)) {
				current.setChar('A');
				break;
			}

			drawMaze();
			
			/**
			 * check Neighbours, if walkable and we have not visited..
			 * recalculate H
			 **/
			for (Node neighbour : current.getNeighbours()) {

				if (neighbour.isWalkable() && !visted.contains(neighbour)) {
					if (!queue.contains(neighbour)) {
						queue.add(neighbour);
						neighbour.setParent(current);
					} else {
						int newG = current.getG();
						if (newG < neighbour.getG()) {
							neighbour.setG(newG);
							neighbour.setParent(current);
						}
					}
				}
			}

		} while (queue.size() > 0);

		Node node = target;

		/** calculate shortest path **/
		List<Node> path = new ArrayList<Node>();

		while (node.getParent() != null) {

			path.add(node);
			
			/** Mark as valid path **/
			node.setChar('A');

			
			drawMaze();
			node = node.getParent();
			



		}

		return path;
	}

	public static Node findClosestNode(List<Node> openList) {
		Node closest = openList.get(0);
		int lowestDistance = closest.getF();

		for (Node node : openList) {
			if (node.getF() < lowestDistance) {
				closest = node;
				lowestDistance = node.getF();
			}
		}

		return closest;
	}

	private static Node getNode(int x, int y) {
		return _grid[y][x];
	}

	private static boolean insideMaze(int x, int y) {
		return !(x < 0 || x >= gridWidth || y < 0 || y >= gridHeight);
	}

	private static void drawMaze() {
		for (int y = 0; y < _grid.length; y++) {
			for (int x = 0; x < _grid[y].length; x++) {
				Node node = getNode(x, y);
				if (node.isWalkable() && node.getData() != 'A' && node.getData() != '.' && node.getData() != '0'
						&& node.getData() != 'X')
					System.out.print(node.getF() + " ");
				else
					System.out.print(node.getData() + " ");
			}

			System.out.println();

		}
		System.out.println();
	}

	private static boolean isWall(int x, int y) {
		if (x < 0 || y < 0)
			return true;

		int number = x * x + 3 * x + 2 * x * y + y + y * y + SALT;

		String binary = Integer.toBinaryString(number);

		if (getNumberOfOnes(binary) % 2 != 0)
			return true;

		return false;
	}

	private static int getNumberOfOnes(String in) {
		int count = 0;

		for (char s : in.toCharArray())
			if (s == '1')
				count++;

		return count;
	}
}

class Node implements Comparable<Node> {

	private List<Node> _neighbors;
	private int _x;
	private int _y;
	private int _h;
	private int _f;
	private int _g;
	private char _letter;
	private Node _parent;

	public Node(int x, int y, char letter) {
		this._x = x;
		this._y = y;
		this._letter = letter;

		this._neighbors = new ArrayList<Node>();
	}

	public boolean isWalkable() {
		return this._letter != '#';
	}

	public void setF(int i) {
		_f = i;
	}

	public int getF() {
		return _f;
	}

	public void setG(int i) {
		_g = i;
	}

	public int getG() {
		return _g;
	}

	public Node getParent() {
		return _parent;
	}

	public void setParent(Node parent) {
		this._parent = parent;
	}

	public void addNeighbor(Node node) {
		this._neighbors.add(node);
	}

	public List<Node> getNeighbours() {
		return _neighbors;
	}

	public void setChar(char letter) {
		this._letter = letter;
	}

	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

	public void setH(int h) {
		this._h = h;
	}

	public int getH() {
		return _h;
	}

	public char getData() {
		return _letter;
	}

	@Override
	public String toString() {
		return "Node [_x=" + _x + ", _y=" + _y + ", _f=" + _f + ", _letter=" + _letter + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _x;
		result = prime * result + _y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (_x != other._x)
			return false;
		if (_y != other._y)
			return false;
		return true;
	}

	@Override
	public int compareTo(Node o) {
		return Integer.compare(this.getF(), o.getF());
	}

}