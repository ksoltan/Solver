import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SolverTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCtor2X2() {
		/**
		 * x + y = 6
		 * 
		 * -3x + y = 2
		 */
		Fraction[][] m = {
				{ new Fraction(1), new Fraction(1), new Fraction(6) },
				{ new Fraction(-3), new Fraction(1), new Fraction(2) } };

		Fraction[][] coeffs = { { new Fraction(1), new Fraction(1) },
				{ new Fraction(-3), new Fraction(1) } };

		Fraction[][] cnst = { { new Fraction(6) }, { new Fraction(2) } };
		Solver s = new Solver(m);
		assertEquals(new Matrix(coeffs), s.getCoefficients());
		assertEquals(new Matrix(cnst), s.getConstants());
	}

	@Test
	public void testCtor3X3() {
		/**
		 * 2x + y - 2z = 3
		 * 
		 * x - y - z = 0
		 * 
		 * x + y + 3z = 12
		 */
		Fraction[][] m = {
				{ new Fraction(2), new Fraction(1), new Fraction(-2),
						new Fraction(3) },
				{ new Fraction(1), new Fraction(-1), new Fraction(-1),
						new Fraction(0) },
				{ new Fraction(1), new Fraction(1), new Fraction(3),
						new Fraction(12) } };

		Fraction[][] coeffs = {
				{ new Fraction(2), new Fraction(1), new Fraction(-2) },
				{ new Fraction(1), new Fraction(-1), new Fraction(-1) },
				{ new Fraction(1), new Fraction(1), new Fraction(3) }, };

		Fraction[][] cnst = { { new Fraction(3) }, { new Fraction(0) },
				{ new Fraction(12) } };
		Solver s = new Solver(m);
		assertEquals(new Matrix(coeffs), s.getCoefficients());
		assertEquals(new Matrix(cnst), s.getConstants());
	}

	@Test
	public void testSolve2X2() {
		/**
		 * x + y = 6
		 * 
		 * -3x + y = 2
		 */
		Fraction[][] m = {
				{ new Fraction(1), new Fraction(1), new Fraction(6) },
				{ new Fraction(-3), new Fraction(1), new Fraction(2) } };

		Fraction[][] solution = { { new Fraction(1) }, { new Fraction(5) } };
		Solver s = new Solver(m);
		assertEquals(new Matrix(solution), s.solve());
	}

	@Test
	public void testSolve3X3() {
		/**
		 * 2x + y - 2z = 3
		 * 
		 * x - y - z = 0
		 * 
		 * x + y + 3z = 12
		 */
		Fraction[][] m = {
				{ new Fraction(2), new Fraction(1), new Fraction(-2),
						new Fraction(3) },
				{ new Fraction(1), new Fraction(-1), new Fraction(-1),
						new Fraction(0) },
				{ new Fraction(1), new Fraction(1), new Fraction(3),
						new Fraction(12) } };

		Fraction[][] solution = { { new Fraction(7, 2) }, { new Fraction(1) },
				{ new Fraction(5, 2) } };
		Solver s = new Solver(m);
		assertEquals(new Matrix(solution), s.solve());
	}

	@Test
	public void testSolve4X4() {
		/**
		 * 4w+ x+ 2y -3z=-16
		 * 
		 * -3w+ 3x -y+ 4z= 20
		 * 
		 * -w+2x+5y+ z= -4
		 * 
		 * 5w+4x+3y- z=-10
		 */
		Fraction[][] m = {
				{ new Fraction(4), new Fraction(1), new Fraction(2),
						new Fraction(-3), new Fraction(-16) },
				{ new Fraction(-3), new Fraction(3), new Fraction(-1),
						new Fraction(4), new Fraction(20) },
				{ new Fraction(-1), new Fraction(2), new Fraction(5),
						new Fraction(1), new Fraction(-4) },
				{ new Fraction(5), new Fraction(4), new Fraction(3),
						new Fraction(-1), new Fraction(-10) } };

		Fraction[][] solution = { { new Fraction(-1) }, { new Fraction(1) },
				{ new Fraction(-2) }, { new Fraction(3) } };
		Solver s = new Solver(m);
		assertEquals(new Matrix(solution), s.solve());
	}

	@Test
	public void testInitWithArrayList2X2() {
		/**
		 * x + y = 6
		 * 
		 * -3x + y = 2
		 */
		ArrayList<ArrayList<Fraction>> m = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			m.add(new ArrayList<Fraction>());
		}
		m.get(0).add(new Fraction(1));
		m.get(0).add(new Fraction(1));
		m.get(0).add(new Fraction(6));
		m.get(1).add(new Fraction(-3));
		m.get(1).add(new Fraction(1));
		m.get(1).add(new Fraction(2));
		System.out.println(m);
		Fraction[][] coeffs = { { new Fraction(1), new Fraction(1) },
				{ new Fraction(-3), new Fraction(1) } };

		Fraction[][] cnst = { { new Fraction(6) }, { new Fraction(2) } };
		Solver s = new Solver(m);
		assertEquals(new Matrix(coeffs), s.getCoefficients());
		assertEquals(new Matrix(cnst), s.getConstants());
	}

	@Test
	public void testSolve2() {
		/**
		 * x + y = 6
		 * 
		 * -3x + y = 2
		 */
		Fraction[][] m = {
				{ new Fraction(1), new Fraction(1), new Fraction(6) },
				{ new Fraction(-3), new Fraction(1), new Fraction(2) } };
		String solution = "(1, 5)";
		Solver s = new Solver(m);
		assertEquals(solution, s.solve2());

	}

	@Test
	public void testCtor2X2Decimals() {
		/**
		 * x + y = 6
		 * 
		 * -3x + y = 2
		 */
		Fraction[][] m = {
				{ new Fraction(7), new Fraction("-0.6"), new Fraction(-4) },
				{ new Fraction(1), new Fraction("0.6"), new Fraction(-3) } };

		Fraction[][] coeffs = { { new Fraction(7), new Fraction(-3, 5) },
				{ new Fraction(1), new Fraction(3, 5) } };

		Fraction[][] cnst = { { new Fraction(-4) }, { new Fraction(-3) } };
		Solver s = new Solver(m);
		assertEquals(new Matrix(coeffs), s.getCoefficients());
		assertEquals(new Matrix(cnst), s.getConstants());
	}

	@Test
	public void testSolveDecimals() {
		/**
		 * 7x - 0.6y = -4
		 * 
		 * x + 0.6y = -3
		 */
		Fraction[][] m = {
				{ new Fraction(7), new Fraction("-0.6"), new Fraction(-4) },
				{ new Fraction(1), new Fraction("0.6"), new Fraction(-3) } };
		String solution = "(-7/8, -3 13/24)";
		Solver s = new Solver(m);
		assertEquals(solution, s.solve2());

	}
}
