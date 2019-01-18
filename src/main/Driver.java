package main;

import java.io.IOException;

public class Driver {
	public static void main(String[] args) throws IOException {
		String filename = "d_big";
		PizzaSlicer ps = new PizzaSlicer(filename + ".in");
		System.out.println("Rows: " + ps.getRow());
		System.out.println("Columns: " + ps.getColumn());
		System.out.println("MI: " + ps.getMinIngredient());
		System.out.println("MC: " + ps.getMaxCells());
		ps.cutPizza();
		System.out.println("Pizza Cut.");
		ps.writeSlices(filename + ".submission");
	}
}
