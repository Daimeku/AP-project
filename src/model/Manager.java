package model;

import java.io.Serializable;

public class Manager extends Staff implements Serializable{

	public Manager() {
		super();
	}

	public Manager(String id, String name, String password) {
		super(id, name, password);
	}
	
}
