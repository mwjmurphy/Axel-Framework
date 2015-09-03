package org.xmlactions.mapping.xml_to_bean.tutorial;

public class First {

	public int id;
	private String name;
	private Second second;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Second getSecond() {
		return second;
	}
	public void setSecond(Second second) {
		this.second = second;
	}
	
}
