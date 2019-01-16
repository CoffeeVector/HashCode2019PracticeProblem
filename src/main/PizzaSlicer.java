package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class PizzaSlicer {
	private int row, column, minIngredient, maxCells;
	private Scanner stdin;
	private boolean[][] isT;

	public PizzaSlicer(String filename) throws FileNotFoundException {
		stdin = new Scanner(new File(filename));
		Scanner dimensionScanner = new Scanner(stdin.nextLine());
		row = Integer.parseInt(dimensionScanner.next());
		column = Integer.parseInt(dimensionScanner.next());
		minIngredient = Integer.parseInt(dimensionScanner.next());
		maxCells = Integer.parseInt(dimensionScanner.next());
		dimensionScanner.close();
		isT = new boolean[row][column];
		for (int j = 0; j < row; j++) {
			String toppings = stdin.next();
			for (int i = 0; i < column; i++) {
				if (toppings.charAt(i) == 'T') {
					isT[j][i] = true;
				}
			}
		}
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
}
