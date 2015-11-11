import java.util.ArrayList;
import java.util.Scanner;

public class Solver {
	Matrix coefficients;
	Matrix constants;

	/**
	 * coefficient matrix * variable matrix = constants matrix
	 */
	public Solver(ArrayList<ArrayList<Fraction>> equation) {
		Fraction[][] coeffs = new Fraction[equation.size()][equation.size()];
		Fraction[][] cnst = new Fraction[equation.size()][1];
		for (int i = 0; i < coeffs.length; i++) {
			for (int j = 0; j < coeffs[i].length; j++) {
				coeffs[i][j] = equation.get(i).get(j);
			}
			cnst[i][0] = equation.get(i).get(equation.get(i).size() - 1);
		}
		coefficients = new Matrix(coeffs);
		constants = new Matrix(cnst);

	}

	public Solver(Fraction[][] equation) {
		/**
		 * Equation is a rectangular matrix: rows = num of Constants, columns =
		 * num of constants + 1. The extra column with constants
		 */

		Fraction[][] coeffs = new Fraction[equation.length][equation.length];
		Fraction[][] cnst = new Fraction[equation.length][1];
		for (int i = 0; i < coeffs.length; i++) {
			for (int j = 0; j < coeffs[i].length; j++) {
				coeffs[i][j] = equation[i][j];
			}
			cnst[i][0] = equation[i][equation[i].length - 1];
		}
		coefficients = new Matrix(coeffs);
		constants = new Matrix(cnst);
	}

	public Matrix solve() {
		Matrix solution = coefficients.inverse().multiply(constants);
		System.out.println(toString(solution));
		return solution;
	}

	public String solve2() {
		return toString(solve());
	}

	private String toString(Matrix x) {
		String s = "(";
		for (int i = 0; i < x.numberRows(); i++) {
			for (int j = 0; j < x.numberColumns(); j++) {
				s += x.get(i, j);
				if (i == x.numberRows() - 1 && j == x.numberColumns() - 1) {
					s += ")";
				} else {
					s += ", ";
				}
			}

		}
		return s;
	}

	public Matrix getCoefficients() {
		return coefficients;
	}

	public Matrix getConstants() {
		return constants;
	}

	public static void main(String[] args) {
//		Scanner kb = new Scanner(System.in);
//		boolean go = true;
//		while (go) {
//			run(kb);
//			System.out.println("Continue? (Enter NO or no to quit)");
//			if (kb.nextLine().toLowerCase().equals("no")) {
//				go = false;
//			}
//		}
		System.out.println((char)('a' + 2));
	}

	private static void run(Scanner kb) {
		System.out.print("Number of Variables: ");
		int numVars = Integer.parseInt(kb.nextLine());
		System.out.println();
		ArrayList<ArrayList<Fraction>> eq = new ArrayList<>();
		for (int i = 0; i < numVars; i++) {
			eq.add(new ArrayList<Fraction>());
			System.out.print("Next line: ");
			String[] line = kb.nextLine().split(", ");
			for (int j = 0; j < numVars + 1; j++) {
				eq.get(i).add(new Fraction(line[j]));
			}
		}
		Solver s = new Solver(eq);
		s.solve();
	}
}
