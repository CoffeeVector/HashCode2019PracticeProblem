package main;

import java.util.ArrayList;

public class Pizza {
	private int rows;
	private int columns;
	private ArrayList<Slice> slices;

	public Pizza(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		slices = new ArrayList<Slice>();
	}

	public Pizza(int rows, int columns, ArrayList<Slice> slices) {
		this.rows = rows;
		this.columns = columns;
		this.slices = slices;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public ArrayList<Slice> getSlices() {
		return (ArrayList<Slice>) slices.clone();
	}

	public int getLeftOver() {
		int out = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				out += (contains(j, i)) ? 0 : 1;
			}
		}
		return out;
	}

	public boolean contains(int x, int y) {
		for (Slice s : slices) {
			if (s.contains(x, y)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		String out = "";
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				out += (contains(j, i)) ? "I":"N";
			}
			out += "\n";
		}
		for (Slice s : slices) {
			out += s + "\n";
		}
		out += "\n";
		return out;
	}

	public static Pizza combine(Pizza a, Pizza b) throws PizzaMismatchException {
		ArrayList<Slice> slicesOutput = new ArrayList<Slice>();
		slicesOutput.addAll(a.getSlices());
		slicesOutput.addAll(b.getSlices());
		for (int i = 0; i < a.getRows(); i++) {
			for (int j = 0; j < a.getColumns(); j++) {
				if (a.contains(j, i) && b.contains(j, i)) {
					throw new PizzaMismatchException();
				}
			}
		}
		return new Pizza(a.getRows(), a.getColumns(), slicesOutput);
	}

	public static Pizza slice(Pizza a, Slice s) throws PizzaMismatchException {
		for (int i = 0; i < a.getRows(); i++) {
			for (int j = 0; j < a.getColumns(); j++) {
				if (a.contains(j, i) && s.contains(j, i)) {
					throw new PizzaMismatchException();
				}
			}
		}
		ArrayList<Slice> slicesOutput = new ArrayList<Slice>();
		slicesOutput.addAll(a.getSlices());
		slicesOutput.add(s);
		return new Pizza(a.getRows(), a.getColumns(), slicesOutput);
	}
}
