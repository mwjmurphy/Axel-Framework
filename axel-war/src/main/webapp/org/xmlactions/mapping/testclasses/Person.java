package org.xmlactions.mapping.testclasses;

import java.util.ArrayList;
import java.util.List;

public class Person {

	private int age;
	private String name;
	private List<String> mobiles = new ArrayList<String>();
	private Address address;
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getMobiles() {
		return mobiles;
	}
	public void setMobiles(List<String> mobiles) {
		this.mobiles = mobiles;
	}
	public void setMobile(String mobile) {
		this.mobiles.add(mobile);
	}
	public String getMobile() {
		if (this.mobiles.size() > 0) {
			return this.mobiles.get(this.mobiles.size()-1);
		}
		return null;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	public Address getAddress() {
		return this.address;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Person:");
		sb.append("\nname   = " + getName());
		sb.append("\nage = " + getAge());
		for (String mobile : mobiles) {
			sb.append("\nmobile = " + mobile);
		}
		if (getAddress() != null) {
			sb.append("\n" + getAddress());
		}
		return sb.toString();
	}
}
