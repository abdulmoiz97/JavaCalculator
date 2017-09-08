package javaCalculator;

class IntVal extends Value {
	int val;
	
	IntVal (int val){
		m = Mode.INTEGER;
		this.val = val;
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.val);
	}
}
