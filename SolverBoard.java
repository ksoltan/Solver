import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SolverBoard extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_DIMENSION = 2;
	private static final String DEFAULT_SOLUTION_TEXT = "Please fill in coefficients.";

	/**
	 * Defines dimension of the matrix.
	 */
	private final JPanel controlPane = new JPanel();
	private int dimension = DEFAULT_DIMENSION;
	private final JTextField dimensionField = new JTextField(4);
	/**
	 * Defines matrix coefficients.
	 */
	private final JPanel inputPane = new JPanel();
	private final ArrayList<ArrayList<JTextField>> matrix = new ArrayList<>();
	private final ArrayList<ArrayList<Fraction>> equations = new ArrayList<>();
	/**
	 * Solution.
	 */
	private final JPanel solutionPane = new JPanel();
	private final JTextArea solutionArea = new JTextArea();

	public SolverBoard() {
	}

	private void updateMatrix() {
		matrix.clear();
		for (int i = 0; i < dimension; i++) {
			matrix.add(new ArrayList<JTextField>());
			for (int j = 0; j < dimension + 1; j++) {
				JTextField coefficientField = new JTextField();
				matrix.get(i).add(coefficientField);
				coefficientField.setText(Integer.toString(0));
				coefficientField.selectAll();
			}
		}
	}

	private void updateEquations() {
		equations.clear();
		for (int i = 0; i < dimension; i++) {
			equations.add(new ArrayList<Fraction>());
			for (int j = 0; j < dimension + 1; j++) {
				JTextField coefficientField = matrix.get(i).get(j);
				equations.get(i).add(new Fraction(coefficientField.getText()));
			}
		}
	}

	public void initSolverBoard(JFrame frame) {
		Container cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(controlPane, BorderLayout.NORTH);
		cp.add(inputPane, BorderLayout.CENTER);
		cp.add(solutionPane, BorderLayout.SOUTH);

		initControlPane(frame);
		initMatrixPane();
		initSolutionPane();
	}

	private void initMatrixPane() {
		inputPane.removeAll();
		inputPane.setLayout(new GridLayout(dimension, 2 * dimension + 1));
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension + 1; j++) {
				JTextField coefficientField = matrix.get(i).get(j);
				inputPane.add(coefficientField);
				char var = (char)(j + 97);
				if (j < dimension - 1) {
					inputPane.add(new JLabel(var + "   + "));
				}else if (j == dimension - 1) {
					inputPane.add(new JLabel(var + "   = "));
				}
				
			}
		}
	}

	private void initControlPane(final JFrame frame) {
		controlPane.add(new JLabel("Number of variables:"));
		controlPane.add(dimensionField);

		dimensionField.setText(Integer.toString(dimension));
		dimensionField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateDimension();
				frame.pack();
			}
		});
		updateDimension();
	}

	private void initSolutionPane() {
		JButton solve = new JButton("Solve");
		solve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateEquations();
				try {
					String solutionText = new Solver(equations).solve2();
					solutionArea.setText(solutionText);
				} catch (Matrix.MatrixException matrixException) {
					solutionArea.setText("Exception: "
							+ matrixException.getMessage());
				}
			}
		});
		solutionPane.add(solve);
		solutionPane.add(solutionArea);
		solutionArea.setPreferredSize(new Dimension(600, 200));
	}

	private void updateDimension() {
		dimension = Integer.parseInt(dimensionField.getText());
		updateMatrix();
		initMatrixPane();
		solutionArea.setText(DEFAULT_SOLUTION_TEXT);
	}

	public static void main(String[] args) {
		JFrame w = new JFrame("Solver");
		// w.setBounds(200, 200, 1000, 500);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SolverBoard sb = new SolverBoard();
		sb.initSolverBoard(w);
		w.pack();
		w.setVisible(true);
	}
}