package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Driver {
	public static void main(String[] args) throws FileNotFoundException {
		PizzaSlicer ps = new PizzaSlicer("a_example.in");
		System.out.println("Rows: " + ps.getRow());
		System.out.println("Columns: " + ps.getColumn());
		System.out.println("MI: " + ps.getMinIngredient());
		System.out.println("MC: " + ps.getMaxCells());
		System.out.println(ps.printPizza());
		ArrayList<Pizza> allFullyCutPizzas = ps.cutPizza();
		Pizza optimized = allFullyCutPizzas.get(0);
		for (Pizza p : allFullyCutPizzas) {
			System.out.println(p);
			System.out.println("Left Over: " + p.getLeftOver());
			if (p.getLeftOver() < optimized.getLeftOver()) {
				optimized = p;
			}
		}
		System.out.println("Optimized: \n" + optimized);
	}
}
