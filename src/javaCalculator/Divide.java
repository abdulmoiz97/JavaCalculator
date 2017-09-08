package javaCalculator;

class Divide extends Expr { // NOT BinaryOp!
  Expr left;
  Expr right;
  Divide(Expr x, Expr y) { left = x; right = y; }
  public String toString() { 
    return betweenParens(left) + " / " + betweenParens(right); }
  public boolean isGround() { return false; }
  
  @Override
  public int evalToInt() throws NotAnInteger {
	  int temporaryInt = (int)(left.evalToInt()/right.evalToInt());
	  double temporaryFloat = left.evalToFloat()/right.evalToFloat();
	  if (temporaryFloat != temporaryInt) {
		  throw new NotAnInteger("This expressions does not denote an Integer");
	  }
	  return left.evalToInt()/right.evalToInt();
  }
  
  @Override
  public double evalToFloat() {
	  return left.evalToFloat() / right.evalToFloat();
  }
}
