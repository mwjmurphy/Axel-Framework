package org.xmlactions.mapping.testclasses;

public class A2 {

	private String name;
	private int anInt;
	private String aString;
	private String content;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}

	public void setAnInt(int anInt) {
		this.anInt = anInt;
	}

	public int getAnInt() {
		return anInt;
	}

	public void setaString(String aString) {
		this.aString = aString;
	}

	public String getaString() {
		return aString;
	}
	

	public String toString(String spaces) {
		StringBuilder sb = new StringBuilder();
		sb.append(spaces + "anInt:" + anInt);
		sb.append("\n" + spaces + "aString:" + aString);
		sb.append("\n" + spaces + "content:" + content);
		return sb.toString();
		
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
}
