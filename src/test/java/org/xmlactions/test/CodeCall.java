package org.xmlactions.test;

public class CodeCall {

	public String toString() {
		return "toString:" + this.getClass().getName();
	}
	
	public String call(Object arg1, Object arg2) {
		return ("arg1(Object):" + arg1 + " arg2(Object):" + arg2);
	}
	
	public String call(String arg1, String arg2) {
		return ("arg1(String):" + arg1 + " arg2(String):" + arg2);
	}
	
	public String call(int number) {
		return "The number is(int):" + number;
	}
}
