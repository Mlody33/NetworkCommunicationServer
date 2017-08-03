package model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import javafx.beans.property.SimpleIntegerProperty;

public class TestCom implements Serializable, Externalizable{

	private static final long serialVersionUID = 1L;
	private SimpleIntegerProperty number;
	
	public TestCom() {}
	
	public TestCom(int number) {
		this.number = new SimpleIntegerProperty(number);
	}
	
	public int getNumber() {
		return this.number.get();
	}
	
	public void setNumber(int number) {
		this.number = new SimpleIntegerProperty(number);
	}

	@Override
	public String toString() {
		return "TestCom [number=" + number + "]";
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.number = new SimpleIntegerProperty(in.readInt());
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(getNumber());
	}
	
}
