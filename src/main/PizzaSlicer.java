package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PizzaSlicer {
	private int row, column, minIngredient, maxCells;
	private Scanner stdin;
	private Boolean[][] isT;
	private ArrayList<Slice> s;

	public PizzaSlicer(String filename) throws FileNotFoundException {
		stdin = new Scanner(new File(filename));
		Scanner dimensionScanner = new Scanner(stdin.nextLine());
		row = Integer.parseInt(dimensionScanner.next());
		column = Integer.parseInt(dimensionScanner.next());
		minIngredient = Integer.parseInt(dimensionScanner.next());
		maxCells = Integer.parseInt(dimensionScanner.next());
		dimensionScanner.close();
		isT = new Boolean[row][column];
		for (int j = 0; j < row; j++) {
			String toppings = stdin.next();
			for (int i = 0; i < column; i++) {
				if (toppings.charAt(i) == 'T') {
					isT[j][i] = true;
				} else {
					isT[j][i] = false;
				}
			}
		}
	}

	/**
	 * 
	 * @param s slice object that describes the area of consideration (inclusively)
	 * @return the amount of tomatoes in that region
	 */
	public int tomatoCount(Slice s) {
		int count = 0;
		for (int i = s.x1(); i <= s.x2(); i++) {
			for (int j = s.y1(); j <= s.y2(); j++) {
				count += (isT[j][i]) ? 1 : 0;
			}
		}
		return count;
	}

	/**
	 * 
	 * @param xInitial x coordinate for upper left corner of slice
	 * @param yInitial y coordinate for upper left corner of slice
	 * @param area     Area of slices
	 * @return
	 */
	public ArrayList<Slice> searchValidSlices(int xInitial, int yInitial, int area) {
		ArrayList<Slice> output = new ArrayList<Slice>();
		for (int i = 1; i < area; i++) {// i is the width.
			if (area % i == 0) {// if area divides width cleanly, otherwise do not consider.
				output.add(new Slice(xInitial, yInitial, xInitial + i - 1, yInitial + area / i - 1));
			}
		}
		return output;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getMinIngredient() {
		return minIngredient;
	}

	public int getMaxCells() {
		return maxCells;
	}

	public boolean[][] getIsT() {
		boolean[][] out = new boolean[isT.length][isT[0].length];
		for (int i = 0; i < isT.length; i++) {
			for (int j = 0; j < isT[i].length; j++) {
				out[i][j] = isT[i][j];
			}
		}
		return out;
	}

	public String printPizza() {
		String out = "";
		for (int i = 0; i < isT.length; i++) {
			for (int j = 0; j < isT[i].length; j++) {
				out += (isT[i][j]) ? "T" : "M";
			}
			out += "\n";
		}
		return out;
	}
}
