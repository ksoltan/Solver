interface MatrixI {
	int numberRows(); // number of rows

	int numberColumns(); // number of columns

	boolean isSquare(); // true if matrix is square

	int getDimension(); // dimension of square matrix

	Fraction get(int row, int col); // get element at [row][col]

	Matrix add(Matrix that); // matrix sum of this with that

	Matrix multiply(Matrix that); // matrix product of this with that

	Matrix getMinor(int row, int col); // minor matrix removing row and col

	Fraction cofactor(int row, int col); // cofactor for row and col

	Fraction determinant(); // matrix determinant

	Matrix transpose(); // matrix transpose

	Matrix adjugate(); // matrix adjugate

	Matrix scalarMultiply(Fraction scalar); // matrix multiplied by scalar

	Matrix inverse(); // matrix inverse
	
	Matrix ref(); // row echelon form
	
	Matrix rref(); // reduced row echelon form
	
}

public class Matrix implements MatrixI {
	// Matrix is immutable, rectangular array.
	private Fraction[][] matrix;

	public Matrix(Fraction[][] matrix) {
		this.matrix = matrix;
	}

	public Matrix(Matrix other) {
		this(other.matrix);
	}

	@Override
	public int numberRows() {
		return matrix.length;
	}

	@Override
	public int numberColumns() {
		return matrix[0].length;
	}

	@Override
	public boolean isSquare() {
		return numberColumns() == numberRows();
	}

	@Override
	public int getDimension() {
		if (isSquare()) {
			return numberRows();
		}
		throw new MatrixException("Not square matrix");
	}

	@Override
	public Fraction get(int row, int col) {
		return matrix[row][col];
	}

	@Override
	public Matrix add(Matrix that) {
		if (numberColumns() != that.numberColumns()
				|| numberRows() != that.numberRows()) {
			throw new MatrixException("Not the same dimensions");
		}
		Fraction[][] sum = new Fraction[numberRows()][numberColumns()];
		for (int i = 0; i < numberRows(); i++) {
			for (int j = 0; j < numberColumns(); j++) {
				sum[i][j] = this.get(i, j).add(that.get(i, j));
			}
		}
		return new Matrix(sum);
	}

	@Override
	public Matrix multiply(Matrix that) {
		if (numberColumns() != that.numberRows()) {
			throw new MatrixException("Incompatible dimensions");
		}
		Fraction[][] product = new Fraction[numberRows()][that.numberColumns()];
		for (int i = 0; i < product.length; i++) {
			for (int j = 0; j < product[i].length; j++) {
				for (int k = 0; k < numberColumns(); k++) {
					if (product[i][j] == null) {
						product[i][j] = this.get(i, k).multiply(that.get(k, j));
					} else {
						product[i][j] = product[i][j].add(this.get(i, k)
								.multiply(that.get(k, j)));
					}
				}
			}
		}
		return new Matrix(product);
	}

	public void set(int row, int col, Fraction value) {
		matrix[row][col] = value;
	}

	@Override
	// Return (new) minor matrix eliminating row row and column col.
	// Throw MatrixException if matrix not square, dimension < 2, or
	// row or col not on [0, dimension).
	public Matrix getMinor(int row, int col) {
		int dim = getDimension();
		if (dim < 1)
			throw new MatrixException("minor undefined for < 2x2 matrix: "
					+ dim);
		if (row < 0 || row >= dim)
			throw new MatrixException("row index error: " + row);
		if (col < 0 || col >= dim)
			throw new MatrixException("column index error: " + col);

		int[] newRows = new int[numberRows() - 1];
		int[] newCols = new int[numberColumns() - 1];
		for (int oldRow = 0, newRow = 0; oldRow < numberRows(); oldRow++) {
			if (oldRow != row) {
				newRows[newRow] = oldRow;
				newRow++;
			}
		}

		for (int oldCol = 0, newCol = 0; oldCol < numberColumns(); oldCol++) {
			if (oldCol != col) {
				newCols[newCol] = oldCol;
				newCol++;
			}
		}

		Fraction[][] m = new Fraction[newRows.length][newCols.length];
		for (int i = 0; i < newRows.length; i++) {
			for (int j = 0; j < newCols.length; j++) {
				m[i][j] = get(newRows[i], newCols[j]);
			}
		}

		return new Matrix(m);
	}

	@Override
	public Fraction cofactor(int row, int col) {
		Fraction cofactor = getMinor(row, col).determinant();
		if ((row + col) % 2 != 0)
			cofactor = cofactor.negate();
		return cofactor;
	}

	@Override
	public Fraction determinant() {
		if (!isSquare()) {
			throw new MatrixException("Not square");
		}
		return determinantRecursive(new Matrix(matrix));
	}

	private Fraction determinantRecursive(Matrix m) {
		if (m.numberColumns() == 2 && m.numberRows() == 2) {
			Fraction determinant = (m.get(0, 0).multiply(m.get(1, 1)))
					.subtract((m.get(0, 1).multiply(m.get(1, 0))));
			return determinant;
		}
		Fraction determinant = new Fraction(0);
		for (int i = 0; i < m.numberColumns(); i++) {
			if (i % 2 != 0) {
				determinant = determinant.subtract(m.get(0, i).multiply(
						determinantRecursive(m.getMinor(0, i))));
			} else {
				determinant = determinant.add(m.get(0, i).multiply(
						determinantRecursive(m.getMinor(0, i))));
			}
		}
		return determinant;
	}

	@Override
	// turn all columns to rows, rows to columns
	public Matrix transpose() {
		Fraction[][] m = new Fraction[numberColumns()][numberRows()];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				m[i][j] = get(j, i);
			}
		}
		return new Matrix(m);
	}

	@Override
	public Matrix adjugate() {
		if (!isSquare()) {
			throw new MatrixException("Not square");
		}
		Fraction[][] adjugate = new Fraction[numberRows()][numberColumns()];
		for (int i = 0; i < numberRows(); i++) {
			for (int j = 0; j < numberColumns(); j++) {
				adjugate[i][j] = cofactor(i, j);
			}
		}
		return new Matrix(adjugate);
	}

	@Override
	public Matrix scalarMultiply(Fraction scalar) {
		Fraction[][] product = new Fraction[numberRows()][numberColumns()];
		for (int i = 0; i < numberRows(); i++) {
			for (int j = 0; j < numberColumns(); j++) {
				product[i][j] = scalar.multiply(get(i, j));
			}
		}
		return new Matrix(product);
	}

	@Override
	public Matrix inverse() {
		if (determinant().equals(new Fraction(0))) {
			throw new MatrixException("Dividing by 0");
		}
		if (getDimension() == 2) {
			return inverse2X2();
		}
		return adjugate().transpose()
				.scalarMultiply(determinant().reciprocal());
	}

	public Matrix inverse2X2() {
		Fraction[][] mTemp = new Fraction[matrix.length][matrix.length];
		for (int i = 0; i < mTemp.length; i++) {
			for (int j = 0; j < mTemp[i].length; j++) {
				if (i != j) {
					mTemp[i][j] = matrix[i][j].negate();
				} else if (i == 0) {
					mTemp[i][j] = matrix[1][1];
				} else {
					mTemp[i][j] = matrix[0][0];
				}
			}
		}
		return (new Matrix(mTemp)).scalarMultiply(determinant().reciprocal());
	}

	@Override
	public String toString() {
		String s = "[";
		for (int i = 0; i < numberRows(); i++) {
			s += "\n[";
			for (int j = 0; j < numberColumns(); j++) {
				s += " " + get(i, j) + ",";
			}
			s += "]";
		}
		s += "]";
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Matrix) {
			Matrix other = (Matrix) obj;
			if (other.numberColumns() != numberColumns()
					|| other.numberRows() != numberRows()) {
				return false;
			}
			for (int i = 0; i < numberRows(); i++) {
				for (int j = 0; j < numberColumns(); j++) {
					if (!get(i, j).equals(other.get(i, j))) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	static class MatrixException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MatrixException(String msg) {
			super(msg);
		}
	}

	public static void main(String[] args) {
		System.out.println(new Fraction("0.75"));
		System.out.println(new Fraction("3/4"));
		System.out.println(new Fraction("1.00265"));
		System.out.println(new Fraction("123/768"));
	}

	@Override
	public Matrix ref() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix rref() {
		// TODO Auto-generated method stub
		return null;
	}
}

class Fraction {

	// ****************** Instance variables ******************

	private int num;
	private int denom;

	// private final static int MAX_DENOM = 10000;

	// ********************* Constructors *********************

	public Fraction() // no-args constructor
	{
		num = 0;
		denom = 1;
	}

	public Fraction(String s) {
		String[] nums = new String[2];
		if (s.indexOf('/') != -1) {
			nums[0] = s.substring(0, s.indexOf('/'));
			nums[1] = s.substring(s.indexOf('/') + 1);
			num = Integer.parseInt(nums[0]);
			denom = Integer.parseInt(nums[1]);
		} else if (s.indexOf('.') != -1) {
			parseDecimal(s);
		} else {
			try {
				num = Integer.parseInt(s);
				denom = 1;
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		}

	}

	private void parseDecimal(String s) {
		boolean negative = false;
		String[] nums = new String[2];
		nums[0] = s.substring(0, s.indexOf('.'));
		if (nums[0].indexOf('-') != -1) {
			nums[0] = nums[0].substring(1);
			negative = true;
		}
		nums[1] = s.substring(s.indexOf('.') + 1);
		num = Integer.parseInt(nums[0]) * (int) Math.pow(10, nums[1].length())
				+ Integer.parseInt(nums[1]);
		num = (negative == true) ? -num : num;
		denom = (int) Math.pow(10, nums[1].length());
		reduce();
	}

	public Fraction(int n) {
		num = n;
		denom = 1;
	}

	public Fraction(int n, int d) {
		if (d != 0) {
			num = n;
			denom = d;
			reduce();
		} else {
			throw new IllegalArgumentException(
					"Fraction construction error: denominator is 0");
		}
	}

	public Fraction(Fraction other) // copy constructor
	{
		num = other.num;
		denom = other.denom;
	}

	// ******************** Public methods ********************

	// Returns the sum of this fraction and other
	public Fraction add(Fraction other) {
		int newNum = num * other.denom + denom * other.num;
		int newDenom = denom * other.denom;
		return new Fraction(newNum, newDenom);
	}

	// Returns the sum of this fraction and m
	public Fraction add(int m) {
		return new Fraction(num + m * denom, denom);
	}

	// Returns the product of this fraction and other
	public Fraction multiply(Fraction other) {
		int newNum = num * other.num;
		int newDenom = denom * other.denom;
		return new Fraction(newNum, newDenom);
	}

	// Returns the product of this fraction and m
	public Fraction multiply(int m) {
		return new Fraction(num * m, denom);
	}

	// Returns the value of this fraction as a double
	public double getValue() {
		return (double) num / (double) denom;
	}

	// Returns a string representation of this fraction
	public String toString() {
		if (denom == 1) {
			return num / denom + "";
		}
		if (Math.abs(num) > Math.abs(denom)) {
			return num / denom + " "
					+ new Fraction(Math.abs(num % denom), Math.abs(denom));
		}
		return num + "/" + denom;
	}

	public Fraction reciprocal() {
		return new Fraction(denom, num);
	}

	public int getNumerator() {
		return num;
	}

	public int getDenominator() {
		return denom;
	}

	public Fraction negate() {
		return new Fraction(num * -1, denom);
	}

	public Fraction subtract(Fraction other) {
		return add(other.negate());
	}

	public Fraction divide(Fraction other) {
		if (other.num == 0) {
			throw new IllegalArgumentException(
					"Fraction division error: denominator is 0");
		}
		return multiply(new Fraction(other.denom, other.num));
	}

	// ******************* Private methods ********************

	// Reduces this fraction by the gcf and makes denom > 0
	private void reduce() {
		if (num == 0) {
			denom = 1;
			return;
		}

		if (denom < 0) {
			num = -num;
			denom = -denom;
		}

		int q = gcf(Math.abs(num), denom);
		num /= q;
		denom /= q;
	}

	// Returns the greatest common factor of two positive integers
	private int gcf(int n, int d) {
		if (n <= 0 || d <= 0) {
			throw new IllegalArgumentException("gcf precondition failed: " + n
					+ ", " + d);
		}

		if (n % d == 0)
			return d;
		else if (d % n == 0)
			return n;
		else
			return gcf(n % d, d % n);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Fraction) {
			Fraction other = (Fraction) obj;
			return this.denom == other.denom && this.num == other.num;
		}
		return false;
	}
}
