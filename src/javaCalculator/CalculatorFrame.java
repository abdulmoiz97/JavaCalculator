package javaCalculator;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

class CalculatorFrame extends JFrame {
	
	private String current = "";
	private Mode M = Mode.INTEGER;
	private Expr expression;

	private static final int NUMBER_PAD_WIDTH = 4;
	private static final int NUMBER_PAD_HEIGHT = 5;
	
	private static final int CALCULATOR_WIDTH = 1;
	private static final int CALCULATOR_HEIGHT = 2;
	
	/* Format Example
	 *    -------------------
	 *    |               0 |
	 *    -------------------
	 *    | ( | ) | B | AC  |
	 *    -------------------
	 *    | 7 | 8 | 9 |  /  |
	 *    -------------------
	 *    | 4 | 5 | 6 |  *  |
	 *    -------------------
	 *    | 1 | 2 | 3 |  -  |
	 *    -------------------
	 *    | 0 | M | = |  +  |
	 *    -------------------
	 */

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel, resultPanel, numberPanel;
	
	private static Set<String> operators = new HashSet<String>();
	private static Set<String> digits = new HashSet<String>();
	
	
	static {
		
		for(String x : new String[]{"(", ")", "+", "-", "*", "/", "B", "=", "M", "AC"})
			operators.add(x);
		
		for(int i = 0; i < 10; i++)
			digits.add(Integer.toString(i));
	}
	
	private boolean clearResultField = true;

	private Integer firstNumber = null;

	private String action = null;

	private JTextField resultField;

	public CalculatorFrame() {

		super("Really Simple Calculator");

		resultPanel = new JPanel();

		resultPanel.setLayout(new FlowLayout());
		
		resultField = new JTextField("0", 20);
		
		resultPanel.add(resultField);
		
		resultField.setHorizontalAlignment(JTextField.RIGHT);
		
		resultField.setEditable(false);

		numberPanel = new JPanel();
		
		numberPanel.setLayout(new GridLayout(NUMBER_PAD_HEIGHT, NUMBER_PAD_WIDTH));
		
		Map<String, JButton> buttons = new HashMap<String, JButton>();

		for(String x : digits)
			buttons.put(x, new JButton(x));
		for(String x : operators)
			buttons.put(x, new JButton(x));

		String[][] buttonOrder = new String[][]{

				{ "(", ")", "B", "AC" },
				{ "7", "8", "9", "/" },
				{ "4", "5", "6", "*" },
				{ "1", "2", "3", "-" },
				{ "0", "M", "=", "+" }
		};

		for(int i = 0; i < NUMBER_PAD_HEIGHT; i++)
			for(int j = 0; j < NUMBER_PAD_WIDTH; j++)
				numberPanel.add(buttons.get(buttonOrder[i][j]));
		
		
		ActionListener digitListener = buildDigitListener();
		
		for(String x : digits)
			buttons.get(x).addActionListener(digitListener);
		
		ActionListener operatorListener = buildOperatorListener();
		
		for(String x : operators)
			buttons.get(x).addActionListener(operatorListener);
		
		mainPanel = new JPanel();

		mainPanel.setLayout(new GridLayout(CALCULATOR_HEIGHT, CALCULATOR_WIDTH));

		mainPanel.add(resultPanel);
		mainPanel.add(numberPanel);

		add(mainPanel);
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();

		setVisible(true);
	}
	
	
	private ActionListener buildDigitListener(){
		
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				JButton j = (JButton) e.getSource();
				
				clearResultField = false;
				current += j.getText();
				resultField.setText(current);
			}
		};
	}
	

	private ActionListener buildOperatorListener(){
		
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				clearResultField = true;
				
				JButton j = (JButton) e.getSource();
				
				String operator = j.getText();
				

				switch(operator.charAt(0)){
				
					case 'A': 
						
						resultField.setText("0");
						action = null;
						firstNumber = null;
						current = "";
						expression = null;
						break; 
					
					case 'M':
						if (M == Mode.INTEGER) {
							M = Mode.FLOAT;
							j.setBackground(Color.GREEN);
						}
						else{
							M = Mode.INTEGER;
							j.setBackground(Color.ORANGE);
						}
						break;
						
					case 'B':
						if (current != "" && current != null && current != "0"){
							if (current.lastIndexOf('=') != - 1){
								current = current.substring(0, current.lastIndexOf('='));
								resultField.setText(current);
							}
							else if (current.length() > 1) {
								current = current.substring(0, current.length() - 1);
								resultField.setText(current);
							}
							else if (current.length() == 1){
								current = current.substring(0, current.length() - 1);
								resultField.setText("0");
							}
						}
						else {
							resultField.setText("0");
						}
						break;

					case '=':
						current = resultField.getText() + "=";
						try {
							Evaluate finalAnswer = new Evaluate(resultField.getText(), M);
							current += finalAnswer.toString();
						} catch (ParseError f) {
							current += f.Error();
						} catch (ArithmeticException f) {
							if (M == Mode.FLOAT){
								current += "NaN";
							}
							else {
								current += "attempting to divide by zero";
							}
						} catch (StringIndexOutOfBoundsException f) {
							current += "syntax error";
						}
						
						resultField.setText(current);
						break;
					

					case '+':
						current = resultField.getText() + "+";
						resultField.setText(current);
						break;
					case '-':
						current = resultField.getText() + "-";
						resultField.setText(current);
						break;
					case '/':
						current = resultField.getText() + "/";
						resultField.setText(current);
						break;
					case '*':
						current = resultField.getText() + "*";
						resultField.setText(current);
						break;
					case '(':
						current += "(";
						resultField.setText(current);
						break;
					case ')':
						current += ")";
						resultField.setText(current);
						break;
							
				}
			}
		};
	}
	
	
	public static void main(String[] args) {
		CalculatorFrame test = new CalculatorFrame();
	}
}