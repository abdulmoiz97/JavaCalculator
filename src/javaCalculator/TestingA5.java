package javaCalculator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
public class TestingA5 {
	Expr currentTest;
	String input;
	Evaluate evalResult;
	IntVal intValResult;
	DblVal dblValResult;

	@Test
	public void AllExprClasses() throws NotAnInteger {
		
		currentTest = new Plus(new Integer2(5), new Integer2(10));
		assertEquals(currentTest.evalToInt(), 15);
		assertEquals(currentTest.evalToFloat(), 15, 0.000001);
		
		currentTest = new Plus (new Plus(new Integer2(6), new Integer2(8)), new Plus(new Integer2(11), new Integer2(23)));
		assertEquals(currentTest.evalToInt(), 48);
		assertEquals(currentTest.evalToFloat(), 48, 0.000001);
		
		currentTest = new Minus(new Integer2(5), new Integer2(10));
		assertEquals(currentTest.evalToInt(), -5);
		assertEquals(currentTest.evalToFloat(), -5, 0.000001);
		
		currentTest = new Minus(new Minus(new Integer2(8), new Integer2(2)), new Minus(new Integer2(10), new Integer2(1)));
		assertEquals(currentTest.evalToInt(), -3);
		assertEquals(currentTest.evalToFloat(), -3, 0.000001);
		
		currentTest = new Times(new Integer2(5), new Integer2(10));
		assertEquals(currentTest.evalToInt(), 50);
		assertEquals(currentTest.evalToFloat(), 50, 0.000001);
		
		currentTest = new Times(new Times(new Integer2(5), new Integer2(3)), new Times(new Integer2(1), new Integer2(7)));
		assertEquals(currentTest.evalToInt(), 105);
		assertEquals(currentTest.evalToFloat(), 105, 0.000001);
		
		currentTest = new Divide(new Integer2(10), new Integer2(5));
		assertEquals(currentTest.evalToInt(), 2);
		assertEquals(currentTest.evalToFloat(), 2, 0.000001);
		
		currentTest = new Divide(new Divide(new Divide(new Integer2(100), new Integer2(2)), new Integer2(10)), new Integer2(5));
		assertEquals(currentTest.evalToInt(), 1);
		assertEquals(currentTest.evalToFloat(), 1, 0.000001);
		
		currentTest = new UnaryMinus(new Integer2(5));
		assertEquals(currentTest.evalToInt(), -5);
		assertEquals(currentTest.evalToFloat(), -5, 0.000001);
		
		currentTest = new Times(new Divide(new Plus(new UnaryMinus(new Integer2(5)), new Integer2(100)),new Integer2(5)), new Minus(new Integer2(50), new Integer2(37)));
		assertEquals(currentTest.evalToInt(), 247);
		assertEquals(currentTest.evalToFloat(), 247, 0.000001);
	}

	@Test
	public void EvaluateClassTesting() throws ParseError, NotAnInteger {
		input = "5+5";
		evalResult = new Evaluate(input, Mode.INTEGER);
		intValResult = (IntVal) evalResult.eval();
		assertEquals(intValResult.val, 10);

		evalResult = new Evaluate(input, Mode.FLOAT);
		dblValResult = (DblVal) evalResult.eval();
		assertEquals(dblValResult.val, 10, 0.000001);

		input = "5/5";
		evalResult = new Evaluate(input, Mode.INTEGER);
		intValResult = (IntVal) evalResult.eval();
		assertEquals(intValResult.val, 1);

		input = "10/5";
		evalResult = new Evaluate(input, Mode.FLOAT);
		dblValResult = (DblVal) evalResult.eval();
		assertEquals(dblValResult.val, 2, 0.000001);

		input = "5-5";
		evalResult = new Evaluate(input, Mode.INTEGER);
		intValResult = (IntVal) evalResult.eval();
		assertEquals(intValResult.val, 0);

		evalResult = new Evaluate(input, Mode.FLOAT);
		dblValResult = (DblVal) evalResult.eval();
		assertEquals(dblValResult.val, 0, 0.000001);


		input = "5*5";
		evalResult = new Evaluate(input, Mode.INTEGER);
		intValResult = (IntVal) evalResult.eval();
		assertEquals(intValResult.val, 25);

		evalResult = new Evaluate(input, Mode.FLOAT);
		dblValResult = (DblVal) evalResult.eval();
		assertEquals(dblValResult.val, 25, 0.000001);

		input = "5+23*2+3-5*6+2";
		evalResult = new Evaluate(input, Mode.INTEGER);
		intValResult = (IntVal) evalResult.eval();
		assertEquals(intValResult.val, 326);

		evalResult = new Evaluate(input, Mode.FLOAT);
		dblValResult = (DblVal) evalResult.eval();
		assertEquals(dblValResult.val, 326, 0.000001);


		input = "(2+(3*9))-14";
		evalResult = new Evaluate(input, Mode.INTEGER);
		intValResult = (IntVal) evalResult.eval();
		assertEquals(intValResult.val, 15);
		
		evalResult = new Evaluate(input, Mode.FLOAT);
		dblValResult = (DblVal) evalResult.eval();
		assertEquals(dblValResult.val, 15, 0.000001);
	}

	@Test(expected = ParseError.class)
	public void SyntaxErrorTest() throws ParseError, NotAnInteger {
		Parser p = new Parser("5*x10");
		p.parse();
	}
	
	@Test(expected = ParseError.class)
	public void SyntaxErrorTest2() throws ParseError, NotAnInteger {
		Parser p = new Parser("(2+(5*1b0))-4a9");
		p.parse();
	}
	
	@Test(expected = NotAnInteger.class)
	public void FractionErrorTest()throws NotAnInteger{
		Expr test = new Divide(new Integer2(5) , new Integer2(10));
		test.evalToInt();
	}
	
	@Test(expected = NotAnInteger.class)
	public void FractionErrorTest2()throws NotAnInteger{
		Expr test = new Divide (new Plus(new Integer2(5),new Integer2(7)),new Plus(new Integer2(8),new Integer2(1)));
		test.evalToInt();
	}
}

