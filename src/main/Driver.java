package main;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Driver {
	public static void main(String[] args) throws FileNotFoundException {
		PizzaSlicer ps = new PizzaSlicer("a_example.in");
		System.out.println("Rows: " + ps.getRow());
		System.out.println("Columns: " + ps.getColumn());
		System.out.println("MI: " + ps.getMinIngredient());
		System.out.println("MC: " + ps.getMaxCells());
		System.out.println(ps.printPizza());
	}
}
