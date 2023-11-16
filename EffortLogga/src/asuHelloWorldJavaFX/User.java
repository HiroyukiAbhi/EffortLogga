package asuHelloWorldJavaFX;

import java.util.ArrayList;

public class User {
	public ArrayList<Project> projectList;
	private String userId;
	private String password;
	
	
	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
	public String getUserId() {
		return this.userId;
	}
	public String getPassword() {
		return this.password;
	}

}
