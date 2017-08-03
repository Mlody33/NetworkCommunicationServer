package model;

import java.io.Serializable;

public class TestCom implements Serializable{

	private static final long serialVersionUID = 1L;
	private int number;
	
	public TestCom(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "TestCom [number=" + number + "]";
	}
	
}
