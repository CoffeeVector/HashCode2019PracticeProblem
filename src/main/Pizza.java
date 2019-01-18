package main;

import java.util.ArrayList;

public class Pizza {
	private Boolean[][] isT;
	private ArrayList<Slice> slices;

	public Pizza(Boolean[][] isT) {
		this.isT = new Boolean[isT.length][isT[0].length];
		for (int i = 0; i < isT.length; i++) {
			for (int j = 0; j < isT[i].length; j++) {
				this.isT[i][j] = isT[i][j];
			}
		}
		slices = new ArrayList<Slice>();
	}

	public Pizza(Boolean[][] isT, ArrayList<Slice> slices) {
		this.isT = new Boolean[isT.length][isT[0].length];
		for (int i = 0; i < isT.length; i++) {
			for (int j = 0; j < isT[i].length; j++) {
				this.isT[i][j] = isT[i][j];
			}
		}
		this.slices = slices;
	}

	public Boolean[][] getIsT() {
		Boolean[][] output = new Boolean[isT.length][isT[0].length];
		for (int i = 0; i < isT.length; i++) {
			for (int j = 0; j < isT[0].length; j++) {
				output[i][j] = isT[i][j];
			}
		}
		return output;
	}

	public ArrayList<Slice> getSlices() {
		return (ArrayList<Slice>) slices.clone();
	}

	public int getLeftOver() {
		int out = 0;
		for (Boolean[] bs : isT) {
			for (Boolean b : bs) {
				if (b != null) {
					out++;
				}
			}
		}
		return out;
	}

	public String toString() {
		String out = "";
		for (int i = 0; i < isT.length; i++) {
			for (int j = 0; j < isT[i].length; j++) {
				if (isT[i][j] == null) {
					out += "N";
				} else {
					out += (isT[i][j]) ? "T" : "M";
				}
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
		Boolean[][] isTOutput = new Boolean[a.getIsT().length][a.getIsT()[0].length];
		for (int i = 0; i < isTOutput.length; i++) {
			for (int j = 0; j < isTOutput[0].length; j++) {
				if (a.getIsT()[i][j] == null && b.getIsT()[i][j] == null) {
					throw new PizzaMismatchException();
				}
				if (a.getIsT()[i][j] == null || b.getIsT()[i][j] == null) {
					isTOutput[i][j] = null;
				} else {
					isTOutput[i][j] = a.getIsT()[i][j];// assume that a[i][j] and b[i][j] are the same.
				}
			}
		}
		return new Pizza(isTOutput, slicesOutput);
	}

	public static Pizza slice(Pizza a, Slice s) throws PizzaMismatchException {
		ArrayList<Slice> slicesOutput = new ArrayList<Slice>();
		slicesOutput.addAll(a.getSlices());
		slicesOutput.add(s);
		Boolean[][] isTOutput = new Boolean[a.getIsT().length][a.getIsT()[0].length];
		for (int i = 0; i < isTOutput.length; i++) {
			for (int j = 0; j < isTOutput[i].length; j++) {
				if (a.getIsT()[i][j] == null && i >= s.y1() && i <= s.y2() && j >= s.x1() && j <= s.x2()) {
					throw new PizzaMismatchException();
				} else if (a.getIsT()[i][j] == null || (i >= s.y1() && i <= s.y2() && j >= s.x1() && j <= s.x2())) {
					isTOutput[i][j] = null;
				} else {
					isTOutput[i][j] = a.getIsT()[i][j];
				}
			}
		}
		return new Pizza(isTOutput, slicesOutput);
	}
}
