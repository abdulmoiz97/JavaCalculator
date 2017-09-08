package javaCalculator;

class DblVal extends Value {
	double val;
	DblVal (double val){
		m = Mode.FLOAT;
		this.val = val;
	}
	
	@Override
	public String toString() {
		return String.format("%.6f", (this.val));
	}
}
