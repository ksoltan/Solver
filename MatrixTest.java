import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MatrixTest {
	Fraction[][] twoByThree;
	Fraction[][] threeByThree;

	@Before
	public void setUp() throws Exception {
		twoByThree = new Fraction[][] {
				{ new Fraction(1), new Fraction(4), new Fraction(-3) },
				{ new Fraction(3), new Fraction(-1), new Fraction(3) } };
		threeByThree = new Fraction[][] {
				{ new Fraction(1), new Fraction(2), new Fraction(1) },
				{ new Fraction(3), new Fraction(5), new Fraction(7) },
				{ new Fraction(0), new Fraction(8), new Fraction(16) } };
	}

	@Test
	public void testIsSquare() {
		assertEquals(true, new Matrix(threeByThree).isSquare());

	}

	@Test
	public void testIsNotSquare() {
		assertEquals(false, new Matrix(twoByThree).isSquare());
	}

	@Test
	public void testAdd() {
		Fraction[][] sum = new Fraction[][] {
				{ new Fraction(2), new Fraction(8), new Fraction(-6) },
				{ new Fraction(6), new Fraction(-2), new Fraction(6) } };
		assertEquals(new Matrix(sum),
				new Matrix(twoByThree).add(new Matrix(twoByThree)));

	}

	@Test
	public void testMultiply() {
		Fraction[][] sum = new Fraction[][] {
				{ new Fraction(13), new Fraction(-2), new Fraction(-19) },
				{ new Fraction(0), new Fraction(25), new Fraction(44) } };

		assertEquals(new Matrix(sum),
				new Matrix(twoByThree).multiply(new Matrix(threeByThree)));

	}

	@Test
	public void testGetMinor() {
		Fraction[][] matrix = {
				{ new Fraction(3), new Fraction(0), new Fraction(2) },
				{ new Fraction(2), new Fraction(0), new Fraction(-2) },
				{ new Fraction(0), new Fraction(1), new Fraction(1) } };
		Fraction[][] minor = { { new Fraction(0), new Fraction(-2) },
				{ new Fraction(1), new Fraction(1) } };
		assertEquals(new Matrix(minor), new Matrix(matrix).getMinor(0, 0));
	}

	@Test
	public void testGetMinorSecondColumn() {
		Fraction[][] matrix = {
				{ new Fraction(3), new Fraction(0), new Fraction(2) },
				{ new Fraction(2), new Fraction(0), new Fraction(-2) },
				{ new Fraction(0), new Fraction(1), new Fraction(1) } };
		Fraction[][] minor = { { new Fraction(2), new Fraction(-2) },
				{ new Fraction(0), new Fraction(1) } };
		assertEquals(new Matrix(minor), new Matrix(matrix).getMinor(0, 1));

	}

	@Test
	public void testGetMinorThirdColumn() {
		Fraction[][] matrix = {
				{ new Fraction(3), new Fraction(0), new Fraction(2) },
				{ new Fraction(2), new Fraction(0), new Fraction(-2) },
				{ new Fraction(0), new Fraction(1), new Fraction(1) } };
		Fraction[][] minor = { { new Fraction(2), new Fraction(0) },
				{ new Fraction(0), new Fraction(1) } };
		assertEquals(new Matrix(minor), new Matrix(matrix).getMinor(0, 2));

	}

	@Test
	public void testGetDeterminant2X2() {
		Fraction[][] matrix = { { new Fraction(3), new Fraction(1) },
				{ new Fraction(5), new Fraction(2) } };
		assertEquals(new Fraction(1), new Matrix(matrix).determinant());
	}

	@Test
	public void testGetDeterminant3X3() {
		Fraction[][] matrix = {
				{ new Fraction(6), new Fraction(1), new Fraction(1) },
				{ new Fraction(4), new Fraction(-2), new Fraction(5) },
				{ new Fraction(2), new Fraction(8), new Fraction(7) } };
		assertEquals(new Fraction(-306), new Matrix(matrix).determinant());
	}

	@Test
	public void testGetDeterminant4X4() {
		Fraction[][] matrix = {
				{ new Fraction(3), new Fraction(2), new Fraction(0),
						new Fraction(1) },
				{ new Fraction(4), new Fraction(0), new Fraction(1),
						new Fraction(2) },
				{ new Fraction(3), new Fraction(0), new Fraction(2),
						new Fraction(1) },
				{ new Fraction(9), new Fraction(2), new Fraction(3),
						new Fraction(1) } };
		assertEquals(new Fraction(24), new Matrix(matrix).determinant());
	}

	@Test
	public void testGetInverse3X3() {
		Fraction[][] matrix = {
				{ new Fraction(3), new Fraction(0), new Fraction(2) },
				{ new Fraction(2), new Fraction(0), new Fraction(-2) },
				{ new Fraction(0), new Fraction(1), new Fraction(1) } };
		Fraction[][] inverse = {
				{ new Fraction(2, 10), new Fraction(2, 10), new Fraction(0) },
				{ new Fraction(-2, 10), new Fraction(3, 10), new Fraction(1) },
				{ new Fraction(2, 10), new Fraction(-3, 10), new Fraction(0) } };
		assertEquals(new Matrix(inverse), new Matrix(matrix).inverse());
	}

	@Test
	public void testScalarMultiply() {
		Fraction[][] matrix = {
				{ new Fraction(3), new Fraction(0), new Fraction(2) },
				{ new Fraction(2), new Fraction(0), new Fraction(-2) },
				{ new Fraction(0), new Fraction(1), new Fraction(1) } };
		Fraction[][] newMatrix = {
				{ new Fraction(30), new Fraction(0), new Fraction(20) },
				{ new Fraction(20), new Fraction(0), new Fraction(-20) },
				{ new Fraction(0), new Fraction(10), new Fraction(10) } };
		assertEquals(new Matrix(newMatrix),
				new Matrix(matrix).scalarMultiply(new Fraction(10)));
	}
	
	@Test
	public void testDecimalInit(){
		Fraction m = new Fraction(3, 5);
		Fraction n = new Fraction("0.6");
		assertEquals(m, n);
	}
	
	@Test
	public void testDecimalInitNeg(){
		Fraction m = new Fraction(-3, 5);
		Fraction n = new Fraction("-0.6");
		assertEquals(m, n);
	}
	@Test
	public void testDecimalInitNegBig(){
		Fraction m = new Fraction(-7, 5);
		Fraction n = new Fraction("-1.4");
		assertEquals(m, n);
	}

}
