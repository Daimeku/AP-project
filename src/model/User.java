package model;

import java.io.Serializable;

import org.apache.log4j.*;

public abstract class User implements Serializable{
	protected static final Logger log = Logger.getLogger(User.class); //logger for User class and children
	protected String id;
	protected String name;
	protected String password;
	
	public User(){
		this.id = Default.ID;
		this.name = Default.NAME;
		this.password = Default.PASSWORD;
	}
	public User(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 *  PR:
	 *  Protected constants for use with users
	 */
	protected static final class Default{
		public static final String ID = null;
		public static final String NAME = null;
		public static final String PASSWORD = "default";
	}
	
}
