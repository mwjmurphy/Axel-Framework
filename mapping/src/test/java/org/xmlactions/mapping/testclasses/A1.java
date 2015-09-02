package org.xmlactions.mapping.testclasses;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class A1 extends A1a implements A1interface {

	private int anInt;
	public int aLong;
	private double aDouble;
	private String aString;
	public BigInteger bigInt;
	private Timestamp timestamp;
	private Date date;
	private java.sql.Date sqldate;
	
	
	private String [] users;

	private List<A1> listOfA1s = new ArrayList<A1>();

	private A2 a2;
	private List<A2> listOfA2s = new ArrayList<A2>();

	private List<A2> ers = new ArrayList<A2>();

    private String content;

	public int getAnInt() {
		return anInt;
	}
	public void setAnInt(int anInt) {
		this.anInt = anInt;
	}
	public double getaDouble() {
		return aDouble;
	}
	public void setaDouble(double aDouble) {
		this.aDouble = aDouble;
	}
	public String getaString() {
		return aString;
	}
	public void setaString(String aString) {
		this.aString = aString;
	}
	public List<A1> getListOfA1s() {
		return listOfA1s;
	}
	public void setListOfA1s(List<A1> listOfA1s) {
		this.listOfA1s = listOfA1s;
	}
	public BigInteger getBigInt() {
		return bigInt;
	}
	public void setBigInt(BigInteger bigInt) {
		this.bigInt = bigInt;
	}
	
	public String toString(String spaces) {
		StringBuilder sb = new StringBuilder();
		sb.append(spaces + "anInt:" + anInt);
		sb.append("\n" + spaces + "aLong:" + aLong);
		sb.append("\n" + spaces + "aDouble:" + aDouble);
		sb.append("\n" + spaces + "aString:" + aString);
		sb.append("\n" + spaces + "bigInt:" + bigInt);
		sb.append("\n" + spaces + "timestamp:" + timestamp);
		sb.append("\n" + spaces + "date:" + date);
        sb.append("\n" + spaces + "sqldate:" + sqldate);
        sb.append("\n" + spaces + "content:" + content);
		if (getListOfA1s() != null) {
			for (A1 a1 : getListOfA1s()) {
				sb.append("\n " + spaces + "listOfA1s:" + a1.toString(spaces + " "));
			}
		}
		if (a2 != null) {
			sb.append("\n" + spaces + " a2:" + a2.toString());
		}
		if (getListOfA2s() != null) {
			for (A2 _a2 : getListOfA2s()) {
				sb.append("\n " + spaces + "listOfA2s:" + _a2.toString(spaces + " "));
			}
		}
		return sb.toString();
		
	}
	public int _getaLong() {
		return aLong;
	}
	public void _setaLong(int aLong) {
		this.aLong = aLong;
	}
	public A2 getA2() {
		return a2;
	}
	public void setA2(A2 a2) {
		this.a2 = a2;
	}
	public List<A2> getListOfA2s() {
		return listOfA2s;
	}
	public void setListOfA2s(List<A2> listOfA2s) {
		this.listOfA2s = listOfA2s;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	public java.sql.Date getSqldate() {
		return sqldate;
	}
	public void setSqldate(java.sql.Date sqldate) {
		this.sqldate = sqldate;
	}
	public void setUsers(String [] users) {
		this.users = users;
	}
	public String [] getUsers() {
		return users;
	}
	public void setErs(List<A2> ers) {
		this.ers = ers;
	}
	public List<A2> getErs() {
		return ers;
	}
	public List<A2> getErsList() {
		return ers;
	}

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
