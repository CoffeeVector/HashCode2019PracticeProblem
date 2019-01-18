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
		s = new ArrayList<Slice>();
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
				Slice s = new Slice(xInitial, yInitial, xInitial + i - 1, yInitial + area / i - 1);
				if (isSliceValid(s)) {
					output.add(s);
				}
			}
		}
		return output;
	}

	/**
	 * "cutting the slice out of the pizza" refers to saving the Slice information
	 * and also nulling the ingredients that used to be there.
	 * 
	 * @param s Slice under consideration
	 */
	public void cutSlice(Slice s) {
		for (int i = s.x1(); i <= s.x2(); i++) {
			for (int j = s.y1(); j <= s.y2(); j++) {
				isT[j][i] = null;
			}
		}
		this.s.add(s);// add Slice s to ArrayList<Slice> s
	}

	/**
	 * "cutting the slice out of the pizza" refers to saving the Slice information
	 * and also nulling the ingredients that used to be there.
	 * 
	 * @param s   Slice under consideration
	 * @param isT a pizza
	 */
	public Boolean[][] cutSlice(Slice s, Boolean[][] isT) {
		Boolean[][] output = new Boolean[isT.length][isT[0].length];
		for (int i = 0; i < isT.length; i++) {
			for (int j = 0; j < isT[0].length; j++) {
				output[i][j] = isT[i][j];
			}
		}
		for (int i = s.x1(); i <= s.x2(); i++) {
			for (int j = s.y1(); j <= s.y2(); j++) {
				output[j][i] = null;
			}
		}
		return output;
	}

	/**
	 * 
	 * @param s Slice object under consideration
	 * @return if the slice is small enough, contains enough of each ingredients,
	 *         contains no nulls (cut parts), and doesn't go off the pizza, then
	 *         return true. Otherwise return false.
	 */
	public boolean isSliceValid(Slice s) {
		int mushroomCount = 0;
		int tomatoCount = 0;
		for (int i = s.y1(); i <= s.y2(); i++) {
			for (int j = s.x1(); j <= s.x2(); j++) {
				try {
					if (isT[i][j] == null) {
						return false;
					}
					if (isT[i][j]) {
						tomatoCount++;
					} else {
						mushroomCount++;
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					return false;
				}
			}
		}
		return (mushroomCount >= minIngredient && tomatoCount >= minIngredient && s.area() <= maxCells);
	}

	public ArrayList<Pizza> cutPizza() {
		ArrayList<Slice> singleSlices = new ArrayList<Slice>();
		ArrayList<Pizza> pizzas = new ArrayList<Pizza>();// all the current possible pizzas
		ArrayList<Pizza> pizzas1 = new ArrayList<Pizza>();// all the possible pizzas in the next generation
		for (int i = 0; i < isT.length; i++) {
			for (int j = 0; j < isT[0].length; j++) {
				for (int a = maxCells; a >= minIngredient * 2; a--) {// Attempt for maximum area, then go down until
					singleSlices.addAll(searchValidSlices(j, i, a));
				}
			}
		}
		for (Slice s : singleSlices) {
			ArrayList<Slice> sList = new ArrayList<Slice>();
			sList.add(s);
			Pizza p = new Pizza(cutSlice(s, isT), sList);
			pizzas1.add(p);
		}
		while (!pizzas1.isEmpty()) {// if pizzas1 is empty, that means none of the slices can be combined
			pizzas = pizzas1;
			pizzas1 = new ArrayList<Pizza>();
			for (int i = 0; i < pizzas.size(); i++) {// loop through all the current pizzas
				for (int j = 0; j < singleSlices.size(); j++) {
					try {
						pizzas1.add(Pizza.slice(pizzas.get(i), singleSlices.get(j)));
					} catch (PizzaMismatchException e) {
						// do nothing
					}
				}
			}
		}
		return pizzas;
	}

	public Pizza bestCut() {
		ArrayList<Pizza> cutPizzas = cutPizza();
		Pizza best = cutPizzas.get(0);
		for (Pizza p : cutPizzas) {
			if (p.getLeftOver() < best.getLeftOver()) {
				best = p;
			}
		}
		return best;
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

	@SuppressWarnings("unchecked")
	public ArrayList<Slice> getSlices() {
		return (ArrayList<Slice>) s.clone();
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
