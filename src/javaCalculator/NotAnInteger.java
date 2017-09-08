package javaCalculator;

public class NotAnInteger extends Throwable {
	
	String error;
	NotAnInteger(String s) {
		error = s;
	}
	
	public String IntegerException(){
		return error;
	}
	
	public String Error() {
		return "fraction";
	}
}
