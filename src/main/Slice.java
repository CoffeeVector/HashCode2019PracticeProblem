package main;

public class Slice {
	private int x1, y1, x2, y2;

	public Slice(int... coordinates) {
		x1 = coordinates[0];
		y1 = coordinates[1];
		x2 = coordinates[2];
		y2 = coordinates[3];
	}

	public int x1() {
		return x1;
	}

	public int y1() {
		return y1;
	}

	public int x2() {
		return x2;
	}

	public int y2() {
		return y2;
	}

	public int area() {
		return (x2 - x1 + 1) * (y2 - y1 + 1);
	}

	public boolean contains(int x, int y) {
		return (x >= x1() && x <= x2() && y >= y1() && y <= y2());
	}

	public String toString() {
		return "( " + x1 + ", " + y1 + " ) " + "( " + x2 + ", " + y2 + " )";
	}
}
